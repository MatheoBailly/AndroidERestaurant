package fr.isen.bailly.androiderestaurant.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import fr.isen.bailly.androiderestaurant.R
import fr.isen.bailly.androiderestaurant.databinding.ActivityConnectionBinding
import fr.isen.bailly.androiderestaurant.fragment.LoginFragment
import fr.isen.bailly.androiderestaurant.fragment.SignupFragment

class ConnectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConnectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val buttonLogin = binding.loginButton
        val buttonRegister = binding.signupButton
        val fragmentManager: FragmentManager = supportFragmentManager
        var fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment, LoginFragment()).commit()

        buttonLogin.setOnClickListener {
            fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment, LoginFragment()).commit()
        }
        buttonRegister.setOnClickListener {
            fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment, SignupFragment()).commit()
        }


    }
}