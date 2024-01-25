package com.inspire.techstore.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inspire.techstore.R
import com.inspire.techstore.api.data.CategoriesModel

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>() {

    private var productList: CategoriesModel? = null

    fun setProductList(productList: CategoriesModel){
        this.productList = productList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.text.text = productList?.get(position)

    }

    override fun getItemCount(): Int {
        return if(productList == null) 0
        else productList!!.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var text: TextView

        init {
            text = itemView.findViewById(R.id.text)
        }
    }

}