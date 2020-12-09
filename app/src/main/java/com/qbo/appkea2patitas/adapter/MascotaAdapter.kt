package com.qbo.appkea2patitas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qbo.appkea2patitas.R
import com.qbo.appkea2patitas.model.Mascota

class MascotaAdapter(private var lstmascota: List<Mascota>,
                private val context: Context)
    : RecyclerView.Adapter<MascotaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvnommascota : TextView = itemView.findViewById(R.id.tvnommascota)
        val tvlugarperdida : TextView = itemView.findViewById(R.id.tvlugarperdida)
        val tvfechaperdida : TextView = itemView.findViewById(R.id.tvfechaperdida)
        val tvcontacto : TextView = itemView.findViewById(R.id.tvcontacto)
        val ivmascota : ImageView = itemView.findViewById(R.id.ivmascota)
        val contenedor: CardView = itemView.findViewById(R.id.contenedor)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotaAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(
                R.layout.item_mascota,
                parent,
                false
        ))
    }
    override fun onBindViewHolder(holder: MascotaAdapter.ViewHolder, position: Int) {
        val itemMascota = lstmascota[position]
        holder.tvnommascota.text = itemMascota.nommascota
        holder.tvfechaperdida.text = itemMascota.fechaperdida
        holder.tvlugarperdida.text = itemMascota.lugar
        holder.tvcontacto.text = itemMascota.contacto
        Glide.with(context).load(itemMascota.urlimagen)
                .into(holder.ivmascota)
        /*holder.contenedor.setOnClickListener {

        }*/
    }
    override fun getItemCount(): Int {
        return lstmascota.size
    }


}