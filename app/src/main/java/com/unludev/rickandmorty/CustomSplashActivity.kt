package com.unludev.rickandmorty

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.unludev.rickandmorty.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class CustomSplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        setUpSplashText()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.root.background = AppCompatResources.getDrawable(this, R.drawable.splash_land)
        } else {
            binding.root.background = AppCompatResources.getDrawable(this, R.drawable.splash)
        }
    }

    private fun setUpSplashText() {
        if (sharedPreferences.getBoolean("isFirstTime", true)) {
            binding.splashText.text = getString(R.string.splash_welcome_text)
            sharedPreferences.edit().putBoolean("isFirstTime", false).apply()
        } else {
            binding.splashText.text = getString(R.string.splash_hello_text)
        }
    }
}
