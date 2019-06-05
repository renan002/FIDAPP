package br.com.micdev.fid2.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import br.com.micdev.fid2.util.Mask
import br.com.micdev.fid2.R
import br.com.micdev.fid2.util.Util
import br.com.micdev.fid2.retrofit.APIUtils.userService
import br.com.micdev.fid2.user.UserRequest
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.content_cadastro.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class CadastroActivity : AppCompatActivity() {

    private val context:Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cadastroCPF.addTextChangedListener(Mask.mask("###.###.###-##", cadastroCPF))
        cadastroDataNasc.addTextChangedListener(Mask.mask("##/##/####", cadastroDataNasc))

        cadastroBotao.setOnClickListener { view ->
            cadastrar(view)
        }
    }

    private fun cadastrar(view: View){
        var cpf = cadastroCPF.text.toString()

        if(Util.myValidateCPF(cpf)) {
            cpf = cpf.replace(".","").replace("-","")
            val nome = cadastroNome.text.toString()
            val email = cadastroEmail.text.toString()

            if(Util.isEmail(email)){
                val senha = cadastroSenha.text.toString()
                //val dataNascString= cadastroDataNasc.text.toString()
                try {
                    //val dataNasc = Date.valueOf(cadastroDataNasc.text.toString())
                    //TODO adicionar as regras de exeções
                    val jsonString = Gson().toJson(UserRequest(cpf,email,nome,senha))

                    val requestBody : RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonString)

                    Log.e("CadastroActivity",requestBody.contentType().toString()+" "+jsonString)

                    val call : Call<Unit> = userService.registrationPost(requestBody)

                    //TODO Usar a merda da origentação a objetos direito
                    call.enqueue(
                        object : Callback<Unit>{
                            override fun onFailure(call: Call<Unit>, t: Throwable) {
                                Util.showSnackFeedback("Não foi F", false, view, context)
                                Toast.makeText(context,t.message,Toast.LENGTH_LONG).show()
                                Log.e("CadastroActivity",t.message)

                            }
                            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                                if(response.isSuccessful) {
                                    Util.showSnackFeedback("Cadastro realizado com sucesso", true, view, context)
                                    Log.i("CadastroActivity",response.message())
                                    val handler = Handler()

                                    val runnable = Runnable {
                                        val i = Intent(context,LoginActivity::class.java)
                                        i.putExtra("cpf",cpf)
                                        startActivity(i)
                                        finish()
                                    }
                                    handler.postDelayed(runnable,1000)

                                } else{
                                    Util.showSnackFeedback("Não foi", false, view, context)
                                    Toast.makeText(context,response.toString(),Toast.LENGTH_LONG).show()
                                    Log.e("CadastroActivity",response.toString())
                                }
                            }
                        }
                    )

                }catch (e:Exception){
                    Log.e("CadastroActivity",e.message)
                }
            }else{
                Util.showSnackFeedback("Digite um e-mail válido", false, view, this)
            }
        }else{
            Util.showSnackFeedback("Digite um CPF válido", false, view, this)
        }
    }
}
