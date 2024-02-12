package com.inspire.techstore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.inspire.techstore.R
import com.inspire.techstore.adapters.ImageAdapter
import com.inspire.techstore.fragments.models.ViewFragmentViewModel
import me.relex.circleindicator.CircleIndicator3

class ViewFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[ViewFragmentViewModel::class.java]
    }

    private lateinit var id: String
    private lateinit var viewpager: ViewPager2
    private lateinit var pagerIndicator: CircleIndicator3
    private lateinit var item: LinearLayout
    private lateinit var name : TextView
    private lateinit var price : TextView
    private lateinit var info : TextView
    private lateinit var backActionLayout : LinearLayout

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
        pagerIndicator = view.findViewById(R.id.dots)
        item = view.findViewById(R.id.item)
        name = view.findViewById(R.id.name)
        price = view.findViewById(R.id.price)
        info = view.findViewById(R.id.info)
        backActionLayout = view.findViewById(R.id.back)

        val header = requireActivity().findViewById<ViewGroup>(R.id.include)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        header.visibility = GONE
        bottomNavigationView.visibility = GONE

        backActionLayout.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragments, MainFragment())
            fragmentTransaction.commit()
            header.visibility = VISIBLE
            bottomNavigationView.visibility = VISIBLE
        }

        viewModel.getLiveDataObserver().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                name.text = data.title
                price.text = "$ ${data.price}"
                info.text = data.description
                val imgList = arrayListOf(data.image)

                viewpager.adapter = ImageAdapter(imgList)
                viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            } else {
                Toast.makeText(requireContext(), "data", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.makeAPICall(id)

        pagerIndicator.setViewPager(viewpager)

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