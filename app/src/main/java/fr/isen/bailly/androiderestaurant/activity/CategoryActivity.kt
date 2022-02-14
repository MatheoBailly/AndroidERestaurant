package fr.isen.bailly.androiderestaurant.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.bailly.androiderestaurant.adapter.CategoryAdapter
import fr.isen.bailly.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.bailly.androiderestaurant.model.DishModel
import fr.isen.bailly.androiderestaurant.model.DishResult
import org.json.JSONObject

class CategoryActivity : MenuActivity() {

    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var categoryType = intent.getStringExtra("category_type")
        binding.categoryTitle.text = categoryType
        if (categoryType != null) {
            loadDishesFromCategory(categoryType)
        }

    }

    private fun loadDishesFromCategory(category: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val jsonObject = JSONObject()
        jsonObject.put("id_shop", "1")
        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            { response ->

                val gson = Gson()
                val dishresult = gson.fromJson(response.toString(), DishResult::class.java)

                displayDishes(
                    dishresult.data.firstOrNull() { it.name_fr == category }?.items ?: listOf()
                )
            }, {
                Log.e("DishActivity", "Erreur lors de la récupération de la liste des plats")
            })

        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,

            0,
            1f
        )
        queue.add(request)
    }

    private fun displayDishes(dishresult: List<DishModel>) {

        val recyclerview = binding.dishList

        recyclerview.layoutManager = LinearLayoutManager(this)

        binding.dishList.layoutManager = LinearLayoutManager(this)
        binding.dishList.adapter = CategoryAdapter(dishresult) {

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("dish_name", it)
            startActivity(intent)
        }
    }

}