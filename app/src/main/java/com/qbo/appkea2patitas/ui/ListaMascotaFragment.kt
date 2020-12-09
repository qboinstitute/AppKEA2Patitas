package com.qbo.appkea2patitas.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.qbo.appkea2patitas.R
import com.qbo.appkea2patitas.adapter.MascotaAdapter
import com.qbo.appkea2patitas.model.Mascota
import kotlinx.android.synthetic.main.fragment_lista_mascota.*


class ListaMascotaFragment : Fragment() {

    private lateinit var colaPeticiones: RequestQueue

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val vista: View = inflater.inflate(R.layout.fragment_lista_mascota,
                container, false)
        colaPeticiones = Volley.newRequestQueue(context)
        val rvmascotas : RecyclerView = vista.findViewById(R.id.rvmascotas)
        rvmascotas.layoutManager = LinearLayoutManager(context)
        listarMascotasApiRest(vista.context)
        return vista
    }

    private fun listarMascotasApiRest(context: Context) {
        val lstMascota : ArrayList<Mascota> = ArrayList()
        val uriapirest = "http://www.kreapps.biz/patitas/mascotaperdida.php"
        val request = JsonArrayRequest(
                Request.Method.GET,
                uriapirest,
                null,
                { response->
                  for (i in 0 until response.length()){
                      val item = response.getJSONObject(i)
                      lstMascota.add(Mascota(
                              item["nommascota"].toString(),
                              item["fechaperdida"].toString(),
                              item["urlimagen"].toString(),
                              item["lugar"].toString(),
                              item["contacto"].toString()
                      ))
                  }
                    rvmascotas.adapter = MascotaAdapter(lstMascota, context)
                },{
                Log.e("ErrorLista", it.message.toString())
        })
        colaPeticiones.add(request)
    }

}