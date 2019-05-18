package br.com.micdev.fid2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.View
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.content_cadastro.*
import java.lang.Exception

class CadastroActivity : AppCompatActivity() {

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


        }else{
            Util.showSnackFeedback("Digite um CPF válido",false,view,this)
        }


    }

}
