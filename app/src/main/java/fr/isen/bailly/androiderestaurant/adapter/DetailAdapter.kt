package fr.isen.bailly.androiderestaurant.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.isen.bailly.androiderestaurant.fragment.DetailFragment

class DetailAdapter (binding: AppCompatActivity, val pictures : List<String>): FragmentStateAdapter(binding) {
    override fun getItemCount(): Int = pictures.size

    override fun createFragment(position: Int): Fragment {
        return DetailFragment.newInstance(pictures[position])
    }
}