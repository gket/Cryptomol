package com.gk.cryptomol.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.gk.cryptomol.MainActivity
import com.gk.cryptomol.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val splashViewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        observeProcess()
        splashViewModel.getAllCoins()
    }

    private fun observeProcess(){
        splashViewModel.coinResponse.observe(this , Observer {
            if(it.data != null){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }

}