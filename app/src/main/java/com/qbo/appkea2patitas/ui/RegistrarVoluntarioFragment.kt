package com.qbo.appkea2patitas.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.qbo.appkea2patitas.R
import com.qbo.appkea2patitas.db.entity.PersonaEntity
import com.qbo.appkea2patitas.viewmodel.PersonaViewModel
import kotlinx.android.synthetic.main.fragment_registrar_voluntario.*
import org.json.JSONObject


class RegistrarVoluntarioFragment : Fragment() {

    private lateinit var colapeticiones : RequestQueue
    private lateinit var personaViewModel: PersonaViewModel
    private lateinit var objpersona: PersonaEntity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        colapeticiones = Volley.newRequestQueue(context)
        personaViewModel = ViewModelProvider(this)
            .get(PersonaViewModel::class.java)
        personaViewModel.obtener()
            .observe(viewLifecycleOwner, Observer { persona->
                persona?.let {
                    if(persona.esvoluntario == "1"){
                        actualizarFragmento()
                    }else{
                        objpersona = persona
                    }
                }
            })
        val vista = inflater.inflate(R.layout.fragment_registrar_voluntario, container, false)
        val btnregistrarvoluntario : Button = vista
            .findViewById(R.id.btnregistrarvoluntario)
        btnregistrarvoluntario.setOnClickListener {
            if(cbaceptarterminos.isChecked){
                btnregistrarvoluntario.isEnabled = false
                registrarVoluntarioApiRest(it)
            }else{
                mostrarMensaje(it, getString(R.string.valerrorvoluntario))
            }
        }
        return vista
    }

    private fun registrarVoluntarioApiRest(vista: View) {
        val urlapivoluntario = "http://www.kreapps.biz/patitas/personavoluntaria.php"
        val parametro = JSONObject()
        parametro.put("idpersona", objpersona.id)
        val request = JsonObjectRequest(
            Request.Method.POST,
            urlapivoluntario,
            parametro,
            {response->
                if(response.getBoolean("rpta")){
                    val nuevaPersona = PersonaEntity(
                        objpersona.id,
                        objpersona.nombres,
                        objpersona.apellidos,
                        objpersona.email,
                        objpersona.celular,
                        objpersona.usuario,
                        objpersona.password,
                        "1"
                    )
                    personaViewModel.actualizar(nuevaPersona)
                    actualizarFragmento()
                }
                mostrarMensaje(vista, response.getString("mensaje"))
                btnregistrarvoluntario.isEnabled = true
            },{
                Log.e("ErrorVoluntario", it.message.toString())
                btnregistrarvoluntario.isEnabled = true
            })
        colapeticiones.add(request)
    }

    private fun actualizarFragmento(){
        tvtextovoluntario.visibility = View.GONE
        btnregistrarvoluntario.visibility = View.GONE
        cbaceptarterminos.visibility = View.GONE
        tvvoluntario.text = getString(R.string.valtituloesvoluntario)
    }

    private fun mostrarMensaje(vista: View, mensaje: String) {
        Snackbar.make(vista, mensaje, Snackbar.LENGTH_LONG).show()
    }

}