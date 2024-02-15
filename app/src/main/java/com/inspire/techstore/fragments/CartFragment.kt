package com.inspire.techstore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_cart, container, false)

        emptyTextView = view.findViewById(R.id.empty)
        recyclerCart = view.findViewById(R.id.recycler_cart)
        orderButton = view.findViewById(R.id.order)

        recyclerCart.adapter = productAdapter

        viewModel.getLiveDataObserver().observe(viewLifecycleOwner) { products ->
            if (products != null) {
                productAdapter.setProductList(products)
                productAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "Error with products loading", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.makeAPICall()

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