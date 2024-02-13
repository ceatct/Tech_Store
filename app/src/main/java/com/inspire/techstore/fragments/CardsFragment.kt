package com.inspire.techstore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.inspire.techstore.R
import com.inspire.techstore.adapters.CardsAdapter
import com.inspire.techstore.db.card.CardViewModel
import com.inspire.techstore.fragments.models.CardsFragmentViewModel

class CardsFragment : Fragment() {

    private val viewModel by lazy {
        val viewModelProvider = ViewModelProvider(this)
        CardsFragmentViewModel(viewModelProvider)
    }

    private val cardAdapter by lazy {
        val viewModelProvider = ViewModelProvider(this)
        CardsAdapter(viewModelProvider)
    }

    private lateinit var cardViewModel: CardViewModel

    private lateinit var recyclerCard: RecyclerView
    private lateinit var emptyTextView: TextView
    private lateinit var addButton: FloatingActionButton
    private lateinit var backActionLayout : LinearLayout

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cards, container, false)

        cardViewModel = ViewModelProvider(this)[CardViewModel::class.java]

        recyclerCard = view.findViewById(R.id.recycler_cards)
        emptyTextView = view.findViewById(R.id.empty)
        addButton = view.findViewById(R.id.add_button)
        backActionLayout = view.findViewById(R.id.back)

        recyclerCard.adapter = cardAdapter

        backActionLayout.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragments, MainFragment())
            fragmentTransaction.commit()
        }

        addButton.setOnClickListener{
            viewModel.addCard(requireContext())
        }

        cardViewModel.readAllData.observe(viewLifecycleOwner) { card ->
            cardAdapter.setProductList(card)
            cardAdapter.notifyDataSetChanged()

            if (card.isEmpty()) {
                emptyTextView.visibility = View.VISIBLE
            } else {
                emptyTextView.visibility = View.GONE
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        })
    }

}