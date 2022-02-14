package fr.isen.bailly.androiderestaurant.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import fr.isen.bailly.androiderestaurant.R
import fr.isen.bailly.androiderestaurant.activity.BasketActivity
import fr.isen.bailly.androiderestaurant.activity.DetailActivity
import fr.isen.bailly.androiderestaurant.databinding.FragmentSignupBinding
import fr.isen.bailly.androiderestaurant.model.SignupModel
import org.json.JSONObject

class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding

    lateinit var mView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var buttonValidate = binding.validateButton
        buttonValidate.setOnClickListener() {
            signup()
            startActivity(Intent(requireContext(), BasketActivity::class.java))
        }
    }

    private fun signup() {
        val params = HashMap<String, String>()
        params["id_shop"] = "1"
        params["firstname"] = binding.surnameInput.text.toString()
        params["lastname"] = binding.nameInput.text.toString()
        params["address"] = binding.addressInput.text.toString()
        params["email"] = binding.emailInput.text.toString()
        params["password"] = binding.passwordInput.text.toString()
        var errorBool: Boolean
        errorBool = true
        if (TextUtils.isEmpty(binding.nameInput.text)) {
            binding.name.error = getString(R.string.error)
            errorBool = false
        } else binding.name.error = null
        if (TextUtils.isEmpty(binding.surnameInput.text)) {
            binding.surname.error = getString(R.string.error)
            errorBool = false
        } else binding.surname.error = null
        if (TextUtils.isEmpty(binding.emailInput.text)) {
            binding.email.error = getString(R.string.error)
            errorBool = false
        } else binding.email.error = null
        if (TextUtils.isEmpty(binding.addressInput.text)) {
            binding.address.error = getString(R.string.error)
            errorBool = false
        } else binding.address.error = null
        if (TextUtils.isEmpty(binding.passwordInput.text)) {
            binding.password.error = getString(R.string.error)
            errorBool = false
        }else{
            binding.password.error = null
        }
        if (errorBool) {
            val queue = Volley.newRequestQueue(requireContext())
            val url = "http://test.api.catering.bluecodegames.com/user/register"
            val jsonObject = JSONObject(params as HashMap<*, *>)
            val request = JsonObjectRequest(
                Request.Method.POST, url, jsonObject,
                { response ->
                    val signup = GsonBuilder().create().fromJson(response.toString(), SignupModel::class.java)
                    val editor = requireContext().getSharedPreferences(DetailActivity.APP_PREFS, Context.MODE_PRIVATE).edit()
                    editor.putString(USER_ID, signup.data.userId)
                    editor.apply()
                }, {
                })
            request.retryPolicy = DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,0,1f)
            queue.add(request)
        }else{
            Snackbar.make(binding.root, "Informations mal renseignées", Snackbar.LENGTH_LONG).show()
        }
    }

    companion object {
        const val USER_ID = "USER_ID"
    }

}