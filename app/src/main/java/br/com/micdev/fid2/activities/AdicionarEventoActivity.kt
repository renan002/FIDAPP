package br.com.micdev.fid2.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import br.com.micdev.fid2.R
import br.com.micdev.fid2.invite.InviteResponse
import br.com.micdev.fid2.retrofit.APIUtils.inviteService
import br.com.micdev.fid2.util.SaveSharedPreference
import br.com.micdev.fid2.util.Util
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_adicionar_evento.*
import kotlinx.android.synthetic.main.content_adicionar_evento.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdicionarEventoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_evento)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buttonAdicionarEvento.setOnClickListener { view ->
            adicionarEvento(view)
        }
    }

    private fun adicionarEvento(view: View){
        val codEvento = editTextCodEvento.text.toString()

        val call: Call<Unit> = inviteService.invitePost(SaveSharedPreference.getUserToken(applicationContext)!!,codEvento)

        call.enqueue(
            object : Callback<Unit>{
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Util.showSnackFeedback(getString(R.string.errorGeneric),false,view,this@AdicionarEventoActivity)
                    Log.e("AdicionarEventoAct",t.message)
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if(response.isSuccessful){
                        Log.i("AdicionarEventoAct",Gson().toJson(response.body()))
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

    private fun popularFormEvento(inviteResponse: InviteResponse?){
        textViewNomeEvento.text = inviteResponse?.name
        textViewDataIncio.text = inviteResponse?.startDate
        textViewDataFim.text = inviteResponse?.endDate
        textViewPreco.text = "R$ "+ inviteResponse?.price.toString()

        formEvento.visibility = View.VISIBLE
    }

}
