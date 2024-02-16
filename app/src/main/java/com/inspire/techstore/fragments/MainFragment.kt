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
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.inspire.techstore.R
import com.inspire.techstore.adapters.CategoriesAdapter
import com.inspire.techstore.adapters.ImageAdapter
import com.inspire.techstore.adapters.ProductAdapter
import com.inspire.techstore.api.data.CategoriesModel
import com.inspire.techstore.fragments.models.MainFragmentViewModel
import me.relex.circleindicator.CircleIndicator3
class MainFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[MainFragmentViewModel::class.java]
    }

    private val productAdapter by lazy {
        val viewModelProvider = ViewModelProvider(this)
        ProductAdapter(viewModelProvider)
    }

    private val categoriesAdapter = CategoriesAdapter()
    private lateinit var viewpager: ViewPager2
    private lateinit var pagerIndicator: CircleIndicator3
    private lateinit var recyclerSale: RecyclerView
    private lateinit var recyclerNew: RecyclerView
    private lateinit var recyclerCategory: RecyclerView
    private lateinit var progressBarSale: ProgressBar
    private lateinit var progressBarNew: ProgressBar
    private lateinit var progressBarCategory: ProgressBar

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        viewpager = view.findViewById(R.id.viewpager)
        pagerIndicator = view.findViewById(R.id.dots)
        recyclerSale = view.findViewById(R.id.recycler_sale)
        recyclerNew = view.findViewById(R.id.recycler_new)
        recyclerCategory = view.findViewById(R.id.recycler_category)
        progressBarSale = view.findViewById(R.id.sale_progress)
        progressBarNew = view.findViewById(R.id.new_progress)
        progressBarCategory = view.findViewById(R.id.category_progress)

        recyclerSale.adapter = productAdapter
        recyclerNew.adapter = productAdapter
        recyclerCategory.adapter = categoriesAdapter

        viewModel.getLiveDataObserver().observe(viewLifecycleOwner) { product ->
            if (product != null) {
                productAdapter.setProductList(product)
                productAdapter.notifyDataSetChanged()

                if (product.isEmpty()) {
                    progressBarSale.visibility = View.VISIBLE
                    progressBarNew.visibility = View.VISIBLE
                } else {
                    progressBarSale.visibility = View.GONE
                    progressBarNew.visibility = View.GONE
                }

            } else {
                Toast.makeText(context, getString(R.string.error_product), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getLiveDataObserverCategories().observe(viewLifecycleOwner) { category ->
            if (category != null) {

                val newList = CategoriesModel()
                newList.addAll(category.take(3))
                newList.add("More")

                categoriesAdapter.setProductList(newList)
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

        viewModel.getLiveDataObserverBanners().observe(viewLifecycleOwner) { banner ->
            if (banner != null) {
                val imgList = ArrayList<String>(banner)

                viewpager.adapter = ImageAdapter(imgList)
                viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                pagerIndicator.setViewPager(viewpager)
            } else {
                Toast.makeText(context, getString(R.string.error_product), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.makeAPICall()
        viewModel.makeAPICallCategories()
        viewModel.makeAPICallBanners()

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
