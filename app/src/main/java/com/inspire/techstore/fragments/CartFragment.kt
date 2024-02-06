package com.inspire.techstore.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inspire.techstore.R

class CartFragment : Fragment() {

    private lateinit var recyclerCart: RecyclerView
    private lateinit var empty: TextView
    private lateinit var order: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_cart, container, false)

        empty = view.findViewById(R.id.empty)
        recyclerCart = view.findViewById(R.id.recycler_cart)
        order = view.findViewById(R.id.order)



        return view
    }

}