package br.com.micdev.fid2

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import br.com.micdev.fid2.user.UserModel
import br.com.micdev.fid2.user.UserResponse
import br.com.micdev.fid2.retrofit.APIUtils.userService
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

        cadastroCPF.addTextChangedListener(Mask.mask("###.###.###-##",cadastroCPF))
        cadastroDataNasc.addTextChangedListener(Mask.mask("##/##/####",cadastroDataNasc))

        cadastroBotao.setOnClickListener { view ->
            cadastrar(view)
        }
    }

    private fun cadastrar(view: View){
        var cpf = cadastroCPF.text.toString()

        if(Util.myValidateCPF(cpf)) {
            cpf = cpf.replace(".","").replace("-","")
            val nome = cadastroNome.text.toString()
            //TODO Adiconar validador de email
            val email = cadastroEmail.text.toString()
            val senha = cadastroSenha.text.toString()
            //val dataNascString= cadastroDataNasc.text.toString()
            try {
                //val dataNasc = Date.valueOf(cadastroDataNasc.text.toString())
                //TODO adicionar as regras de exeções
                val jsonString = Gson().toJson(UserModel(cpf,email,nome,senha))

                val requestBody : RequestBody = RequestBody.create(MediaType.parse("application/json"),jsonString)

                val call : Call<UserResponse> = userService.registrationPost(requestBody)

                //TODO Usar a merda da origentação a objetos direito
                call.enqueue(
                    object : Callback<UserResponse>{
                        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                            Util.showSnackFeedback("Não foi F",true,view,context)
                            Toast.makeText(context,t.message,Toast.LENGTH_LONG).show()
                        }
                        override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                            if(response.isSuccessful) {
                                Util.showSnackFeedback("Foiii", true, view, context)
                                Toast.makeText(context,response.message(),Toast.LENGTH_LONG).show()
                            } else{
                                Util.showSnackFeedback("Não foi",false,view,context)
                                Toast.makeText(context,response.toString(),Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                )

            }catch (e:Exception){
                //Util.showSnackFeedback(e.toString(),false,view,this)
                //return
            }


        }else{
            Util.showSnackFeedback("Digite um CPF válido",false,view,this)
        }


    }



}
