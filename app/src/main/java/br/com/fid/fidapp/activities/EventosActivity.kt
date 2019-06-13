package br.com.fid.fidapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import br.com.fid.fidapp.R
import br.com.fid.fidapp.event.*
import br.com.fid.fidapp.invite.InviteAdapter
import br.com.fid.fidapp.invite.InviteObject
import br.com.fid.fidapp.invite.InviteResponseGet
import br.com.fid.fidapp.retrofit.APIUtils.eventService
import br.com.fid.fidapp.retrofit.APIUtils.inviteService
import br.com.fid.fidapp.util.SaveSharedPreference
import br.com.fid.fidapp.util.Util
import br.com.fid.fidapp.util.Util.Companion.tenMinutesInMillis
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_eventos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventosActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView

    private var podeSair: Boolean = false

    var eventsPagos: ArrayList<EventObject> = ArrayList()
    var eventsProprios:ArrayList<EventProprioObject> = ArrayList()
    var eventsNaoPagos: ArrayList<InviteObject> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        //Obtem os eventos por integração
        if (System.currentTimeMillis()-tenMinutesInMillis >= SaveSharedPreference.getEventosNaoPagosDate(applicationContext)){
            obterEventosNaoPagos()
        }else{
            val typeMyType = object : TypeToken<ArrayList<InviteObject>>(){}.type
            eventsNaoPagos = Gson().fromJson(SaveSharedPreference.getEventosNaoPagos(applicationContext),typeMyType) as ArrayList<InviteObject>
        }

        if (System.currentTimeMillis() >= SaveSharedPreference.getEventosPagosDate(applicationContext)){
            obterEventosPagos()
        }else{
            val typeMyType = object : TypeToken<ArrayList<EventObject>>(){}.type
            eventsPagos = Gson().fromJson(SaveSharedPreference.getEventosPagos(applicationContext),typeMyType) as ArrayList<EventObject>
            eventsProprios = Gson().fromJson(SaveSharedPreference.getEventosProprios(applicationContext),typeMyType) as ArrayList<EventProprioObject>
            recyclerViewEventos.layoutManager = LinearLayoutManager(this)
            recyclerViewEventos.adapter = EventsAdapter(eventsPagos, this,this)
        }

        textMessage = message
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_ativo -> {
                textMessage.setText(R.string.title_home)
                recyclerViewEventos.layoutManager = LinearLayoutManager(this)

                recyclerViewEventos.adapter = EventsAdapter(eventsPagos, this,this)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_proximo -> {
                textMessage.setText(R.string.title_dashboard)
                recyclerViewEventos.layoutManager = LinearLayoutManager(this)

                recyclerViewEventos.adapter = InviteAdapter(eventsNaoPagos, this)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_vencido -> {
                textMessage.setText(R.string.title_notifications)
                recyclerViewEventos.layoutManager = LinearLayoutManager(this)

                recyclerViewEventos.adapter = EventsPropriosAdapter(eventsProprios, this,this)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val view:ConstraintLayout = findViewById(R.id.container)
        when(item?.itemId){
            R.id.upMenu_perfil ->{
                Util.showSnackFeedback("WIP", true, view, this)
                return true
            }
            R.id.upMenu_addEvento ->{
                startActivity(Intent(this, AdicionarEventoActivity::class.java))
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
        //Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.up_menu, menu)
        return true
    }

    private fun obterEventosPagos(){

        val call : Call<EventResponse> = eventService.eventGet(SaveSharedPreference.getUserToken(applicationContext))

        call.enqueue(
            object: Callback<EventResponse>{
                override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                    Log.e("EventosActivity",t.message)
                }

                override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                    if (response.isSuccessful) {
                        Log.e("EventosActivitySuccess",Gson().toJson(response.body()))
                        montarEventPagosList(response.body())

                        recyclerViewEventos.layoutManager = LinearLayoutManager(this@EventosActivity)

                        recyclerViewEventos.adapter = EventsAdapter(eventsPagos, this@EventosActivity,this@EventosActivity)
                    }else{
                        Log.e("EventosActivity",response.message())
                    }
                }
            }
        )
    }

    private fun obterEventosNaoPagos(){

        val call : Call<InviteResponseGet> = inviteService.inviteGet(SaveSharedPreference.getUserToken(applicationContext)!!)

        call.enqueue(
            object : Callback<InviteResponseGet>{
                override fun onFailure(call: Call<InviteResponseGet>, t: Throwable) {
                    Util.showSnackFeedback(getString(R.string.errorGeneric),false,container,this@EventosActivity)
                    Log.e("EventoActivityInviteGet",t.message,t)
                }

                override fun onResponse(call: Call<InviteResponseGet>, response: Response<InviteResponseGet>) {
                    if (response.isSuccessful){
                        monstarEventNaoPagosList(response.body())
                        Log.e("EventoActivityInviteGet", Gson().toJson(eventsNaoPagos))
                        recyclerViewEventos.layoutManager = LinearLayoutManager(this@EventosActivity)

                        recyclerViewEventos.adapter = InviteAdapter(eventsNaoPagos, this@EventosActivity)
                    }else{
                        Util.showSnackFeedback(getString(R.string.errorGeneric),false,container,this@EventosActivity)
                        Log.e("EventoActivityInviteGet",response.toString())
                    }
                }

            }
        )
    }

    private fun monstarEventNaoPagosList(inviteResponseGet: InviteResponseGet?){
        val listId:MutableSet<String>? = SaveSharedPreference.getInvitesFavoritos(applicationContext)
        inviteResponseGet!!.content.forEach{i ->
            val fav = listId?.contains(i.id.toString()) ?: false
            val inviteObject = InviteObject(
                i.accepted,
                i.deleted,
                i.eventEndDate,
                i.eventId,
                i.eventName,
                i.eventPrice,
                Util.formatDateTime(i.eventStartDate),
                i.id,
                i.paid,
                fav
            )
            eventsNaoPagos.add(inviteObject)
        }
        SaveSharedPreference.setEventosNaoPagos(applicationContext,Gson().toJson(eventsNaoPagos))
    }

    private fun montarEventPagosList(eventResponse: EventResponse?){
        val listId:MutableSet<String>? = SaveSharedPreference.getEventsFavoritos(applicationContext)
        eventResponse!!.content.forEach{t ->
            val fav = listId?.contains(t.id.toString()) ?: false
            if (t.ownerName != SaveSharedPreference.getUserName(applicationContext) ) {
                val eventObject = EventObject(
                    t.endDate,
                    null,
                    t.id,
                    t.isPublic,
                    t.maxCapacity,
                    t.minCapacity,
                    t.name,
                    t.ownerName,
                    t.price,
                    Util.formatDateTime(t.startDate),
                    t.tokenText,
                    fav
                )
                eventsPagos.add(eventObject)
            }else{
                val eventProprioObject = EventProprioObject(
                    t.endDate,
                    null,
                    t.id,
                    t.isPublic,
                    t.maxCapacity,
                    t.minCapacity,
                    t.name,
                    t.ownerName,
                    t.price,
                    Util.formatDateTime(t.startDate),
                    t.tokenText, fav
                )
                eventsProprios.add(eventProprioObject)
            }


        }
        SaveSharedPreference.setEventosPagos(applicationContext,Gson().toJson(eventsPagos))
        SaveSharedPreference.setEventosProprios(applicationContext,Gson().toJson(eventsProprios))
    }

    override fun onBackPressed() {
        if(podeSair)
            super.onBackPressed()
        else{
            podeSair = true
            Util.showToastFeedbach(getString(R.string.desejaSair),Toast.LENGTH_SHORT,this)
            val handler = Handler()

            val runnable = Runnable {
                podeSair = false
            }
            handler.postDelayed(runnable,2000)
        }
    }
}
