package com.inspire.techstore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inspire.techstore.R
import com.inspire.techstore.adapters.CartAdapter
import com.inspire.techstore.fragments.models.CartFragmentViewModel

class CartFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[CartFragmentViewModel::class.java]
    }

    private val productAdapter by lazy {
        CartAdapter()
    }

    private lateinit var recyclerCart: RecyclerView
    private lateinit var emptyTextView: TextView
    private lateinit var orderButton: Button
    private lateinit var progressBarCart: ProgressBar
    private lateinit var totalPrice : TextView

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_cart, container, false)

        emptyTextView = view.findViewById(R.id.empty)
        recyclerCart = view.findViewById(R.id.recycler_cart)
        orderButton = view.findViewById(R.id.order)
        progressBarCart = view.findViewById(R.id.cart_progress)
        totalPrice = view.findViewById(R.id.total)

        val numberOfColumns = resources.getInteger(R.integer.number_of_columns)
        recyclerCart.layoutManager = GridLayoutManager(requireContext(), numberOfColumns)

        recyclerCart.adapter = productAdapter

        viewModel.getLiveDataObserver().observe(viewLifecycleOwner) { products ->
            if (products != null) {
                productAdapter.setProductList(products)
                productAdapter.notifyDataSetChanged()

                if (products.isEmpty()) {
                    emptyTextView.visibility = View.VISIBLE
                    progressBarCart.visibility = View.VISIBLE
                    orderButton.visibility = View.GONE
                    totalPrice.visibility = View.GONE
                } else {
                    emptyTextView.visibility = View.GONE
                    progressBarCart.visibility = View.GONE
                    orderButton.visibility = View.VISIBLE
                    totalPrice.visibility = View.VISIBLE
                }

            } else {
                Toast.makeText(context, getString(R.string.error_product), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.liveDataTotalPrice.observe(viewLifecycleOwner) { total ->
            totalPrice.text = "$ $total"
        }

        orderButton.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragments, OrderFragment())
            fragmentTransaction.commit()
        }

        viewModel.loadData()

        return view

    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })
    }

}