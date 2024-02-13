package com.inspire.techstore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.inspire.techstore.R
import com.inspire.techstore.fragments.models.ProfileFragmentViewModel

class ProfileFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[ProfileFragmentViewModel::class.java]
    }

    private lateinit var loginImageView : ImageView
    private lateinit var walletImageView : ImageView
    private lateinit var ordersHistoryImageView : ImageView
    private lateinit var supportImageView : ImageView
    private lateinit var phoneImageView : ImageView
    private lateinit var mailImageView : ImageView
    private lateinit var instagramImageView : ImageView
    private lateinit var siteImageView : ImageView
    private lateinit var aboutImageView : ImageView
    private lateinit var addressImageView : ImageView

    private lateinit var loginTextView : TextView
    private lateinit var walletTextView : TextView
    private lateinit var ordersHistoryTextView : TextView
    private lateinit var supportTextView : TextView
    private lateinit var phoneTextView : TextView
    private lateinit var mailTextView : TextView
    private lateinit var instagramTextView : TextView
    private lateinit var siteTextView : TextView
    private lateinit var aboutTextView : TextView
    private lateinit var addressTextView : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        loginImageView = view.findViewById(R.id.login_image)
        loginTextView = view.findViewById(R.id.login_text)
        walletImageView = view.findViewById(R.id.wallet_image)
        walletTextView = view.findViewById(R.id.wallet_text)
        ordersHistoryImageView = view.findViewById(R.id.orders_image)
        ordersHistoryTextView = view.findViewById(R.id.orders_text)
        supportImageView = view.findViewById(R.id.support_image)
        supportTextView = view.findViewById(R.id.support_text)
        phoneImageView = view.findViewById(R.id.phone_image)
        phoneTextView = view.findViewById(R.id.phone_text)
        mailImageView = view.findViewById(R.id.mail_image)
        mailTextView = view.findViewById(R.id.mail_text)
        instagramImageView = view.findViewById(R.id.inst_image)
        instagramTextView = view.findViewById(R.id.inst_text)
        siteImageView = view.findViewById(R.id.site_image)
        siteTextView = view.findViewById(R.id.site_text)
        aboutImageView = view.findViewById(R.id.about_image)
        aboutTextView = view.findViewById(R.id.about_text)
        addressImageView = view.findViewById(R.id.address_image)
        addressTextView = view.findViewById(R.id.address_text)


        listOf(loginImageView, loginTextView).forEach { it.setOnClickListener { viewModel.switchFragment((activity as AppCompatActivity?)!!, RegisterFragment(), R.id.fragments) }}


        listOf(walletImageView, walletTextView).forEach { it.setOnClickListener { viewModel.switchFragment((activity as AppCompatActivity?)!!, CardsFragment(), R.id.fragments) }}

        //add orders history fragment
        listOf(ordersHistoryImageView, ordersHistoryTextView).forEach { it.setOnClickListener { viewModel.switchFragment((activity as AppCompatActivity?)!!, ResultFragment(), R.id.fragments) }}


        listOf(supportImageView, supportTextView).forEach { it.setOnClickListener { Toast.makeText(requireContext(), "Open site with support chat", Toast.LENGTH_SHORT).show() }}


        listOf(phoneImageView, phoneTextView).forEach { it.setOnClickListener { viewModel.makeCall(requireContext(), phoneTextView.text.toString()) }}


        listOf(mailImageView, mailTextView).forEach { it.setOnClickListener { viewModel.openGmail(requireContext(), mailTextView.text.toString()) }}


        listOf(instagramImageView, instagramTextView).forEach { it.setOnClickListener { viewModel.openBrowser(requireContext(), "https://www.instagram.com/") }}


        listOf(siteImageView, siteTextView).forEach { it.setOnClickListener { viewModel.openBrowser(requireContext(), "https://www.google.com/") }}


        listOf(aboutImageView, aboutTextView).forEach { it.setOnClickListener { viewModel.openBrowser(requireContext(), "https://www.google.com/") }}


        listOf(addressImageView, addressTextView).forEach { it.setOnClickListener { viewModel.openBrowser(requireContext(), "https://www.google.com/") }}

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