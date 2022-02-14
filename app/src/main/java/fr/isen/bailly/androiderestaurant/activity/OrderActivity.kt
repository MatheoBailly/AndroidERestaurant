package fr.isen.bailly.androiderestaurant.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.isVisible
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.Gson
import fr.isen.bailly.androiderestaurant.R
import fr.isen.bailly.androiderestaurant.databinding.ActivityOrderBinding
import fr.isen.bailly.androiderestaurant.fragment.LoginFragment
import fr.isen.bailly.androiderestaurant.model.Basket
import org.json.JSONObject
import java.io.File

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        animation(0)
        val filename = getString(R.string.basket_save)
        if (File(cacheDir.absolutePath + filename).exists()) {
            if (File(cacheDir.absolutePath + filename).readText().isNotEmpty()) {
                val jsonData = JSONObject()
                jsonData.put("msg", File(cacheDir.absolutePath + filename).readText())
                jsonData.put("id_shop", "1")
                jsonData.put("id_user", getSharedPreferences(DetailActivity.APP_PREFS,MODE_PRIVATE).getString(LoginFragment.USER_ID, ""))

                val queue = Volley.newRequestQueue(this)
                val url = "http://test.api.catering.bluecodegames.com/user/order"
                val jsonObject = jsonData
                val recup = File(cacheDir.absolutePath + filename).readText()
                val resultat = Gson().fromJson(recup, Basket::class.java)
                val request = JsonObjectRequest(
                    Request.Method.POST, url, jsonObject, {
                        if (resultat.quantity == 0){
                            displayPage(true)
                        }else{
                            displayPage(false)
                        }
                    }, {
                        displayPage(true)
                    })
                request.retryPolicy = DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,0,1f)
                queue.add(request)
            }else{
                displayPage(true)
            }
        }else{
            displayPage(true)
        }
    }

    private fun displayPage(error: Boolean) {
        if (error) {
            animation(2)
            binding.orderStatusOK.isVisible = false
            binding.orderStatusError.isVisible = true
        } else {
            animation(1)
            binding.orderStatusOK.isVisible = true
            binding.orderStatusError.isVisible = false
        }
    }

    private fun animation(verif : Int) {
        val imageView: ImageView = binding.animation
        if(verif==0){
            Glide.with(this).load(R.drawable.hug).into(imageView)
        }
        if(verif==1){
            Glide.with(this).load(R.drawable.checkmark).into(imageView)
        }
        if(verif==2){
            Glide.with(this).load(R.drawable.thumbdown).into(imageView)
        }
    }

}