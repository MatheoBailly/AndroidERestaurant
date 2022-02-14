package fr.isen.bailly.androiderestaurant.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.isen.bailly.androiderestaurant.R
import fr.isen.bailly.androiderestaurant.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("images_url")?.let { imagesUrl ->
            Picasso.get()
                .load(imagesUrl.ifEmpty { null })
                .error(R.drawable.outline_visibility_off_white_24dp)
                .placeholder(R.drawable.outline_cached_white_24dp)
                .fit()
                .centerInside()
                .into(binding.detailImages)
        }
    }

    companion object{
        fun newInstance(imagesUrl: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString("images_url", imagesUrl)
                }
            }
    }
}