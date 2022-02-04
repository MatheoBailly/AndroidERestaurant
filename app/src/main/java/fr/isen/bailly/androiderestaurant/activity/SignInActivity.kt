package fr.isen.bailly.androiderestaurant.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import fr.isen.bailly.androiderestaurant.R
import fr.isen.bailly.androiderestaurant.databinding.ActivitySignInBinding
import org.json.JSONObject

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInButton.setOnClickListener{

            if(verifyForm()){
                val surname = binding.signInSurnameInput.text.toString()
                val name = binding.signInNameInput.text.toString()
                val address = binding.signInAddressInput.text.toString()
                val mail = binding.signInMailInput.text.toString()
                val password = binding.signInPwdInput.text.toString()

                //http request to the API
                val queue= Volley.newRequestQueue(this)
                val url="http://test.api.catering.bluecodegames.com/user/register"
                val jsonObject= JSONObject()
                jsonObject.put("id_shop","1")
                jsonObject.put("firstname","$surname")
                jsonObject.put("lastname","$name")
                jsonObject.put("address","$address")
                jsonObject.put("email","$mail")
                jsonObject.put("password","$password")

                // Request a string response from the provided URL.
                val request = JsonObjectRequest(
                    Request.Method.POST,url,jsonObject,
                    { response ->
                        Log.d("", "$response")
                    }, {
                        // Error in request
                        Log.i("","Volley error: $it")
                    })

                // Volley request policy, only one time request to avoid duplicate transaction
                request.retryPolicy = DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    // 0 means no retry
                    0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
                    1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )

                // Add the volley post request to the request queue
                queue.add(request)
            }
        }
    }

    private fun verifyForm(): Boolean{
        var nb_error = 0

        nb_error += verifyEmpty(binding.signInNameInput,binding.signInName)
        nb_error += verifyEmpty(binding.signInNameInput,binding.signInName)
        nb_error += verifyEmpty(binding.signInAddressInput,binding.signInAddress)
        nb_error += verifyEmpty(binding.signInMailInput,binding.signInMail)
        nb_error += verifyEmpty(binding.signInMailInput,binding.signInMail)

        nb_error += verifyMail(binding.signInMailInput,binding.signInMail)

        nb_error += verifyPwd(binding.signInPwdInput,binding.signInPwd)

        val error = nb_error == 0
        return error

    }

    private fun verifyEmpty(signInInput: TextInputEditText, signInParam: TextInputLayout): Int {
        val errorEmptyField = getString(R.string.empty_field)
        return if (signInInput.text.toString().trim().isEmpty()){
            signInParam.error = errorEmptyField
            1
        }else{
            signInParam.error = null
            0
        }
    }

    private fun verifyMail(signInMailInput: TextInputEditText, signInMail: TextInputLayout): Int{
        val errorInvalidMail = getString(R.string.invalid_mail)
        return if (""".+\@.+\..+""".toRegex().matches(signInMailInput.text.toString())){
            signInMail.error = null
            0
        }else{
            signInMail.error = errorInvalidMail
            1
        }
    }

    private fun verifyPwd(signInPwdInput: TextInputEditText, signInPwd: TextInputLayout): Int{
        val errorPwd = getString(R.string.error_pwd)
        return if (signInPwdInput.text.toString().length < 8){
            signInPwd.error = errorPwd
            1
        }else{
            signInPwd.error = null
            0
        }
    }

}