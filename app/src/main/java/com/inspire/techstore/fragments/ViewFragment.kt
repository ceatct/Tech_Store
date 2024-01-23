package com.inspire.techstore.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.inspire.techstore.R
import com.inspire.techstore.adapters.ImageAdapter
import com.inspire.techstore.models.ImageItem
import me.relex.circleindicator.CircleIndicator3

class ViewFragment : Fragment() {

    private lateinit var viewpager: ViewPager2
    private lateinit var dots: CircleIndicator3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_view, container, false)

        viewpager = view.findViewById(R.id.viewpager)
        dots = view.findViewById(R.id.dots)

        val imgList = arrayListOf(
            ImageItem("https://touch.com.ua/upload/resize_cache/webp/iblock/472/500_500_1/twswnd6xwz5lzsmlku8pse1dwuwrlrmj.webp"),
            ImageItem("https://touch.com.ua/upload/resize_cache/webp/iblock/0d6/500_500_1/01qiufecya0yic1uvis42t0i1knhprb6.webp"),
            ImageItem("https://touch.com.ua/upload/resize_cache/webp/iblock/c42/500_500_1/bthxj010b8t46767gpqrqn64q3qx2iyv.webp")
        )

        viewpager.adapter = ImageAdapter(imgList)
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        dots.setViewPager(viewpager)

        return view
    }
}