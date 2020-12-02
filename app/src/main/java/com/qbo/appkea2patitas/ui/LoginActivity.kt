package com.qbo.appkea2patitas.ui

import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var colapeticiones: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        colapeticiones = Volley.newRequestQueue(this)
        btnlogin.setOnClickListener {
            btnlogin.isEnabled = false
            pblogin.visibility = View.VISIBLE
            if(validarUsuarioPassword()){
                autenticacionApiRest(it, etusuario.text.toString(),
                        etpassword.text.toString())
            }else{
                mostrarMensaje(it, getString(R.string.msgerrorlogin))
                pblogin.visibility = View.GONE
                btnlogin.isEnabled = true
            }
        }
        btnregistrar.setOnClickListener {
            startActivity(Intent(this,
                    RegistroActivity::class.java))
        }

    }

    private fun autenticacionApiRest(vista: View, usuario: String, password: String) {
        val urlapilogin = "http://www.kreapps.biz/patitas/login.php"
        val objJson = JSONObject()
        objJson.put("usuario", usuario)
        objJson.put("password", password)
        val request = JsonObjectRequest(
                Request.Method.POST,
                urlapilogin,
                objJson,
                { response ->
                    if(response.getBoolean("rpta")){
                        startActivity(Intent(this,
                                MainActivity::class.java))
                        finish()
                    }else{
                        mostrarMensaje(vista, response.getString("mensaje"))

                    }
                    pblogin.visibility = View.GONE
                    btnlogin.isEnabled = true
                },{ error->
            Log.e("ErrorLogin", error.message.toString())
            pblogin.visibility = View.GONE
            btnlogin.isEnabled = true
        })
        colapeticiones.add(request)
    }

    private fun validarUsuarioPassword(): Boolean{
        var respuesta = true
        if(etusuario.text.toString().trim().isEmpty()){
            etusuario.isFocusableInTouchMode = true
            etusuario.requestFocus()
            respuesta = false
        }else if(etpassword.text.toString().trim().isEmpty()){
            etpassword.isFocusableInTouchMode = true
            etpassword.requestFocus()
            respuesta = false
        }
        return respuesta
    }

    private fun mostrarMensaje(vista: View, mensaje : String){
        Snackbar.make(vista, mensaje, Snackbar.LENGTH_LONG).show()
    }
}