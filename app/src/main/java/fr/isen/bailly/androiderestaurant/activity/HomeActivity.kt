package fr.isen.bailly.androiderestaurant.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
//import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.bailly.androiderestaurant.R
import fr.isen.bailly.androiderestaurant.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

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
            //Toast.makeText(this@HomeActivity, "Vous avez sélectionné l'entrée", Toast.LENGTH_SHORT).show()
            changeActivity(getString(R.string.home_starters))
        }

        binding.btnMainDishes.setOnClickListener{
            //val toast = Toast.makeText(this@HomeActivity, "Vous avez sélectionné le plat", Toast.LENGTH_SHORT).show()
            changeActivity(getString(R.string.home_main_dishes))
        }

        binding.btnDesserts.setOnClickListener{
            //val toast = Toast.makeText(this@HomeActivity, "Vous avez sélectionné le dessert", Toast.LENGTH_SHORT).show()
            changeActivity(getString(R.string.home_desserts))
        }
    }

    private fun changeActivity( category: String ) {
        val changePage = Intent(this@HomeActivity, CategoryActivity::class.java)
        changePage.putExtra("category_type",category)
        Log.i("INFO", "End of HomeActivity")
        startActivity(changePage)
    }
}