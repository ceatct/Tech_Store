package com.inspire.techstore.fragments.models

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.inspire.techstore.api.RetroServiceInterface
import com.inspire.techstore.api.RetrofitInstance
import com.inspire.techstore.api.data.Address
import com.inspire.techstore.api.data.Geolocation
import com.inspire.techstore.api.data.Name
import com.inspire.techstore.api.data.User

class RegisterFragmentViewModel: ViewModel() {
    suspend fun register(email: String, username: String, password: String, firstname: String, lastname: String, phone: String, context: Context) {
        val service = RetrofitInstance.getRetrofitInstance().create(RetroServiceInterface::class.java)

        val user = User(
            email = email,
            username = username,
            password = password,
            name = Name(
                firstname = firstname,
                lastname = lastname
            ),
            address = Address(
                city = " ",
                street = " ",
                number = 0,
                zipcode = " ",
                geolocation = Geolocation(
                    lat = " ",
                    long = " "
                )
            ),
            phone = phone
        )

        val response = service.registerUser(user)
        if (response.isSuccessful) {
            Toast.makeText(context, "Complete", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, response.code(), Toast.LENGTH_SHORT).show()
        }
    }
}