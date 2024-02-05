package com.inspire.techstore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.inspire.techstore.R
import com.inspire.techstore.adapters.ImageAdapter
import com.inspire.techstore.fragments.models.ViewFragmentViewModel
import me.relex.circleindicator.CircleIndicator3


class ViewFragment : Fragment() {

    private lateinit var viewpager: ViewPager2
    private lateinit var viewpager2: ViewPager2
    private lateinit var dots: CircleIndicator3
    private lateinit var dots2: CircleIndicator3

    private lateinit var item: LinearLayout
    private lateinit var fullView: LinearLayout
    private lateinit var close: ImageButton

    private lateinit var id: String

    private lateinit var name : TextView
    private lateinit var price : TextView
    private lateinit var info : TextView
    private lateinit var back : LinearLayout

    private val viewModel by lazy {
        ViewModelProvider(this)[ViewFragmentViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getString("id") ?: ""
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_view, container, false)

        viewpager = view.findViewById(R.id.viewpager)
        viewpager2 = view.findViewById(R.id.viewpager2)
        dots = view.findViewById(R.id.dots)
        dots2 = view.findViewById(R.id.dots2)

        item = view.findViewById(R.id.item)
        fullView = view.findViewById(R.id.fullView)
        close = view.findViewById(R.id.close)

        name = view.findViewById(R.id.name)
        price = view.findViewById(R.id.price)
        info = view.findViewById(R.id.info)
        back = view.findViewById(R.id.back)

        close.setOnClickListener {
            item.visibility = VISIBLE
            fullView.visibility = GONE
        }

        back.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStackImmediate()
        }

        viewModel.getLiveDataObserver().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                name.text = data.title
                price.text = "$ ${data.price}"
                info.text = data.description
                val imgList = arrayListOf(data.image)

                viewpager.adapter = ImageAdapter(imgList)
                viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

                viewpager2.adapter = ImageAdapter(imgList)
                viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            } else {
                Toast.makeText(requireContext(), "data", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.makeAPICall(id)

        dots.setViewPager(viewpager)
        dots2.setViewPager(viewpager2)

        return view
    }

}