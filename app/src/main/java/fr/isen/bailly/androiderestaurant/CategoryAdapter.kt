package fr.isen.bailly.androiderestaurant

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.bailly.androiderestaurant.databinding.CategoryCellBinding
import fr.isen.bailly.androiderestaurant.model.DishModel

class CategoryAdapter(val dishes: List<DishModel>, private val onDishClicked: (DishModel) -> Unit):RecyclerView.Adapter<CategoryAdapter.DishViewHolder>() {

    class DishViewHolder(val binding: CategoryCellBinding): RecyclerView.ViewHolder(binding.root){
        val dishPicture = binding.dishPicture
        val dishName = binding.dishName
        val dishPrice = binding.dishPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val binding = CategoryCellBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return DishViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dish = dishes[position]

        holder.dishName.text = dish.name_fr
        holder.dishPrice.text = dish.prices[0].price +"€"

        Picasso.get()
            .load(if(dish.images[0].isNotEmpty())dish.images[0] else null)
            .error(R.drawable.fond).placeholder(R.drawable.fond)
            .fit()
            .centerInside()
            //.resize(60,60)
            .into(holder.dishPicture)

        holder.itemView.setOnClickListener {
            onDishClicked(dish)
        }
    }

    override fun getItemCount(): Int = dishes.size

}