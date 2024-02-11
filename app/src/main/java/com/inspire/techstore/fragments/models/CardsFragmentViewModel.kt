package com.inspire.techstore.fragments.models

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inspire.techstore.R
import com.inspire.techstore.db.card.Card
import com.inspire.techstore.db.card.CardViewModel
import com.inspire.techstore.db.card.DetectCardCompany

class CardsFragmentViewModel(private var viewModelProvider: ViewModelProvider): ViewModel()  {

    private val detectCardCompany = DetectCardCompany()
    private lateinit var cardViewModel: CardViewModel

    fun addCard(context: Context){
        cardViewModel = viewModelProvider[CardViewModel::class.java]

        val dialogView = LayoutInflater.from(context).inflate(R.layout.layout_card_input, null)
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setView(dialogView)

        val alertDialog = alertDialogBuilder.create()

        val addButton = dialogView.findViewById<Button>(R.id.add)

        addButton.setOnClickListener {
            val cardNumber = dialogView.findViewById<EditText>(R.id.card_number).text?.toString()
            val cardDateYY = dialogView.findViewById<EditText>(R.id.card_date_yy).text?.toString()
            val cardDateMM = dialogView.findViewById<EditText>(R.id.card_date_mm).text?.toString()
            val cardHolderName = dialogView.findViewById<EditText>(R.id.card_holderName).text?.toString()
            val cardCvv = dialogView.findViewById<EditText>(R.id.card_cvv).text?.toString()

            if (cardNumber!!.length < 16 || cardDateYY!!.length < 2 || cardDateMM!!.length < 2 || cardHolderName.isNullOrEmpty() || cardCvv!!.length < 3) {
                Toast.makeText(context, "FILL ALL!", Toast.LENGTH_SHORT).show()
            } else {
                val card = Card(cardNumber.toLong(), "$cardDateMM/$cardDateYY", cardCvv, cardHolderName, detectCardCompany.getCardType(cardNumber))
                cardViewModel.addCard(card)
                alertDialog.dismiss()
            }
        }
        alertDialog.show()
    }

}