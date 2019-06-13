package br.com.fid.fidapp.event

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView
import android.util.ArraySet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.fid.fidapp.R
import br.com.fid.fidapp.activities.EventosActivity
import br.com.fid.fidapp.qrcode.QRCodeAlertDialog
import br.com.fid.fidapp.qrcode.QRCodeUtils.Companion.textToImageEncode
import br.com.fid.fidapp.util.SaveSharedPreference
import br.com.fid.fidapp.util.Util
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*

class EventsPropriosAdapter(var events: ArrayList<EventProprioObject>, val context: Context, val eventosActivity: EventosActivity) : RecyclerView.Adapter<EventsPropriosAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false), eventosActivity)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvEventoName.text = events[position].name
        holder.tvEventoPreco.text = String.format(context.getString(R.string.cifrao), events[position].price)
        holder.tvEventoDataInit.text = (events[position].startDate)
        holder.tvEventoOwnerName.text = events[position].ownerName
        holder.token = events[position].tokenText!!
        holder.switch.isChecked = events[position].favoritado
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
        }
    }

    class ViewHolder (view: View,activity: EventosActivity): RecyclerView.ViewHolder(view) {
        val tvEventoName = view.textViewNome
        val tvEventoPreco = view.textViewPreco
        val tvEventoDataInit = view.textViewDataInit
        val tvEventoOwnerName = view.textViewOwnerName
        val switch = view.favotitarSwitch
        lateinit var token:String
        val context = view.context
        internal var bitmap: Bitmap? = null
        private val naoSeiPqSemUmaValNaoFuncionaEssaMerda = view.setOnClickListener{
            val manager:FragmentManager = activity.supportFragmentManager
            try {
                popularQrCodeView(manager)
            } catch (e: WriterException) {
                e.printStackTrace()
            }
        }
        private val naoSeiPqSemUmaValNaoFuncionaEssaMerda2 = view.setOnLongClickListener{v->
            Util.showSnackFeedback(tvEventoPreco.text.toString(),true,v,v.context)

            return@setOnLongClickListener true
        }

        private fun popularQrCodeView(fragmentManager: FragmentManager){
            bitmap = textToImageEncode(token,context)
            val qrCodeAlertDialog = QRCodeAlertDialog(bitmap!!)
            qrCodeAlertDialog.show(fragmentManager,"AlertDialogQRCode")
        }



    }


}