package br.com.micdev.fid2.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import br.com.micdev.fid2.util.Mask
import br.com.micdev.fid2.R
import br.com.micdev.fid2.login.*
import br.com.micdev.fid2.retrofit.APIUtils.loginService
import br.com.micdev.fid2.util.SaveSharedPreference
import br.com.micdev.fid2.util.Util
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        if (SaveSharedPreference.getLoggedStatus(applicationContext)){
            startActivity(Intent(this,EventosActivity::class.java))
            finish()
        }

        loginCPF.addTextChangedListener(Mask.mask("###.###.###-##", loginCPF))

        val i = intent

        if(i.getStringExtra("cpf") != null){
            loginCPF.setText(i.getStringExtra("cpf"))
        }

        logarButton.setOnClickListener {view ->

            if(Util.myValidateCPF(loginCPF.text.toString())){
                logar(view)
            }else{
                Util.showSnackFeedback("Digite um CPF válido", false, view, this)
            }
        }
        cadastrar.setOnClickListener { view ->
            startActivity(Intent(this, CadastroActivity::class.java))
        }

    }

    private fun logar(view: View){
        val cpf = loginCPF.text.toString().replace("-","").replace(".","")
        val senha = loginSenha.text.toString()

        val loginRequest = LoginRequest(cpf,senha)

        try {
            val jsonString = Gson().toJson(loginRequest)

            val requestBody:RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString)

            Log.i("LoginActivity",requestBody.contentType().toString()+" "+jsonString)

            val call : Call<LoginResponse> = loginService.loginPost(requestBody)

            call.enqueue(
                object : Callback<LoginResponse>{
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.e("LoginActivity","Erro ao logar: "+t.message)
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        //TODO tratar as exceções de erro, senha incorreta, cpf não cadastrado, etc...
                        if(response.isSuccessful){
                            Log.e("LoginActivity",Gson().toJson(response.body()))
                            armazenarLogin(response.body())
                            startActivity(Intent(this@LoginActivity,EventosActivity::class.java))
                            finish()
                        }else{
                            Util.showSnackFeedback(getString(R.string.usuarioInvalido),false,view,this@LoginActivity)
                        }
                    }
                }
            )
        }catch (e:Exception){
            Log.e("LoginActivity",e.message)
        }
    }

    private fun armazenarLogin(loginResponse: LoginResponse?){
        SaveSharedPreference.setLoginParams(applicationContext,loginResponse)
    }

}
