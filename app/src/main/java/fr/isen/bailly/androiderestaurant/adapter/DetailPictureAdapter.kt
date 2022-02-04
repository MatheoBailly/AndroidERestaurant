package fr.isen.bailly.androiderestaurant.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.isen.bailly.androiderestaurant.fragment.DetailPictureFragment

class DetailPictureAdapter (binding: AppCompatActivity, private val pictures : List<String>): FragmentStateAdapter(binding) {
    override fun getItemCount(): Int = pictures.size

    override fun createFragment(position: Int): Fragment {
        return DetailPictureFragment.newInstance(pictures[position])
    }
}