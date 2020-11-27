package com.qbo.appkea2patitas.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.qbo.appkea2patitas.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnlogin.setOnClickListener {
            if(validarUsuarioPassword()){
                mostrarMensaje(it, "Llamar al API Rest de login")
            }else{
                mostrarMensaje(it, getString(R.string.msgerrorlogin))
            }
        }
        btnregistrar.setOnClickListener {
            startActivity(Intent(this,
                    RegistroActivity::class.java))
        }

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