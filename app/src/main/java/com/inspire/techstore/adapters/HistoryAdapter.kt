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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = productList?.get(position)

        holder.orderNumber.text = product?.number.toString()
        holder.orderDate.text = product?.date.toString()
        holder.orderStatus.text = product?.status.toString()
        holder.orderPrice.text = "$ " + product?.total.toString()

        val itemsRecyclerView = ItemHistoryAdapter(product!!.products)
        holder.recyclerItems.adapter = itemsRecyclerView

    }

    override fun getItemCount(): Int {
        return if (productList == null) 0 else productList!!.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var orderNumber: TextView
        var orderDate: TextView
        var orderStatus: TextView
        var orderPrice: TextView
        var recyclerItems: RecyclerView

        init {
            orderNumber = itemView.findViewById(R.id.order_number)
            orderDate = itemView.findViewById(R.id.order_date)
            orderStatus = itemView.findViewById(R.id.order_status)
            orderPrice = itemView.findViewById(R.id.order_price)
            recyclerItems = itemView.findViewById(R.id.recycler_items)

            recyclerItems.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
        }
    }

}
