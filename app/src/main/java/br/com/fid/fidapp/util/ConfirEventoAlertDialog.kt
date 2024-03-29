package br.com.fid.fidapp.util

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.TextView
import br.com.fid.fidapp.R
import br.com.fid.fidapp.event.EventObject

class ConfirEventoAlertDialog : DialogFragment() {

    interface Servico{
        fun onConfirm() //Manda o resultado para o Activity principal
        fun onCancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view : View = activity!!.layoutInflater.inflate(R.layout.alert_dialog_confir_evento,null)

        val eventObject: EventObject = arguments?.getSerializable("eventObject") as EventObject

        val textViewNomeEvento: TextView = view.findViewById(R.id.textViewNomeEvento)
        textViewNomeEvento.text = String.format(getString(R.string.eventName), eventObject.name)
        val textViewDataIncio: TextView = view.findViewById(R.id.textViewDataIncio)
        textViewDataIncio.text = String.format(getString(R.string.eventDateInit), Util.formatDateTime(eventObject.startDate))
        val textViewDataFim: TextView = view.findViewById(R.id.textViewDataFim)
        textViewDataFim.text = String.format(getString(R.string.eventDateEnd),  Util.formatDateTime(eventObject.endDate))
        val textViewPreco: TextView = view.findViewById(R.id.textViewPreco)
        textViewPreco.text = String.format(getString(R.string.eventPrice), eventObject.price)

        val alert = AlertDialog.Builder(activity)
        alert.setView(view)

        alert.setTitle(getString(R.string.informaçõesEvento))
        alert.setCancelable(false)

        alert.setOnDismissListener{
            (activity as(Servico)).onCancel()
        }

        alert.setPositiveButton(getString(android.R.string.yes)){ _, _ ->
            (activity as(Servico)).onConfirm()
            dismiss()
        }
        alert.setNegativeButton(getString(android.R.string.no)){ _, _ ->
            (activity as(Servico)).onCancel()
            dismiss()
        }

        return alert.create()
    }
}