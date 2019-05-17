package br.com.micdev.fid2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import br.com.micdev.fid2.retrofit2.RetrofitClient
import br.com.micdev.fid2.retrofit2.user.UserService
import br.com.micdev.fid2.retrofit2.user.UserUtils
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.content_cadastro.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Response.success
import java.lang.Exception

class CadastroActivity : AppCompatActivity() {

    private var mUserService: UserService? = null
    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        setSupportActionBar(toolbar)

        mUserService = UserUtils.userService

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cadastroCPF.addTextChangedListener(Mask.mask("###.###.###-##",cadastroCPF))
        cadastroDataNasc.addTextChangedListener(Mask.mask("##/##/####",cadastroDataNasc))

        cadastroBotao.setOnClickListener { view ->
            cadastrar(view)
        }
    }

    private fun cadastrar(view: View){
        val CPF = cadastroCPF.text.toString()
        if(Util.myValidateCPF(CPF)) {

            val nome = cadastroNome.text.toString()
            val email = cadastroEmail.text.toString()
            val senha = cadastroSenha.text.toString()
            //val dataNascString= cadastroDataNasc.text.toString()
            try {
                //val dataNasc = Date.valueOf(cadastroDataNasc.text.toString())
            }catch (e:Exception){
                //Util.showSnackFeedback(e.toString(),false,view,this)
                //return
            }
            val userModel = UserModel()
            userModel.cpf=CPF
            userModel.email=email
            userModel.name=nome
            userModel.password=senha

            sendPost(userModel)

        }else{
            Util.showSnackFeedback("Digite um CPF válido",false,view,this)
        }


    }

    private fun sendPost(userModel: UserModel) {
             mUserService?.savePost(userModel)?.enqueue(object : Callback<RetrofitClient>{
            override fun onFailure(call: Call<RetrofitClient>, t: Throwable) {
                Util.showSnackFeedback(t.message.toString(),true,context.findViewById(R.id.cadastroLayout),context)
            }

            override fun onResponse(call: Call<RetrofitClient>?, response: Response<RetrofitClient>?) {
                Util.showSnackFeedback(response.toString(),true,context.findViewById(R.id.cadastroLayout),context)
            }
        })

    }


}
