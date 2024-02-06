package com.inspire.techstore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var recyclerCategory: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        category = arguments?.getString("category") ?: ""
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_result, container, false)

        recyclerCategory = view.findViewById(R.id.recycler_category)

        recyclerCategory.adapter = productAdapter

        viewModel.getLiveDataObserver().observe(viewLifecycleOwner) { product ->
            if (product != null) {
                productAdapter.setProductList(product)
                productAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "Error with products loading", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.makeAPICall(category)

        view.findViewById<TextView>(R.id.textView).text = category

        return view
    }
}