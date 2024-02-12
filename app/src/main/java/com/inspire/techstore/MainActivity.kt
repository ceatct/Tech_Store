package com.inspire.techstore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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

        replaceFragment(MainFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(MainFragment())
                R.id.like -> replaceFragment(LikeFragment())
                R.id.cart -> replaceFragment(CartFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                else -> {}
            }
            true
        }

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

    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragments, fragment)
        fragmentTransaction.commit()

    }

}