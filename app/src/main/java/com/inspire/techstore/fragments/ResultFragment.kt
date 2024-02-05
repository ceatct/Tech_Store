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

    private lateinit var category: String

    private val productAdapter = ProductAdapter(ViewModelProvider(this))

    private val viewModel by lazy {
        ViewModelProvider(this)[ResultFragmentViewModel::class.java]
    }

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

        viewModel.getLiveDataObserver().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                productAdapter.setProductList(data)
                productAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "data", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.makeAPICall(category)

        view.findViewById<TextView>(R.id.textView).text = category

        return view
    }
}