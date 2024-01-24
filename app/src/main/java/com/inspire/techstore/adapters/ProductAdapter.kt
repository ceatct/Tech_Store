package com.inspire.techstore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inspire.techstore.R
import com.inspire.techstore.api.data.ProductModel
import com.makeramen.roundedimageview.RoundedImageView

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    private var productList: List<ProductModel>? = null

    fun setProductList(productList: List<ProductModel>?){
        this.productList = productList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductAdapter.MyViewHolder, position: Int) {
        // Set product image
        Glide.with(holder.itemView.context)
            .load(productList?.get(position)?.get(position)?.image)
            .into(holder.imageView)

        // Set product name
        holder.name.text = productList?.get(position)?.get(position)?.title

        // Set product prices
        holder.oldPrice.text = "product.oldPrice.toString()"
        holder.price.text = productList?.get(position)?.get(position)?.price.toString()
    }

    override fun getItemCount(): Int {
        if(productList == null) return 0
        else return productList!!.size
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