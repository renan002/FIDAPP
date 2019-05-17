package br.com.micdev.fid2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginCPF.addTextChangedListener(Mask.mask("###.###.###-##",loginCPF))

        logarButton.setOnClickListener {view ->

            if(Util.myValidateCPF(loginCPF.text.toString())){
                val i = Intent(this,EventosActivity::class.java)
                startActivity(i)
                finish()
            }else{
                Util.showSnackFeedback("Digite um CPF válido",false,view,this)
            }
        }
        cadastrar.setOnClickListener { view ->
            val i = Intent(this,CadastroActivity::class.java)
            startActivity(i)

        }

    }


}
