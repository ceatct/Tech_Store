package com.inspire.techstore.fragments.models

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel

class ProfileFragmentViewModel : ViewModel() {

    fun openBrowser(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context.startActivity(intent)
    }

    fun makeCall(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        context.startActivity(intent)
    }

    fun openGmail(context: Context, recipientEmail: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
        context.startActivity(intent)
    }

}