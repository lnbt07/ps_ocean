package br.com.takakadev.ps_ocean

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import br.com.takakadev.ps_ocean.databinding.ActivitySplashBinding
import com.bumptech.glide.Glide

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val urlDoGif = "https://cdn.dribbble.com/users/2575831/screenshots/7917863/media/58f36cc61969e31422dd7b475c5b4f55.gif"

        Glide
            .with(this)
            .asGif()
            .load(urlDoGif)
            .into(binding.ivSplashScreen)

        val handler = Handler()
        handler.postDelayed({abrirTelaLogin()},3000)
    }

    fun abrirTelaLogin(){
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}