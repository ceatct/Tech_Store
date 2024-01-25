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

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    private var productList: List<ProductModelItem>? = null

    fun setProductList(productList: List<ProductModelItem>?){
        this.productList = productList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Set product image
        Glide.with(holder.itemView.context)
            .load(productList?.get(position)?.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.imageView)

        // Set product name
        holder.name.text = productList?.get(position)?.title

        // Set product prices
        holder.oldPrice.text = "$1.20"
        holder.price.text = "$" + productList?.get(position)?.price.toString()
    }

    override fun getItemCount(): Int {
        return if(productList == null) 0
        else productList!!.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: RoundedImageView
        var name: TextView
        var oldPrice: TextView
        var price: TextView

        init {
            imageView = itemView.findViewById(R.id.imageView)
            name = itemView.findViewById(R.id.name)
            oldPrice = itemView.findViewById(R.id.old_price)
            price = itemView.findViewById(R.id.price)
        }
    }

}