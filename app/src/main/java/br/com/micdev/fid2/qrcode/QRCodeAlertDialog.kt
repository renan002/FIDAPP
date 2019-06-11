package br.com.micdev.fid2.qrcode

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.DialogFragment
import br.com.micdev.fid2.R
import br.com.micdev.fid2.qrcode.QRCodeUtils.Companion.saveImage
import kotlinx.android.synthetic.main.alert_dialog_qrcode.view.*


@SuppressLint("ValidFragment")
class QRCodeAlertDialog : DialogFragment {
    var bitmap:Bitmap? = null
    @SuppressLint("ValidFragment")
    constructor(bitmapC: Bitmap) : super(){
        bitmap = bitmapC
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view  = activity!!.layoutInflater.inflate(R.layout.alert_dialog_qrcode,null)

        view.ivQRCode.setImageBitmap(bitmap)

        view.buttonSalvar.setOnClickListener{v->
            saveImage(v, bitmap,"1")
        }

        val alert = AlertDialog.Builder(activity)
        alert.setView(view)
        alert.setTitle(getString(R.string.qrCodeMostre))

        return alert.create()
    }
}