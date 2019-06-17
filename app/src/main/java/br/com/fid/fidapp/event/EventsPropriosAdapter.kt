package br.com.fid.fidapp.event

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.ArraySet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import br.com.fid.fidapp.R
import br.com.fid.fidapp.activities.EventosActivity
import br.com.fid.fidapp.qrcode.QRCodeAlertDialog
import br.com.fid.fidapp.qrcode.QRCodeUtils.Companion.textToImageEncode
import br.com.fid.fidapp.util.SaveSharedPreference
import br.com.fid.fidapp.util.Util
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.list_item.view.*
import java.io.File
import java.util.*

class EventsPropriosAdapter(var events: ArrayList<EventProprioObject>, val context: Context, val eventosActivity: EventosActivity) : RecyclerView.Adapter<EventsPropriosAdapter.ViewHolder>(){
    private var bitmap: Bitmap? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false), eventosActivity)
    }

    override fun getItemCount(): Int {
        return events.size
    }
    private fun popularQrCodeView(fragmentManager: FragmentManager,token:String,eventId:String){
        val file = File(Environment.getExternalStorageDirectory().toString()+"/fid/qrcodes","$eventId.png")
        if (file.exists() && ContextCompat.checkSelfPermission(eventosActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED){
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_4444
            bitmap = BitmapFactory.decodeFile(file.path,options)
        }else{
            bitmap = textToImageEncode(token,context,eventId)
        }
        val qrCodeAlertDialog = QRCodeAlertDialog(bitmap!!)
        qrCodeAlertDialog.show(fragmentManager,"QRCodeAlert")
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvEventoName.text = events[position].name
        holder.tvEventoPreco.text = String.format(context.getString(R.string.cifrao), events[position].price)
        holder.tvEventoDataInit.text = (events[position].startDate)
        holder.tvEventoOwnerName.text = events[position].ownerName
        holder.token = events[position].tokenText!!
        holder.switch.isChecked = events[position].favoritado
        holder.itemView.setOnClickListener{
            val manager:FragmentManager = eventosActivity.supportFragmentManager
            try {
                popularQrCodeView(manager,events[position].tokenText!!,events[position].id.toString())
            } catch (e: WriterException) {
                e.printStackTrace()
            }
        }

        holder.switch.setOnCheckedChangeListener { view, isChecked ->
            events[position].favoritado = isChecked
            val appContext = view.context.applicationContext
            var listId: MutableSet<String>? = SaveSharedPreference.getEventsFavoritos(appContext)
            if (listId != null) {
                if (listId.contains(events[position].id.toString())) {
                    if (!isChecked)
                        listId.remove(events[position].id.toString())
                } else {
                    if (isChecked)
                        listId.add(events[position].id.toString())
                    SaveSharedPreference.setEventsFavoritos(appContext, listId)
                }
            }else{
                listId = ArraySet()
                listId.add(events[position].id.toString())
                SaveSharedPreference.setEventsFavoritos(appContext, listId)
            }
            Collections.sort(events,CompareEventProprioObject())
            eventosActivity.teste()
        }
    }

    class ViewHolder (view: View,activity: EventosActivity): RecyclerView.ViewHolder(view) {
        val tvEventoName:TextView = view.textViewNome
        val tvEventoPreco: TextView = view.textViewPreco
        val tvEventoDataInit:TextView = view.textViewDataInit
        val tvEventoOwnerName:TextView = view.textViewOwnerName
        val switch:CheckBox = view.favotitarSwitch
        lateinit var token:String
        val context:Context = view.context

        private val naoSeiPqSemUmaValNaoFuncionaEssaMerda2 = view.setOnLongClickListener{v->
            Util.showSnackFeedback(tvEventoPreco.text.toString(),true,v,v.context)

            return@setOnLongClickListener true
        }


    }
}