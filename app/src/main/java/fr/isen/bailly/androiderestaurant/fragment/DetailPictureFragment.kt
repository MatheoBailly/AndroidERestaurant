package fr.isen.bailly.androiderestaurant.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import fr.isen.bailly.androiderestaurant.R
import fr.isen.bailly.androiderestaurant.databinding.FragmentDetailPictureBinding

class DetailPictureFragment : Fragment() {

    private lateinit var binding: FragmentDetailPictureBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("picture_url")?.let { pictureUrl ->
            Picasso.get()
                .load(if (pictureUrl.isNotEmpty()) pictureUrl else null)
                .error(R.drawable.fond)
                .placeholder(R.drawable.fond)
                .fit()
                .into(binding.detailPictures)
        }
    }

    companion object{

        fun newInstance(pictureUrl: String) =
            DetailPictureFragment().apply {
                arguments = Bundle().apply {
                    putString("picture_url", pictureUrl)
                }
            }
    }
}