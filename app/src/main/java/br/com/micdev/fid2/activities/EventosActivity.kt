package br.com.micdev.fid2.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import br.com.micdev.fid2.R
import br.com.micdev.fid2.event.EventResponse
import br.com.micdev.fid2.retrofit.APIUtils.eventService
import br.com.micdev.fid2.util.SaveSharedPreference
import br.com.micdev.fid2.util.Util
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_eventos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventosActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView

    private var podeSair: Boolean = false

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_ativo -> {
                textMessage.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_proximo -> {
                textMessage.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_vencido -> {
                textMessage.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        //Obtem os eventos por integração
        var eventResponse = obterEventos()

        textMessage = message
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val view:ConstraintLayout = findViewById(R.id.container)
        when(item?.itemId){
            R.id.upMenu_perfil ->{
                Util.showSnackFeedback("WIP", true, view, this)
                return true
            }
            R.id.upMenu_addEvento ->{
                Util.showSnackFeedback("WIP", true, view, this)
                return true
            }
            R.id.upMenu_configs ->{
                Util.showSnackFeedback("WIP", true, view, this)
                return true
            }
            R.id.upMenu_sair ->{
                logout()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return true
            }

        }
        return false
    }

    private fun logout(){
        SaveSharedPreference.logout(applicationContext)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.up_menu, menu)
        return true
    }

    private fun obterEventos(): EventResponse?{

        var eventResponse: EventResponse? = null

        val call : Call<EventResponse> = eventService.eventGet(SaveSharedPreference.getUserToken(applicationContext)!!)

        call.enqueue(
            object: Callback<EventResponse>{
                override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                    Log.e("EventosActivity",t.message)
                }

                override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                    if (response.isSuccessful) {
                        Log.e("EventosActivity",Gson().toJson(response.body()))
                        eventResponse = response.body()
                    }else{
                        Log.e("EventosActivity",response.message())
                    }
                }

            }
        )
        return eventResponse
    }

    override fun onBackPressed() {
        if(podeSair)
            super.onBackPressed()
        else{
            podeSair = true
            Util.showSnackFeedback(getString(R.string.desejaSair),false,this.nav_view,this)
            val handler = Handler()

            val runnable = Runnable {
                podeSair = false
            }
            handler.postDelayed(runnable,2000)
        }
    }
}
