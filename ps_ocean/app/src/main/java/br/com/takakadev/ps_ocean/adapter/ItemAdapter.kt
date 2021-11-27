package br.com.takakadev.ps_ocean.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.takakadev.ps_ocean.R
import br.com.takakadev.ps_ocean.model.Meta

class ItemAdapter(private val dataset: List<Meta>,
                  private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(
        val view: View,
        val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val tvNMeta: TextView = view.findViewById(R.id.tvNMeta)
        val tvNacoesUnidas: TextView = view.findViewById(R.id.tvNacoesUnidas)
        val tvAlteracaoBrasil: TextView = view.findViewById(R.id.tvAlteracaoBrasil)
        val tvIndicadores: TextView = view.findViewById(R.id.tvIndicadores)
        val btVerMeta: Button = view.findViewById(R.id.btApagar)
        val llMetas: LinearLayout = view.findViewById(R.id.llMetas)

        init {
            llMetas.setOnClickListener(this)
            btVerMeta.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            if (v!!.id == R.id.llMetas) {
                onItemClickListener.onItemClick(adapterPosition, "VIEW")
            } else {
                onItemClickListener.onItemClick(adapterPosition, "DELETE")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout, onItemClickListener)
    }

        //função usada pelo layoutManager para aplicar os dados do dataset a cada item da lista
        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val meta = dataset[position]
            holder.tvNMeta.text = meta.nmeta
            holder.tvNacoesUnidas.text = meta.nacoesunidas
            holder.tvAlteracaoBrasil.text = meta.brasil
            holder.tvIndicadores.text = meta.indicadores
            holder.btVerMeta.setOnClickListener(holder)
    }

    //função utilizada pelo layoutManager para descobrir o tamanho da lista de dados (dataset)
    override fun getItemCount() = dataset.size

    interface OnItemClickListener {
        fun onItemClick(item: Int, acao: String)
    }
}