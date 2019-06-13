package br.com.fid.fidapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import br.com.fid.fidapp.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val handler = Handler()

        val runnable = Runnable {
            irParaLogin()
        }
        handler.postDelayed(runnable,2000)

    }
    private fun irParaLogin(){

        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
        finish()
    }
}
