package fr.isen.bailly.androiderestaurant

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainDishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dish)

        val category = intent.getStringExtra("category_type")
        findViewById<TextView>(R.id.mainDishTitle).text = category
    }
}