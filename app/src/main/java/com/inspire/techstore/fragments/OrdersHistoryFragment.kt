package com.inspire.techstore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.inspire.techstore.R
import com.inspire.techstore.adapters.HistoryAdapter
import com.inspire.techstore.db.history.HistoryViewModel

class OrdersHistoryFragment : Fragment() {

    private lateinit var backActionLayout : LinearLayout
    private lateinit var emptyTextView: TextView
    private lateinit var recyclerOrders: RecyclerView
    private lateinit var historyViewModel: HistoryViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_orders_history, container, false)

        backActionLayout = view.findViewById(R.id.back)
        emptyTextView = view.findViewById(R.id.empty)
        recyclerOrders = view.findViewById(R.id.recycler_history)

        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]

        val ordersAdapter = HistoryAdapter()
        recyclerOrders.adapter = ordersAdapter

        historyViewModel.readAllData.observe(viewLifecycleOwner) { item ->
            ordersAdapter.setProductList(item)
            ordersAdapter.notifyDataSetChanged()

            if (item.isEmpty()) {
                emptyTextView.visibility = View.VISIBLE
            } else {
                emptyTextView.visibility = View.GONE
            }
        }

        backActionLayout.setOnClickListener {
            back()
        }

        return view
    }

    private fun back(){
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragments, ProfileFragment())
        fragmentTransaction.commit()
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