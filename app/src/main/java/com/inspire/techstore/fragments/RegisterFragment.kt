package com.inspire.techstore.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.inspire.techstore.R
import com.inspire.techstore.fragments.models.RegisterFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[RegisterFragmentViewModel::class.java]
    }

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private lateinit var registrationBt: Button
    private lateinit var loginBt: Button

    private lateinit var emailet: TextInputEditText
    private lateinit var usernameet: TextInputEditText
    private lateinit var passwordet: TextInputEditText
    private lateinit var nameet: TextInputEditText
    private lateinit var lastnameet: TextInputEditText
    private lateinit var phoneet: TextInputEditText

    private lateinit var usernamelg: TextInputEditText
    private lateinit var passwordlg: TextInputEditText

    private lateinit var login: TextView

    private lateinit var registrationLayout: LinearLayout
    private lateinit var alreadyLayout: LinearLayout
    private lateinit var havenLayout: LinearLayout
    private lateinit var loginLayout: LinearLayout

    private lateinit var back : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_register, container, false)

        registrationBt = view.findViewById(R.id.registration)
        loginBt = view.findViewById(R.id.login_bt)

        emailet = view.findViewById(R.id.email)
        usernameet = view.findViewById(R.id.username)
        passwordet = view.findViewById(R.id.password)
        nameet = view.findViewById(R.id.name)
        lastnameet = view.findViewById(R.id.lastname)
        phoneet = view.findViewById(R.id.phone)

        usernamelg = view.findViewById(R.id.username_login)
        passwordlg = view.findViewById(R.id.password_login)

        login = view.findViewById(R.id.login)
        registrationLayout = view.findViewById(R.id.registration_ll)
        alreadyLayout = view.findViewById(R.id.already_ll)
        havenLayout = view.findViewById(R.id.havent_ll)
        loginLayout = view.findViewById(R.id.login_ll)

        back = view.findViewById(R.id.back)

        back.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragments, ProfileFragment())
            fragmentTransaction.commit()
        }

        login.setOnClickListener {
            registrationLayout.visibility = GONE
            alreadyLayout.visibility = GONE

            havenLayout.visibility = VISIBLE
            loginLayout.visibility = VISIBLE
        }

        havenLayout.setOnClickListener {
            havenLayout.visibility = GONE
            loginLayout.visibility = GONE

            registrationLayout.visibility = VISIBLE
            alreadyLayout.visibility = VISIBLE
        }

        val allFieldsFilled = listOf(
            emailet.text.toString(),  // Access text properties directly
            usernameet.text.toString(),
            passwordet.text.toString(),
            nameet.text.toString(),
            lastnameet.text.toString(),
            phoneet.text.toString()
        ).all { it.isNotEmpty() }

        val allFieldsFilledLogin = listOf(usernamelg.text.toString(), passwordlg.text.toString()).all { it.isNotEmpty() }

        registrationBt.setOnClickListener {
            if (allFieldsFilled) {
                coroutineScope.launch {
                    viewModel.register(emailet.text.toString(), usernameet.text.toString(), passwordet.text.toString(), nameet.text.toString(), lastnameet.text.toString(), phoneet.text.toString(), requireContext())
                }
            } else {
                Toast.makeText(requireContext(), R.string.fill, Toast.LENGTH_SHORT).show()
            }
        }

        loginBt.setOnClickListener{

            if(allFieldsFilledLogin){
                coroutineScope.launch {
                   // viewModel.register(email.toString(), username.toString(), password.toString(), name.toString(), lastname.toString(), phone.toString(), requireContext())
                }
            }
            else Toast.makeText(requireContext(), R.string.fill, Toast.LENGTH_SHORT).show()

        }

        return view
    }

}