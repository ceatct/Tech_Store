package com.inspire.techstore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.inspire.techstore.R
import com.inspire.techstore.db.card.Card
import com.inspire.techstore.db.card.CardViewModel

class CardsAdapter(private var viewModelProvider: ViewModelProvider) : RecyclerView.Adapter<CardsAdapter.MyViewHolder>() {

    private var cardsList: List<Card>? = null
    private lateinit var cardViewModel: CardViewModel

    fun setProductList(cardsList: List<Card>?){
        this.cardsList = cardsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        cardViewModel = viewModelProvider[CardViewModel::class.java]
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        cardViewModel = viewModelProvider[CardViewModel::class.java]

        if(cardsList?.get(position)?.company.equals("Visa")){
            holder.logo.setImageResource(R.drawable.ic_visa)
        }
        else if(cardsList?.get(position)?.company.equals("Mastercard")){
            holder.logo.setImageResource(R.drawable.ic_mastercard)
        }
        else if(cardsList?.get(position)?.company.equals("Undefined")){
            holder.logo.setImageResource(R.drawable.ic_question)
        }

        val cardNumber = cardsList?.get(position)?.number.toString()
        val formattedCardNumber = cardNumber.replace(Regex("(.{4})"), "$1 ")
        holder.cardNumber.text = formattedCardNumber

        holder.cardDate.text = cardsList?.get(position)?.date.toString()
        holder.cardHolderName.text = cardsList?.get(position)?.holder.toString()

        holder.action.setOnClickListener{
            deleteItem(cardsList?.get(position)!!)
            Toast.makeText(holder.itemView.context, holder.itemView.context.getString(R.string.deleted), Toast.LENGTH_SHORT).show()
        }

    }

    private fun deleteItem(card: Card){
        cardViewModel.deleteCard(card)
    }

    override fun getItemCount(): Int {
        return if(cardsList == null) 0
        else cardsList!!.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var logo: ImageView
        var cardNumber: TextView
        var cardDate: TextView
        var cardHolderName: TextView
        var action: ImageView

        init {
            logo = itemView.findViewById(R.id.logo)
            cardNumber = itemView.findViewById(R.id.card_number)
            cardDate = itemView.findViewById(R.id.card_date)
            cardHolderName = itemView.findViewById(R.id.card_holderName)
            action = itemView.findViewById(R.id.action)
        }
    }

}