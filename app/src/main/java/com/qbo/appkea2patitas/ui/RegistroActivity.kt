package com.qbo.appkea2patitas.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.qbo.appkea2patitas.R
import kotlinx.android.synthetic.main.activity_registro.*
import org.json.JSONObject

class RegistroActivity : AppCompatActivity() {

    private lateinit var colapeticiones : RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        colapeticiones = Volley.newRequestQueue(this)
        btnregistrarusuario.setOnClickListener {
            btnregistrarusuario.isEnabled = false
            pbregistrar.visibility = View.VISIBLE
            if(validarFomulario(it)){
                //mostrarMensaje(it, "Llamar a API Rest registro")
                registroUsuarioApiRest(it)
            }else{
                btnregistrarusuario.isEnabled = true
                pbregistrar.visibility = View.GONE
            }
        }
    }

    private fun registroUsuarioApiRest(vista: View) {
        val urlapiregistro = "http://www.kreapps.biz/patitas/persona.php"
        val parametroJson = JSONObject()
        parametroJson.put("nombres", etnombrereg.text.toString())
        parametroJson.put("apellidos", etapellidoreg.text.toString())
        parametroJson.put("email", etemailreg.text.toString())
        parametroJson.put("celular", etcelularreg.text.toString())
        parametroJson.put("usuario", etusuarioreg.text.toString())
        parametroJson.put("password", etpasswordreg.text.toString())
        val request = JsonObjectRequest(
                Request.Method.PUT,
                urlapiregistro,
                parametroJson,
                { response->
                    if(response.getBoolean("rpta")){
                        setearControles()
                    }
                    mostrarMensaje(vista, response.getString("mensaje"))
                    btnregistrarusuario.isEnabled = true
                    pbregistrar.visibility = View.GONE
                },{
                    Log.e("ErrorRegistro", it.toString())
            btnregistrarusuario.isEnabled = true
            pbregistrar.visibility = View.GONE
        })
        colapeticiones.add(request)
    }

    private fun setearControles(){
        etnombrereg.setText("")
        etapellidoreg.setText("")
        etemailreg.setText("")
        etcelularreg.setText("")
        etusuarioreg.setText("")
        etpasswordreg.setText("")
        etreppasswordreg.setText("")
    }

    private fun validarFomulario(vista: View): Boolean{
        var respuesta = true
        when{
            etnombrereg.text.toString().trim().isEmpty()-> {
                etnombrereg.isFocusableInTouchMode = true
                etnombrereg.requestFocus()
                mostrarMensaje(vista, "Ingrese su nombre")
                respuesta = false
            }
            etapellidoreg.text.toString().trim().isEmpty()-> {
                etapellidoreg.isFocusableInTouchMode = true
                etapellidoreg.requestFocus()
                mostrarMensaje(vista, "Ingrese su apellido")
                respuesta = false
            }
            etemailreg.text.toString().trim().isEmpty()-> {
                etemailreg.isFocusableInTouchMode = true
                etemailreg.requestFocus()
                mostrarMensaje(vista, "Ingrese su email")
                respuesta = false
            }
            etcelularreg.text.toString().trim().isEmpty()-> {
                etcelularreg.isFocusableInTouchMode = true
                etcelularreg.requestFocus()
                mostrarMensaje(vista, "Ingrese su celular")
                respuesta = false
            }
            etusuarioreg.text.toString().trim().isEmpty()-> {
                etusuarioreg.isFocusableInTouchMode = true
                etusuarioreg.requestFocus()
                mostrarMensaje(vista, "Ingrese su usuario")
                respuesta = false
            }
            etpasswordreg.text.toString().trim().isEmpty()-> {
                etpasswordreg.isFocusableInTouchMode = true
                etpasswordreg.requestFocus()
                mostrarMensaje(vista, "Ingrese su password")
                respuesta = false
            }
            etpasswordreg.text.toString().trim().isNotEmpty()-> {
                if(etpasswordreg.text.toString() != etreppasswordreg.text.toString().trim()){
                    etreppasswordreg.isFocusableInTouchMode = true
                    etreppasswordreg.requestFocus()
                    mostrarMensaje(vista, "Su password no coincide")
                    respuesta = false
                }
            }
        }
        return respuesta
    }


    private fun mostrarMensaje(vista: View, mensaje: String){
        Snackbar.make(vista, mensaje, Snackbar.LENGTH_LONG).show()
    }
}