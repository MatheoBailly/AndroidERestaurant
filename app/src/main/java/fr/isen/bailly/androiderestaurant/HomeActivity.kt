package fr.isen.bailly.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnStarter = findViewById<Button>(R.id.btnStarter)
        val btnMainDish = findViewById<Button>(R.id.btnMainDish)
        val btnDessert = findViewById<Button>(R.id.btnDessert)

        btnStarter.setOnClickListener{
            val toast = Toast.makeText(this@HomeActivity, "Vous avez sélectionné l'entrée", Toast.LENGTH_SHORT)
            toast.show()
        }

        btnMainDish.setOnClickListener{
            val toast = Toast.makeText(this@HomeActivity, "Vous avez sélectionné le plat", Toast.LENGTH_SHORT)
            toast.show()
        }

        btnDessert.setOnClickListener{
            val toast = Toast.makeText(this@HomeActivity, "Vous avez sélectionné le dessert", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}