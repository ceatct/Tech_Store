package com.inspire.techstore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.inspire.techstore.R
import com.inspire.techstore.adapters.CategoriesAdapter
import com.inspire.techstore.adapters.ImageAdapter
import com.inspire.techstore.adapters.ProductAdapter
import com.inspire.techstore.fragments.models.MainFragmentViewModel
import me.relex.circleindicator.CircleIndicator3
class MainFragment : Fragment() {

   // private val productAdapter = ProductAdapter(ViewModelProvider(this))
    private val categoriesAdapter = CategoriesAdapter()

    private val viewModel by lazy {
        ViewModelProvider(this)[MainFragmentViewModel::class.java]
    }

    private val productAdapter by lazy {
        val viewModelProvider = ViewModelProvider(this)
        ProductAdapter(viewModelProvider)
    }

    private lateinit var viewpager: ViewPager2
    private lateinit var dots: CircleIndicator3
    private lateinit var recyclerSale: RecyclerView
    private lateinit var recyclerNew: RecyclerView
    private lateinit var recyclerCategory: RecyclerView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        viewpager = view.findViewById(R.id.viewpager)
        dots = view.findViewById(R.id.dots)
        recyclerSale = view.findViewById(R.id.recycler_sale)
        recyclerNew = view.findViewById(R.id.recycler_new)
        recyclerCategory = view.findViewById(R.id.recycler_category)

        recyclerSale.adapter = productAdapter
        recyclerNew.adapter = productAdapter
        recyclerCategory.adapter = categoriesAdapter

        viewModel.getLiveDataObserver().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                productAdapter.setProductList(data)
                productAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "data", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getLiveDataObserverCategories().observe(viewLifecycleOwner) { data2 ->
            if (data2 != null) {
                categoriesAdapter.setProductList(data2)
                categoriesAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "data2", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.makeAPICall()
        viewModel.makeAPICallCategories()

        val imgList = arrayListOf(
            "https://as2.ftcdn.net/v2/jpg/04/57/27/23/1000_F_457272301_TAhCbk02tLPvPftIQfiWML5UHFiyc1XQ.jpg",
            "https://mobisoftinfotech.com/resources/wp-content/uploads/2022/08/Banner-1.png")

        viewpager.adapter = ImageAdapter(imgList)
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        dots.setViewPager(viewpager)

        return view
    }

}
