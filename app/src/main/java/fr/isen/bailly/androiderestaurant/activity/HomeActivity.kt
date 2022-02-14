package fr.isen.bailly.androiderestaurant.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import fr.isen.bailly.androiderestaurant.R
import fr.isen.bailly.androiderestaurant.databinding.ActivityHomeBinding

class HomeActivity : MenuActivity() {

    private lateinit var binding: ActivityHomeBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textWelcome.text = getString(R.string.home_welcome)
        binding.btnStarters.text = getString(R.string.home_starters)
        binding.btnMainDishes.text = getString(R.string.home_main_dishes)
        binding.btnDesserts.text = getString(R.string.home_desserts)

        binding.btnStarters.setOnClickListener{
            changeActivity(getString(R.string.home_starters))
        }

        binding.btnMainDishes.setOnClickListener{
            changeActivity(getString(R.string.home_main_dishes))
        }

        binding.btnDesserts.setOnClickListener{
            changeActivity(getString(R.string.home_desserts))
        }
    }

    private fun changeActivity( category: String ) {
        val changePage = Intent(this@HomeActivity, CategoryActivity::class.java)
        changePage.putExtra("category_type",category)
        startActivity(changePage)
    }
}