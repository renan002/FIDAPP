package br.com.micdev.fid2.event

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaScannerConnection
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.micdev.fid2.R
import br.com.micdev.fid2.activities.EventosActivity
import br.com.micdev.fid2.qrcode.QRCodeAlertDialog
import br.com.micdev.fid2.qrcode.QRCodeUtils.Companion.textToImageEncode
import br.com.micdev.fid2.util.Util
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import kotlinx.android.synthetic.main.list_item.view.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class EventsAdapter(var events: ArrayList<EventObject>, val context: Context ,val eventosActivity: EventosActivity) : RecyclerView.Adapter<EventsAdapter.ViewHolder>(){

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
    }


    class ViewHolder (view: View,activity: EventosActivity): RecyclerView.ViewHolder(view) {
        val tvEventoName = view.textViewNome
        val tvEventoPreco = view.textViewPreco
        val tvEventoDataInit = view.textViewDataInit
        val tvEventoOwnerName = view.textViewOwnerName
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