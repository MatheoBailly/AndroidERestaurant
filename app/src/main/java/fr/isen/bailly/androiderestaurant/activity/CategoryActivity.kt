package fr.isen.bailly.androiderestaurant.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category=intent.getStringExtra("category_type")
        binding.categoryTitle.text=category

        binding.dishList.layoutManager=LinearLayoutManager(this)

        //http request to the API
        val queue=Volley.newRequestQueue(this)
        val url="http://test.api.catering.bluecodegames.com/menu"
        val jsonObject=JSONObject()
        jsonObject.put("id_shop","1")


        // Request a string response from the provided URL.
        val request = JsonObjectRequest(
            Request.Method.POST,url,jsonObject,
            { response ->
                val gson = Gson()
                val dishResult = gson.fromJson(response.toString(), DishResult::class.java)
                displayDishes(dishResult.data.firstOrNull { it.name_fr == category }?.items ?: listOf())
                Log.d("", "$response")
            }, {
                // Error in request
                Log.i("","Volley error: $it")
            })

        // Volley request policy, only one time request to avoid duplicate transaction
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            // 0 means no retry
            0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
            1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        // Add the volley post request to the request queue
        queue.add(request)
    }

    private fun displayDishes (dishResult:List<DishModel>){
        // getting the recyclerview by its id
        binding.dishList.layoutManager = LinearLayoutManager(this)
        binding.dishList.adapter = CategoryAdapter(dishResult){
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("dish_name", it)
            startActivity(intent)
        }

    }
}

