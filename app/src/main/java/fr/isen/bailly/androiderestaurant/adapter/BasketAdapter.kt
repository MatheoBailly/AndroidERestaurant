package fr.isen.bailly.androiderestaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.bailly.androiderestaurant.R
import fr.isen.bailly.androiderestaurant.model.BasketData
import fr.isen.bailly.androiderestaurant.databinding.BasketCellBinding

class BasketAdapter(private val dishes: MutableList<BasketData>, val onBeenClicked: (BasketData) -> Unit) : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    class BasketViewHolder(private val binding: BasketCellBinding): RecyclerView.ViewHolder(binding.root){
        val dishPicture = binding.dishPicture
        val dishName = binding.dishName
        val dishQuantity = binding.quantity
        val deleteItem = binding.trash
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val binding = BasketCellBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return BasketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val dish = dishes[position]
        holder.dishName.text = dish.name.name_fr

        Picasso.get()
            .load(dishes[position].name.images[0].ifEmpty { null })
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.dishPicture)

        holder.dishQuantity.text = "Quantité : " +dishes[position].quantity.toString()
        holder.deleteItem.setOnClickListener {
            if(position < dishes.size) {
                val elementToRemove = dishes[position]
                onBeenClicked.invoke(elementToRemove)
                dishes.remove(elementToRemove)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = dishes.size
}