package fr.isen.bailly.androiderestaurant.model

import com.google.gson.annotations.SerializedName

data class SignupModel(@SerializedName("data") val data: LoginModel, @SerializedName("code") val code: Int)