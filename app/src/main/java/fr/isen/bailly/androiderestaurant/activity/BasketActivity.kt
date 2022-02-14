package fr.isen.bailly.androiderestaurant.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.bailly.androiderestaurant.R
import fr.isen.bailly.androiderestaurant.adapter.BasketAdapter
import fr.isen.bailly.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.bailly.androiderestaurant.fragment.LoginFragment.Companion.USER_ID
import fr.isen.bailly.androiderestaurant.model.Basket
import fr.isen.bailly.androiderestaurant.model.BasketData
import java.io.File

class BasketActivity : MenuActivity() {
    private lateinit var binding: ActivityBasketBinding
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences(DetailActivity.APP_PREFS, Context.MODE_PRIVATE)
        binding.basketTitle.text = getString(R.string.basket_title)
        verifIfConnect()

        val filename = getString(R.string.basket_save)
        if (File(cacheDir.absolutePath + filename).exists()) {
            val recup = File(cacheDir.absolutePath + filename).readText()
            val resultat = Gson().fromJson(recup, Basket::class.java)
            val data = ArrayList<BasketData>()
            for (j in resultat.name.indices) {
                data.add(BasketData(resultat.name[j].name, resultat.name[j].quantity))
            }

            displayDishes(Basket(data, resultat.quantity))
        }
        var buttonConnection = binding.connectionButton
        buttonConnection.setOnClickListener {
            if (binding.connectionButton.text == "Commander") {
                startActivity(Intent(this, OrderActivity::class.java))
            } else {
                startActivity(Intent(this, ConnectionActivity::class.java))
            }
        }
    }

    private fun displayDishes(dishResult: Basket) {
        binding.basketItem.layoutManager = LinearLayoutManager(this)
        binding.basketItem.adapter = BasketAdapter(dishResult.name) {
            dishResult.name.remove(it)
            updateBasket(dishResult)
            invalidateOptionsMenu()
        }


    }

    private fun updateBasket(basket: Basket) {
        val filename = getString(R.string.basket_save)
        dishCountInPref(basket)
        File(cacheDir.absolutePath + filename).writeText(
            GsonBuilder().create().toJson(basket)
        )
    }

    private fun dishCountInPref(basket: Basket) {
        val count = basket.name.sumOf { it.quantity }
        basket.quantity = count
        val editor = getSharedPreferences(DetailActivity.APP_PREFS, Context.MODE_PRIVATE).edit()
        editor.putInt(DetailActivity.basketCount, count)
        editor.apply()

    }

    private fun verifIfConnect() {
        val userIdSave =
            getSharedPreferences(DetailActivity.APP_PREFS, MODE_PRIVATE).contains(USER_ID)
        if (userIdSave) {
            binding.connectionButton.text = getString(R.string.basket_command_button)
            Log.e("","")
        }
    }
}