package com.inspire.techstore

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.inspire.techstore.databinding.ActivityMainBinding
import com.inspire.techstore.fragments.CartFragment
import com.inspire.techstore.fragments.LikeFragment
import com.inspire.techstore.fragments.MainFragment
import com.inspire.techstore.fragments.ProfileFragment
import com.inspire.techstore.fragments.ResultFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            val currentFragment = supportFragmentManager.getFragment(savedInstanceState, "currentFragment")
            if (currentFragment != null) {
                replaceFragment(currentFragment)
            } else {
                replaceFragment(MainFragment())
            }
        } else {
            replaceFragment(MainFragment())
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(MainFragment())
                R.id.cart -> replaceFragment(CartFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                else -> {}
            }
            true
        }

        binding.include.favorite.setOnClickListener{replaceFragment(LikeFragment())}

        binding.include.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val name = binding.include.search.query.toString()
                val fragment = ResultFragment()

                fragment.arguments = Bundle().apply {
                    putString("name", name)
                }

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragments, fragment)
                    .addToBackStack(null)
                    .commit()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        val permissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        } else {
            TODO("VERSION.SDK_INT < TIRAMISU")
        }

        if (permissionState == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragments)
        if (currentFragment != null) {
            supportFragmentManager.putFragment(outState, "currentFragment", currentFragment)
        }
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragments, fragment)
        fragmentTransaction.commit()
    }

}
