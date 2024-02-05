package com.inspire.techstore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.inspire.techstore.R
import com.inspire.techstore.fragments.models.ProfileFragmentViewModel

class ProfileFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[ProfileFragmentViewModel::class.java]
    }

    private lateinit var loginGrid : GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        loginGrid = view.findViewById(R.id.login)

        loginGrid.setOnClickListener {
            viewModel.switchFragment((activity as AppCompatActivity?)!!, RegisterFragment(), R.id.fragments)

            val header = requireActivity().findViewById<ViewGroup>(R.id.include)
            val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            header.visibility = View.GONE
            bottomNavigationView.visibility = View.GONE
        }

        return view
    }

}