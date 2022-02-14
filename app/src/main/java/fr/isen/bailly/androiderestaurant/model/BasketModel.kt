package fr.isen.bailly.androiderestaurant.model

import java.io.Serializable

data class Basket(val name: MutableList<BasketData>, var quantity: Int): Serializable

data class BasketData(val name: DishModel, var quantity : Int): Serializable