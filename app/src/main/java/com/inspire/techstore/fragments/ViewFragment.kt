package com.inspire.techstore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
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
import com.inspire.techstore.api.data.CartProduct
import com.inspire.techstore.fragments.models.ViewFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.relex.circleindicator.CircleIndicator3
import java.text.SimpleDateFormat
import java.util.Date

class ViewFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[ViewFragmentViewModel::class.java]
    }
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private lateinit var id: String
    private lateinit var viewpager: ViewPager2
    private lateinit var pagerIndicator: CircleIndicator3
    private lateinit var item: LinearLayout
    private lateinit var name : TextView
    private lateinit var price : TextView
    private lateinit var info : TextView
    private lateinit var backActionLayout : LinearLayout
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getString("id") ?: ""
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility", "SimpleDateFormat")
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
        addButton = view.findViewById(R.id.add)

        val header = requireActivity().findViewById<ViewGroup>(R.id.include)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        header.visibility = GONE
        bottomNavigationView.visibility = GONE

        backActionLayout.setOnClickListener {
            back()
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

        addButton.setOnClickListener{
            coroutineScope.launch{
                val products = listOf(CartProduct(productId = id.toInt(), quantity = 1))
                val date = SimpleDateFormat("yyyy-MM-dd")
                val formattedDate = date.format(Date())
                viewModel.addToCart(5, formattedDate, products, requireContext())
            }
        }

        viewModel.makeAPICall(id)

        pagerIndicator.setViewPager(viewpager)

        return view
    }

    private fun back(){
        val header = requireActivity().findViewById<ViewGroup>(R.id.include)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragments, MainFragment())
        fragmentTransaction.commit()
        header.visibility = VISIBLE
        bottomNavigationView.visibility = VISIBLE
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