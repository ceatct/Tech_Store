package com.inspire.techstore.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inspire.techstore.R
import com.inspire.techstore.db.history.History

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {

    private var productList: List<History>? = null

    fun setProductList(productList: List<History>?) {
        this.productList = productList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_orders_history, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(productList?.get(position))
    }

    override fun getItemCount(): Int {
        return productList?.size ?: 0
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderNumber: TextView = itemView.findViewById(R.id.order_number)
        private val orderDate: TextView = itemView.findViewById(R.id.order_date)
        private val orderStatus: TextView = itemView.findViewById(R.id.order_status)
        private val orderPrice: TextView = itemView.findViewById(R.id.order_price)
        private val recyclerItems: RecyclerView = itemView.findViewById(R.id.recycler_items)

        private val itemHistoryAdapter = ItemHistoryAdapter()

        init {
            recyclerItems.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
            recyclerItems.adapter = itemHistoryAdapter
        }

        @SuppressLint("SetTextI18n")
        fun bind(history: History?) {
            orderNumber.text = history?.number.toString()
            orderDate.text = history?.date.toString()
            orderStatus.text = history?.status.toString()
            orderPrice.text = "$ " + history?.total.toString()

            val itemHistoryAdapter = ItemHistoryAdapter()
            recyclerItems.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
            recyclerItems.adapter = itemHistoryAdapter
            itemHistoryAdapter.setProductList(history?.products)
        }
    }
}