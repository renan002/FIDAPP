package br.com.micdev.fid2.activities

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import br.com.micdev.fid2.R
import br.com.micdev.fid2.event.EventObject
import br.com.micdev.fid2.retrofit.APIUtils
import br.com.micdev.fid2.util.ConfirEventoAlertDialog
import br.com.micdev.fid2.util.SaveSharedPreference
import br.com.micdev.fid2.util.Util
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_adicionar_evento.*
import kotlinx.android.synthetic.main.content_adicionar_evento.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdicionarEventoActivity : AppCompatActivity(), ConfirEventoAlertDialog.Servico {

    override fun onCancel() {
        Util.showSnackFeedback(getString(R.string.cancelado),false,layoutAddEvent,this@AdicionarEventoActivity)
    }

    override fun onConfirm() {
        adicionarEvento(layoutAddEvent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_evento)
        setSupportActionBar(toolbar)

        //Gambiarra pq ta deixando a cor branca e eu n sei pq
        window.statusBarColor = getColor(R.color.colorPrimaryDark)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buttonAdicionarEvento.setOnClickListener { view ->
            val codEvento:String = editTextCodEvento.text.toString()
            obterInfosEvento(view, codEvento)
        }
    }

    private fun obterInfosEvento(view: View,codEvento:String){
        //TODO Adicionar rota para obter infos do evento
        val call:Call<EventObject> = APIUtils.eventService.eventGetOne(SaveSharedPreference.getUserToken(applicationContext),"api/event/5")
        call.enqueue(
            object : Callback<EventObject>{
                override fun onFailure(call: Call<EventObject>, t: Throwable) {
                    Util.showSnackFeedback(getString(R.string.errorGeneric),false,view,this@AdicionarEventoActivity)
                    Log.e("AdicionarEventoAct",t.message)
                }

                override fun onResponse(call: Call<EventObject>, response: Response<EventObject>) {
                    if(response.isSuccessful){
                        val bundle = Bundle()
                        bundle.putSerializable("eventObject",response.body()!!)

                        val confirEventoAlertDialog = ConfirEventoAlertDialog()
                        confirEventoAlertDialog.arguments = bundle
                        confirEventoAlertDialog.show(supportFragmentManager,"AlertDialogEvento")
                    }else{
                        Util.showSnackFeedback(response.message(),false,view,this@AdicionarEventoActivity)
                        Log.e("AdicionarEventoAct",response.toString())
                    }
                }
            }
        )
    }

    private fun adicionarEvento(view: View){
        val codEvento:String = editTextCodEvento.text.toString()
        val call: Call<Unit> = APIUtils.inviteService.invitePost(SaveSharedPreference.getUserToken(applicationContext)!!,codEvento)

        call.enqueue(
            object : Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Util.showSnackFeedback(getString(R.string.errorGeneric),false,view,this@AdicionarEventoActivity)
                    Log.e("AdicionarEventoAct",t.message)
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if(response.isSuccessful){
                        Log.i("AdicionarEventoAct", Gson().toJson(response.body()))
                        Util.showSnackFeedback("Evento adicionado, aguardando pagamento",true,view,this@AdicionarEventoActivity)
                        //popularFormEvento(response.body())
                    }else{
                        Util.showSnackFeedback(getString(R.string.eventNotFound),false,view,this@AdicionarEventoActivity)
                        Log.e("AdicionarEventoAct",response.toString())
                    }
                }

            }
        )
    }

}
