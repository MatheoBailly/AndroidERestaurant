package fr.isen.bailly.androiderestaurant.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.bailly.androiderestaurant.R
import fr.isen.bailly.androiderestaurant.adapter.DetailAdapter
import fr.isen.bailly.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.bailly.androiderestaurant.model.Basket
import fr.isen.bailly.androiderestaurant.model.BasketData
import fr.isen.bailly.androiderestaurant.model.DishModel
import java.io.File

class DetailActivity : MenuActivity() {

    private lateinit var binding: ActivityDetailBinding
    lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)

        val detail = intent.getSerializableExtra("dish_name") as DishModel
        binding.detailTitle.text = detail.name_fr
        binding.detailPrice.text = detail.prices[0].price + getString(R.string.euros)
        binding.detailNumber.text = getString(R.string.detail_init_value)
        binding.detailButtonPlus.text = getString(R.string.plus)
        binding.detailButtonMinus.text = getString(R.string.minus)
        binding.addToCartButton.text = getString(R.string.detail_add_to_basket)
        binding.detailImagesPager.adapter = DetailAdapter(this,detail.images)

        var ingredients = ""
        for (i in detail.ingredients) {
            ingredients += if(i==detail.ingredients.last()){
                (i.name_fr)
            }else{
                (i.name_fr) + ", "
            }
        }
        binding.detailIngredient.text = ingredients

        binding.detailButtonPlus.setOnClickListener{
            changeNumber(detail,1)
        }

        binding.detailButtonMinus.setOnClickListener{
            changeNumber(detail, 0)
        }

        binding.addToCartButton.setOnClickListener{
            val data = ArrayList<BasketData>()
            val filename = getString(R.string.basket_save)

            if (File(cacheDir.absolutePath + filename).exists()) {
                var basketNumberOfElement: Int
                Snackbar.make(it, getString(R.string.detail_add_to_basket), Snackbar.LENGTH_LONG).show()
                if (File(cacheDir.absolutePath + filename).readText().isNotEmpty()) {
                    val recup = File(cacheDir.absolutePath + filename).bufferedReader().readText()
                    val resultat = Gson().fromJson(recup, Basket::class.java)
                    basketNumberOfElement = resultat.quantity
                    for (j in resultat.name.indices) {
                        addToBasket(BasketData(resultat.name[j].name,resultat.name[j].quantity),data)
                    }

                    addToBasket(BasketData(detail, binding.detailNumber.toString().toInt()), data)
                    basketNumberOfElement += binding.detailNumber.toString().toInt()
                    val editor = sharedPreferences.edit()
                    editor.putInt(basketCount, basketNumberOfElement)
                    editor.apply()
                    File(cacheDir.absolutePath + filename).writeText(Gson().toJson(Basket(data, basketNumberOfElement)))
                } else {
                    File(cacheDir.absolutePath + filename).writeText(Gson().toJson(Basket(mutableListOf(BasketData(detail, binding.detailNumber.toString().toInt())),1)))
                    val editor = sharedPreferences.edit()
                    editor.putInt(basketCount, 1)
                    editor.apply()
                }
            }
            else{
                File(cacheDir.absolutePath + filename).writeText(Gson().toJson(Basket(mutableListOf(BasketData(detail, binding.detailNumber.toString().toInt())),1)))
                val editor = sharedPreferences.edit()
                editor.putInt(basketCount, 1)
                editor.apply()
            }
            startActivity(Intent(this, HomeActivity::class.java))
        }

    }

    private fun changeNumber(detail: DishModel, minusOrPlus: Int) {
        var nb = (binding.detailNumber.text as String).toInt()
        if(minusOrPlus==0){
            if(nb==1){
                //on ne peut pas commander zéro plat ou un nombre négatif de plat
            }else{
                nb --
            }
        }else{
            nb ++
        }
        binding.detailNumber.text = nb.toString()
        changePrice(detail, nb)
    }

    @SuppressLint("SetTextI18n")
    private fun changePrice(detail: DishModel, nb: Int) {
        var newPrice = detail.prices[0].price.toFloatOrNull()
        newPrice = newPrice?.times(nb)
        binding.detailPrice.text = "$newPrice €"
    }

    private fun addToBasket(itemToAdd: BasketData, data: ArrayList<BasketData>) {
        var bool = false

        for (i in data.indices)
            if (itemToAdd.name == data[i].name) {
                data[i].quantity += itemToAdd.quantity
                bool = true
            }
        if (bool == false) {
            data.add(BasketData(itemToAdd.name, itemToAdd.quantity))
        }

    }

    companion object {
        const val APP_PREFS = "app_prefs"
        const val basketCount = "basket_count"
    }
}