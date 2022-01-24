package fr.isen.bailly.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
//import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnStarter = findViewById<Button>(R.id.btnStarter)
        val btnMainDish = findViewById<Button>(R.id.btnMainDish)
        val btnDessert = findViewById<Button>(R.id.btnDessert)

        btnStarter.setOnClickListener{
            //Toast.makeText(this@HomeActivity, "Vous avez sélectionné l'entrée", Toast.LENGTH_SHORT).show()
            changeActivity(getString(R.string.home_starters))
        }

        btnMainDish.setOnClickListener{
            //val toast = Toast.makeText(this@HomeActivity, "Vous avez sélectionné le plat", Toast.LENGTH_SHORT).show()
            changeActivity(getString(R.string.home_main_dishes))
        }

        btnDessert.setOnClickListener{
            //val toast = Toast.makeText(this@HomeActivity, "Vous avez sélectionné le dessert", Toast.LENGTH_SHORT).show()
            changeActivity(getString(R.string.home_desserts))
        }
    }

    private fun changeActivity( category: String ) {
        val changePage = Intent(this@HomeActivity, MainDishActivity::class.java)
        changePage.putExtra("category_type",category)
        Log.i("INFO", "End of HomeActivity")
        startActivity(changePage)
    }
}