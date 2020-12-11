package com.qbo.appkea2patitas.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.qbo.appkea2patitas.R
import com.qbo.appkea2patitas.viewmodel.PersonaViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var navView: NavigationView
    private lateinit var personaViewModel: PersonaViewModel
    private lateinit var preferencias : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferencias = getSharedPreferences("appPatitas", MODE_PRIVATE)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_registravoluntario, R.id.nav_listamascota
        ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        mostrarInformacionLogin()
    }

    private fun mostrarInformacionLogin(){
        val tvnomusuarioh: TextView = navView.getHeaderView(0)
            .findViewById(R.id.tvnomusuarioh)
        val tvemailusuarioh: TextView = navView.getHeaderView(0)
            .findViewById(R.id.tvemailusuarioh)
        personaViewModel = ViewModelProvider(this)
            .get(PersonaViewModel::class.java)
        personaViewModel.obtener()
            .observe(this, Observer { persona->
                persona?.let {
                    tvnomusuarioh.text = persona.nombres
                    tvemailusuarioh.text = persona.email
                }
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemcerrar = item.itemId
        if(itemcerrar == R.id.action_cerrar){
            if(!preferencias.getBoolean("recordardatos", false)){
                personaViewModel.eliminarTodo()
            }
            startActivity(Intent(this,
                LoginActivity::class.java))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}