package com.qbo.appkea2patitas.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.qbo.appkea2patitas.R
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        btnregistrarusuario.setOnClickListener {
            if(validarFomulario(it)){
                mostrarMensaje(it, "Llamar a API Rest registro")
            }
        }
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