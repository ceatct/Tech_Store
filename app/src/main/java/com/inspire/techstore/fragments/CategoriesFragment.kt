package com.inspire.techstore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inspire.techstore.R
import com.inspire.techstore.adapters.CategoriesAdapter
import com.inspire.techstore.fragments.models.CategoriesFragmentViewModel

class CategoriesFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[CategoriesFragmentViewModel::class.java]
    }

    private val categoriesAdapter = CategoriesAdapter()
    private lateinit var recyclerCategory: RecyclerView
    private lateinit var progressBarCategory: ProgressBar

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_categories, container, false)

        recyclerCategory = view.findViewById(R.id.recycler_category)
        progressBarCategory = view.findViewById(R.id.category_progress)

        val numberOfColumns = resources.getInteger(R.integer.number_of_columns)
        recyclerCategory.layoutManager = GridLayoutManager(requireContext(), numberOfColumns)

        recyclerCategory.adapter = categoriesAdapter

        viewModel.getLiveDataObserverCategories().observe(viewLifecycleOwner) { category ->
            if (category != null) {
                categoriesAdapter.setProductList(category)
                categoriesAdapter.notifyDataSetChanged()

                if (category.isEmpty()) {
                    progressBarCategory.visibility = View.VISIBLE
                } else {
                    progressBarCategory.visibility = View.GONE
                }

            } else {
                Toast.makeText(context, getString(R.string.error_product), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loadData()

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