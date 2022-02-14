package fr.isen.bailly.androiderestaurant.model

import com.google.gson.annotations.SerializedName

data class LoginModel(@SerializedName("id") val userId: String)