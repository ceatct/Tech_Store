package com.inspire.techstore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.inspire.techstore.R
import com.inspire.techstore.adapters.CartAdapter
import com.inspire.techstore.adapters.SelectInputAdapter
import com.inspire.techstore.adapters.TextInputAdapter
import com.inspire.techstore.api.data.ProductModelItem
import com.inspire.techstore.api.data.SelectInputModel
import com.inspire.techstore.db.history.History
import com.inspire.techstore.fragments.models.OrderFragmentViewModel
import java.text.SimpleDateFormat
import java.util.Date

class OrderFragment : Fragment() {

    private val viewModel by lazy {
        val viewModelProvider = ViewModelProvider(this)
        OrderFragmentViewModel(viewModelProvider)
    }

    private val productAdapter by lazy {
        CartAdapter()
    }

    private lateinit var recyclerCart: RecyclerView
    private lateinit var recyclerContact: RecyclerView
    private lateinit var recyclerAddress: RecyclerView
    private lateinit var recyclerDelivery: RecyclerView
    private lateinit var recyclerPay: RecyclerView
    private lateinit var backActionLayout : LinearLayout
    private lateinit var totalPrice : TextView
    private lateinit var confirmButton : Button
    private lateinit var progressBarOrder: ProgressBar

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_order, container, false)

        recyclerCart = view.findViewById(R.id.recycler_cart)
        recyclerContact = view.findViewById(R.id.recycler_contact)
        recyclerAddress = view.findViewById(R.id.recycler_address)
        recyclerDelivery = view.findViewById(R.id.recycler_delivery)
        recyclerPay = view.findViewById(R.id.recycler_pay)
        backActionLayout = view.findViewById(R.id.back)
        totalPrice = view.findViewById(R.id.total)
        confirmButton = view.findViewById(R.id.confirm)
        progressBarOrder = view.findViewById(R.id.order_progress)

        val contactList = listOf(getString(R.string.name), getString(R.string.lastname), getString(R.string.email))
        val addressList = listOf(getString(R.string.city), getString(R.string.department))

        val deliveryList = listOf(
            SelectInputModel(getString(R.string.novaposhta), R.drawable.novaposhta),
            SelectInputModel(getString(R.string.ukrposhta), R.drawable.ukrposhta),
            SelectInputModel(getString(R.string.justin), R.drawable.justin))

        val payList = listOf(
            SelectInputModel(getString(R.string.bycard), R.drawable.ic_visa),
            SelectInputModel(getString(R.string.googlepay), R.drawable.googlepay),
            SelectInputModel(getString(R.string.applepay), R.drawable.applepay))

        recyclerCart.adapter = productAdapter

        val contactInputAdapter = TextInputAdapter(contactList)
        val addressInputAdapter = TextInputAdapter(addressList)
        val deliverySelectAdapter = SelectInputAdapter(deliveryList)
        val paySelectAdapter = SelectInputAdapter(payList)
        recyclerContact.adapter = contactInputAdapter
        recyclerAddress.adapter = addressInputAdapter
        recyclerDelivery.adapter = deliverySelectAdapter
        recyclerPay.adapter = paySelectAdapter

        backActionLayout.setOnClickListener {
            back()
        }

        val historyProductList = ArrayList<ProductModelItem>()

        viewModel.getLiveDataObserver().observe(viewLifecycleOwner) { products ->
            if (products != null) {
                productAdapter.setProductList(products)
                productAdapter.notifyDataSetChanged()

                historyProductList.clear()
                historyProductList.addAll(products)

                if (products.isEmpty()) {
                    progressBarOrder.visibility = View.VISIBLE
                } else {
                    progressBarOrder.visibility = View.GONE
                }

            } else {
                Toast.makeText(context, getString(R.string.error_product), Toast.LENGTH_SHORT).show()
            }
        }

        confirmButton.setOnClickListener{
            if(!contactInputAdapter.isAllFieldsFilled() || !addressInputAdapter.isAllFieldsFilled()
                && !deliverySelectAdapter.isItemSelected() || !paySelectAdapter.isItemSelected() )
            {
                Toast.makeText(context, getString(R.string.fill), Toast.LENGTH_SHORT).show()
            }
            else{
                val orderNumber = java.util.Random().nextInt(99999 - 1 + 1) + 99999
                val date = SimpleDateFormat("dd-MM-yyyy").format(Date()).toString()

                viewModel.liveDataTotalPrice.observe(viewLifecycleOwner) { total ->
                    val history = History(orderNumber, date, "Completed", total, historyProductList)
                    viewModel.addToHistory(history)
                }
                Toast.makeText(context, getString(R.string.completed), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.liveDataTotalPrice.observe(viewLifecycleOwner) { total ->
            totalPrice.text = "$ $total"
        }

        viewModel.loadData()

        return view
    }

    private fun back(){
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragments, CartFragment())
        fragmentTransaction.commit()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                back()
            }
        })
    }

}