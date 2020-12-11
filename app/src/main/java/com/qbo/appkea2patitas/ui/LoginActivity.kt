package com.qbo.appkea2patitas.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
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
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var colapeticiones: RequestQueue

    private lateinit var preferencias: SharedPreferences

    private lateinit var personaViewModel: PersonaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //Inicializando la cola de peticiones.
        colapeticiones = Volley.newRequestQueue(this)
        //Inicializando las preferencias de la aplicación.
        preferencias = getSharedPreferences("appPatitas", MODE_PRIVATE)
        //Inicializando el ViewModel
        personaViewModel = ViewModelProvider(this)
            .get(PersonaViewModel::class.java)
        //Validamos que exista la preferencia recordardatos
        if(verificarValorSharedPreferences()){
            cbguardarinfo.isChecked = true
            personaViewModel.obtener()
                .observe(this, Observer { persona->
                    persona?.let {
                        etusuario.setText(persona.usuario)
                        etpassword.setText(persona.password)
                    }
                })
        }else{
            personaViewModel.eliminarTodo()
        }
        cbguardarinfo.setOnClickListener {
            setearValoresDeRecordar(it)
        }
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

    private fun setearValoresDeRecordar(vista: View) {
        if(vista is CheckBox){
            val checked: Boolean = vista.isChecked
            when(vista.id){
                R.id.cbguardarinfo ->{
                    if(!checked){
                        if(verificarValorSharedPreferences()){
                            personaViewModel.eliminarTodo()
                            etusuario.setText("")
                            etpassword.setText("")
                            preferencias.edit().remove("recordardatos")
                                .apply()
                        }
                    }
                }
            }
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
                        val persona = PersonaEntity(
                            response.getString("idpersona").toInt(),
                            response.getString("nombres"),
                            response.getString("apellidos"),
                            response.getString("email"),
                            response.getString("celular"),
                            response.getString("usuario"),
                            response.getString("password"),
                            response.getString("esvoluntario")
                        )
                        if(verificarValorSharedPreferences()){
                            personaViewModel.actualizar(persona)
                        }else{
                            personaViewModel.insertar(persona)
                            if(cbguardarinfo.isChecked){
                                preferencias.edit()
                                    .putBoolean("recordardatos", true)
                                    .apply()
                            }
                        }
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

    private fun verificarValorSharedPreferences(): Boolean{
        return preferencias.getBoolean("recordardatos", false)
    }

}