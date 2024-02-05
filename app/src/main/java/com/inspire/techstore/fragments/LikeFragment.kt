package com.inspire.techstore.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.inspire.techstore.R
import com.inspire.techstore.adapters.LikeAdapter
import com.inspire.techstore.db.LikeViewModel

class LikeFragment : Fragment() {

    private lateinit var recyclerCategory: RecyclerView
    private val productAdapter = LikeAdapter(ViewModelProvider(this))
    private lateinit var likeViewModel: LikeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_like, container, false)

        likeViewModel = ViewModelProvider(this)[LikeViewModel::class.java]

        recyclerCategory = view.findViewById(R.id.recycler_category)
        recyclerCategory.adapter = productAdapter

        likeViewModel.readAllData.observe(viewLifecycleOwner) { like ->
            productAdapter.setProductList(like)
        }

        return view
    }

}