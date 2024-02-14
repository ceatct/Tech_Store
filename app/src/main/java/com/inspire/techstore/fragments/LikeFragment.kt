package com.inspire.techstore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.inspire.techstore.R
import com.inspire.techstore.adapters.LikeAdapter
import com.inspire.techstore.db.like.LikeViewModel

class LikeFragment : Fragment() {

    private val productAdapter by lazy {
        val viewModelProvider = ViewModelProvider(this)
        LikeAdapter(viewModelProvider)
    }

    private lateinit var likeViewModel: LikeViewModel
    private lateinit var emptyTextView: TextView
    private lateinit var recyclerLike: RecyclerView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_like, container, false)

        likeViewModel = ViewModelProvider(this)[LikeViewModel::class.java]

        emptyTextView = view.findViewById(R.id.empty)

        recyclerLike = view.findViewById(R.id.recycler_like)
        recyclerLike.adapter = productAdapter

        likeViewModel.readAllData.observe(viewLifecycleOwner) { like ->
            productAdapter.setProductList(like)
            productAdapter.notifyDataSetChanged()

            if (like.isEmpty()) {
                emptyTextView.visibility = VISIBLE
            } else {
                emptyTextView.visibility = GONE
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragments, MainFragment())
                fragmentTransaction.commit()
            }
        })
    }

}