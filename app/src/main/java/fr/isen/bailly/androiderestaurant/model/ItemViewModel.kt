package fr.isen.bailly.androiderestaurant.model

import java.io.Serializable

data class ItemViewModel(val picture: Int, val name: String, val price: String) : Serializable {

}