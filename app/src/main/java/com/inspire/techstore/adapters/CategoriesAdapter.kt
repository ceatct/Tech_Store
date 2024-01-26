package com.inspire.techstore.adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.inspire.techstore.R
import com.inspire.techstore.api.data.CategoriesModel
import com.inspire.techstore.fragments.ResultFragment

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

        holder.itemView.setOnClickListener {

            val category = productList?.get(position)
            val fragment = ResultFragment()

            fragment.arguments = Bundle().apply {
                putString("category", category)
            }

            (holder.itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragments, fragment)
                .addToBackStack(null)
                .commit()
        }

        holder.itemView.setOnLongClickListener {

            val category = productList?.get(position)
            val fragment = ResultFragment()

            fragment.arguments = Bundle().apply {
                putString("category", category)
            }

            (holder.itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragments, fragment)
                .addToBackStack(null)
                .commit()

            return@setOnLongClickListener false
        }
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