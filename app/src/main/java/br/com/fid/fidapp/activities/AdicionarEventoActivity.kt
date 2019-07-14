package br.com.fid.fidapp.activities

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import br.com.fid.fidapp.R
import br.com.fid.fidapp.event.EventObject
import br.com.fid.fidapp.retrofit.APIUtils
import br.com.fid.fidapp.util.ConfirEventoAlertDialog
import br.com.fid.fidapp.util.SaveSharedPreference
import br.com.fid.fidapp.util.Util
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_adicionar_evento.*
import kotlinx.android.synthetic.main.content_adicionar_evento.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdicionarEventoActivity : AppCompatActivity(), ConfirEventoAlertDialog.Servico {

    override fun onCancel() {
        Util.showSnackFeedback(getString(R.string.cancelado),false,layoutAddEvent,this@AdicionarEventoActivity)
        progressBarAdicionarEvento.visibility = ProgressBar.GONE
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
            progressBarAdicionarEvento.visibility = ProgressBar.VISIBLE
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(buttonAdicionarEvento.windowToken, 0)
            val codEvento:String = editTextCodEvento.text.toString()
            if(codEvento.isNotEmpty())
                obterInfosEvento(view, codEvento)
            else
                Util.showSnackFeedback(getString(R.string.digite_cod_evento),false,view,this)
        }
    }

    private fun obterInfosEvento(view: View,codEvento:String){
        //TODO Adicionar rota para obter infos do evento
        val call:Call<EventObject> = APIUtils.eventService.eventGetOne(SaveSharedPreference.getUserToken(applicationContext),codEvento)
        call.enqueue(
            object : Callback<EventObject>{
                override fun onFailure(call: Call<EventObject>, t: Throwable) {
                    Util.showSnackFeedback(getString(R.string.errorGeneric),false,view,this@AdicionarEventoActivity)
                    Log.e("AdicionarEventoAct",t.message)
                    progressBarAdicionarEvento.visibility = ProgressBar.GONE
                }

                override fun onResponse(call: Call<EventObject>, response: Response<EventObject>) {
                    if(response.isSuccessful){
                        val bundle = Bundle()
                        bundle.putSerializable("eventObject",response.body()!!)

                        val confirEventoAlertDialog = ConfirEventoAlertDialog()
                        confirEventoAlertDialog.arguments = bundle
                        confirEventoAlertDialog.show(supportFragmentManager,"AlertDialogEvento")
                        progressBarAdicionarEvento.visibility = ProgressBar.GONE
                    }else{
                        Util.showSnackFeedback(getString(R.string.eventNotFound),false,view,this@AdicionarEventoActivity)
                        Log.e("AdicionarEventoAct",response.toString())
                        progressBarAdicionarEvento.visibility = ProgressBar.GONE
                    }
                }
            }
        )
    }

    private fun adicionarEvento(view: View){
        progressBarAdicionarEvento.visibility = ProgressBar.VISIBLE
        val codEvento:String = editTextCodEvento.text.toString()
        val call: Call<Unit> = APIUtils.inviteService.invitePost(SaveSharedPreference.getUserToken(applicationContext)!!,codEvento)

        call.enqueue(
            object : Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Util.showSnackFeedback(getString(R.string.errorGeneric),false,view,this@AdicionarEventoActivity)
                    Log.e("AdicionarEventoAct",t.message)
                    progressBarAdicionarEvento.visibility = ProgressBar.GONE
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if(response.isSuccessful){
                        Log.i("AdicionarEventoAct", Gson().toJson(response.body()))
                        Util.showSnackFeedback("Evento adicionado, aguardando pagamento",true,view,this@AdicionarEventoActivity)
                        progressBarAdicionarEvento.visibility = ProgressBar.GONE

                    }else{
                        Util.showSnackFeedback(getString(R.string.eventNotFound),false,view,this@AdicionarEventoActivity)
                        Log.e("AdicionarEventoAct",response.toString())
                        progressBarAdicionarEvento.visibility = ProgressBar.GONE
                    }
                }

            }
        )
    }

}
