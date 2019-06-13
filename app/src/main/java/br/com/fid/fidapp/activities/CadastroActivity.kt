package br.com.fid.fidapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import br.com.fid.fidapp.R
import br.com.fid.fidapp.retrofit.APIUtils.userService
import br.com.fid.fidapp.user.UserRequest
import br.com.fid.fidapp.util.Mask
import br.com.fid.fidapp.util.Util
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.content_cadastro.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroActivity : AppCompatActivity() {

    private val context:Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        setSupportActionBar(toolbar)
        window.statusBarColor = getColor(R.color.colorPrimaryDark)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cadastroCPF.addTextChangedListener(Mask.mask("###.###.###-##", cadastroCPF))
        cadastroDataNasc.addTextChangedListener(Mask.mask("##/##/####", cadastroDataNasc))

        cadastroBotao.setOnClickListener { view ->
            cadastrar(view)
        }
    }

    private fun cadastrar(view: View){
        var cpf = cadastroCPF.text.toString().trim()

        if(Util.myValidateCPF(cpf)) {
            cpf = cpf.replace(".","").replace("-","").trim()
            val email = cadastroEmail.text.toString().trim()

            if(Util.isEmail(email)){
                val senha = cadastroSenha.text.toString().trim()
                Log.e("Senha",senha.length.toString())
                if(senha.length in 6..30){
                    val confirmSenha = cadastroConfirmSenha.text.toString()
                    if (senha == confirmSenha) {
                        val nome = cadastroNome.text.toString().trim()
                        val dataNasc = cadastroDataNasc.text.toString().trim()
                        try {
                            //val dataNasc = Date.valueOf(cadastroDataNasc.text.toString())
                            //TODO adicionar as regras de exeções
                            val jsonString = Gson().toJson(UserRequest(cpf, email, dataNasc, nome, senha))
                            val requestBody: RequestBody =
                                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString)
                            Log.e("CadastroActivity", requestBody.contentType().toString() + " " + jsonString)

                            val call: Call<Unit> = userService.registrationPost(requestBody)

                            //TODO Usar a merda da origentação a objetos direito
                            call.enqueue(
                                object : Callback<Unit> {
                                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                                        Util.showSnackFeedback(getString(R.string.errorGeneric), false, view, context)
                                        Log.e("CadastroActivity", t.message)

                                    }

                                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                                        if (response.isSuccessful) {
                                            Util.showSnackFeedback(
                                                "Cadastro realizado com sucesso",
                                                true,
                                                view,
                                                context
                                            )
                                            Log.i("CadastroActivity", response.message())
                                            val handler = Handler()

                                            val runnable = Runnable {
                                                val i = Intent(context, LoginActivity::class.java)
                                                i.putExtra("cpf", cpf)
                                                startActivity(i)
                                                finish()
                                            }
                                            handler.postDelayed(runnable, 1500)

                                        } else {
                                            Util.showSnackFeedback(
                                                getString(R.string.errorGeneric),
                                                false,
                                                view,
                                                context
                                            )
                                            Log.e("CadastroActivity", response.toString())
                                        }
                                    }
                                }
                            )
                        } catch (e: Exception) {
                            Log.e("CadastroActivity", e.message)
                        }
                    }else{
                        //Senhas diferentes
                        Util.showSnackFeedback(getString(R.string.senhasDif),false, view,this)
                    }
                }else{
                    //Senha muito curta ou muito longa
                    Util.showSnackFeedback(getString(R.string.invalidPassword),false, view,this)
                }
            }else{
                //Email inválido
                Util.showSnackFeedback(getString(R.string.invalidEmail), false, view, this)
            }
        }else{
            //CPF inválido
            Util.showSnackFeedback(getString(R.string.invalidCPF), false, view, this)
        }
    }
}
