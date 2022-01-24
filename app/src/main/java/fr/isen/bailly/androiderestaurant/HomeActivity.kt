package fr.isen.bailly.androiderestaurant

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    private fun showToastEntree(){
        val toast = Toast.makeText(this@HomeActivity, "Vous avez sélectionné l'entrée", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun showToastPlat(){
        val toast = Toast.makeText(this@HomeActivity, "Vous avez sélectionné le plat", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun showToastDessert(){
        val toast = Toast.makeText(this@HomeActivity, "Vous avez sélectionné le dessert", Toast.LENGTH_SHORT)
        toast.show()
    }
}