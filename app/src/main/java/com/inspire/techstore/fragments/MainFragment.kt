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
import com.inspire.techstore.models.ImageItem
import me.relex.circleindicator.CircleIndicator3
class MainFragment : Fragment() {

    private val productAdapter = ProductAdapter()
    private val categoriesAdapter = CategoriesAdapter()

    private val viewModel by lazy {
        ViewModelProvider(this)[MainFragmentViewModel::class.java]
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
            ImageItem("https://as2.ftcdn.net/v2/jpg/04/57/27/23/1000_F_457272301_TAhCbk02tLPvPftIQfiWML5UHFiyc1XQ.jpg"),
            ImageItem("https://mobisoftinfotech.com/resources/wp-content/uploads/2022/08/Banner-1.png"),
            ImageItem("https://thumbs.dreamstime.com/z/%D0%B2%D0%B5%D0%BA%D1%82%D0%BE%D1%80%D0%BD%D1%8B%D0%B9-%D0%B1%D0%B0%D0%BD%D0%BD%D0%B5%D1%80-%D0%B4%D0%BB%D1%8F-iphone-%D0%B2%D0%B8%D0%BD%D0%BD%D0%B8%D1%86%D0%BA%D0%B0%D1%8F-%D1%83%D0%BA%D1%80%D0%B0%D0%B8%D0%BD%D0%B0-%D0%BD%D0%BE%D1%8F%D0%B1%D1%80%D1%8F-%D0%B8%D0%BB%D0%BB%D1%8E%D1%81%D1%82%D1%80%D0%B0%D1%86%D0%B8%D1%8F-%D0%B2%D0%B5%D0%BA%D1%82%D0%BE%D1%80%D0%B0-229970813.jpg"))

        viewpager.adapter = ImageAdapter(imgList)
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        dots.setViewPager(viewpager)

        return view
    }
}
