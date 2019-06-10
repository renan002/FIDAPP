package br.com.micdev.fid2.invite

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.micdev.fid2.R
import br.com.micdev.fid2.util.Util
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.ArrayList

class InviteAdapter(val events: ArrayList<InviteObject>, val context: Context) : RecyclerView.Adapter<InviteAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvEventoName.text = events[position].eventName
        holder.tvEventoPreco.text = String.format(context.getString(R.string.cifrao), events[position].eventPrice)
        holder.tvEventoDataInit.text = (events[position].eventStartDate)
    }


    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val tvEventoName = view.textViewNome
        val tvEventoPreco = view.textViewPreco
        val tvEventoDataInit = view.textViewDataInit
        private val naoSeiPqSemUmaValNaoFuncionaEssaMerda = view.setOnClickListener{v->
            Util.showSnackFeedback(tvEventoName.text.toString(),true,v!!,v.context)
        }
        private val naoSeiPqSemUmaValNaoFuncionaEssaMerda2 = view.setOnLongClickListener{v->
            Util.showSnackFeedback(tvEventoPreco.text.toString(),true,v,v.context)

            return@setOnLongClickListener true
        }

    }


}