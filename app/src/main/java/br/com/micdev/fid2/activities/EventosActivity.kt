package br.com.micdev.fid2.activities

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import br.com.micdev.fid2.R
import br.com.micdev.fid2.util.Util

class EventosActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_ativo -> {
                textMessage.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_proximo -> {
                textMessage.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_vencido -> {
                textMessage.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        textMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val view:ConstraintLayout = findViewById(R.id.container)
        when(item?.itemId){
            R.id.upMenu_perfil ->{
                Util.showSnackFeedback("WIP", true, view, this)
                return true
            }
            R.id.upMenu_addEvento ->{
                Util.showSnackFeedback("WIP", true, view, this)
                return true
            }
            R.id.upMenu_configs ->{
                Util.showSnackFeedback("WIP", true, view, this)
                return true
            }
            R.id.upMenu_sair ->{
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
                return true
            }

        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.up_menu, menu)
        return true
    }
}
