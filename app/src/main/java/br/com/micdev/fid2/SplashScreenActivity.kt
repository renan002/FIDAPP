package br.com.micdev.fid2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val handler = Handler()

        val runnable = Runnable {
            irParaLogin()
        }
        handler.postDelayed(runnable,5000)

    }
    private fun irParaLogin(){

        val i = Intent(this,LoginActivity::class.java)
        startActivity(i)
        finish()
    }
}
