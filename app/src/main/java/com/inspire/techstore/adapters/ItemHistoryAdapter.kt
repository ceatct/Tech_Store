package com.inspire.techstore.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inspire.techstore.R
import com.inspire.techstore.api.data.ProductModelItem
import com.makeramen.roundedimageview.RoundedImageView

class ItemHistoryAdapter : RecyclerView.Adapter<ItemHistoryAdapter.MyViewHolder>() {

    private var products: List<ProductModelItem>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setProductList(products: List<ProductModelItem>?) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(products?.get(position))
    }

    override fun getItemCount(): Int {
        return products?.size ?: 0
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.name)
        private val price: TextView = itemView.findViewById(R.id.price)
        private val image: RoundedImageView = itemView.findViewById(R.id.image)

        @SuppressLint("SetTextI18n")
        fun bind(product: ProductModelItem?) {
            Glide.with(itemView.context)
                .load(product?.image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(image)

            name.text = product?.title
            price.text = "$ ${product?.price}"
        }
    }
}