package com.inspire.techstore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inspire.techstore.R
import com.inspire.techstore.adapters.ProductAdapter
import com.inspire.techstore.fragments.models.ResultFragmentViewModel

class ResultFragment : Fragment() {

    private val productAdapter by lazy {
        val viewModelProvider = ViewModelProvider(this)
        ProductAdapter(viewModelProvider)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[ResultFragmentViewModel::class.java]
    }

    private lateinit var category: String
    private lateinit var name: String
    private lateinit var emptyTextView: TextView
    private lateinit var recyclerCategory: RecyclerView
    private lateinit var progressBarResult: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        category = arguments?.getString("category") ?: ""
        name = arguments?.getString("name") ?: ""

    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_result, container, false)

        recyclerCategory = view.findViewById(R.id.recycler_category)
        progressBarResult = view.findViewById(R.id.result_progress)
        emptyTextView = view.findViewById(R.id.empty)

        val numberOfColumns = resources.getInteger(R.integer.number_of_columns)
        recyclerCategory.layoutManager = GridLayoutManager(requireContext(), numberOfColumns)

        recyclerCategory.adapter = productAdapter

        if(category.isEmpty()){
            view.findViewById<TextView>(R.id.textView).text = "${getString(R.string.result_for)} $name"

            viewModel.getLiveDataObserver().observe(viewLifecycleOwner) { product ->
                if (product != null) {
                    val filteredProducts = product.filter { it.title.contains(name, ignoreCase = true) }
                    productAdapter.setProductList(filteredProducts)
                    productAdapter.notifyDataSetChanged()

                    if (product.isEmpty()) {
                        progressBarResult.visibility = View.VISIBLE
                    } else {
                        progressBarResult.visibility = View.GONE
                        emptyTextView.visibility = View.GONE
                    }

                    if(productAdapter.itemCount == 0){
                        emptyTextView.visibility = View.VISIBLE
                    }

                } else {
                    Toast.makeText(context, getString(R.string.error_product), Toast.LENGTH_SHORT).show()
                }
            }
            viewModel.makeAPICallSearch()
        }
        else {
            view.findViewById<TextView>(R.id.textView).text = category

            viewModel.getLiveDataObserver().observe(viewLifecycleOwner) { product ->
                if (product != null) {
                    productAdapter.setProductList(product)
                    productAdapter.notifyDataSetChanged()

                    if (product.isEmpty()) {
                        progressBarResult.visibility = View.VISIBLE
                    } else {
                        progressBarResult.visibility = View.GONE
                    }

                } else {
                    Toast.makeText(context, getString(R.string.error_product), Toast.LENGTH_SHORT).show()
                }
            }
            viewModel.makeAPICall(category)
        }

        return view
    }

    private fun back(){
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragments, MainFragment())
        fragmentTransaction.commit()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                back()
            }
        })
    }

}