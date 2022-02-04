package fr.isen.bailly.androiderestaurant.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.bailly.androiderestaurant.R
import fr.isen.bailly.androiderestaurant.adapter.DetailPictureAdapter
import fr.isen.bailly.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.bailly.androiderestaurant.model.DishModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detail = intent.getSerializableExtra("dish_name") as DishModel
        binding.detailTitle.text = detail.name_fr
        binding.detailPrice.text = detail.prices[0].price + "€"
        binding.detailNumber.text = getString(R.string.initValue)
        binding.detailButtonPlus.text = getString(R.string.plus)
        binding.detailButtonMinus.text = getString(R.string.minus)
        binding.addToCartButton.text = getString(R.string.addToCart)
        binding.detailPicture.adapter = DetailPictureAdapter(this,detail.images)

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
}