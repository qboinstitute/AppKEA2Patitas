package com.qbo.appkea2patitas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.qbo.appkea2patitas.db.PatitasRoomDatabase
import com.qbo.appkea2patitas.db.entity.PersonaEntity
import com.qbo.appkea2patitas.repository.PersonaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonaViewModel(application: Application)
    : AndroidViewModel(application)
{
        private val repository: PersonaRepository

        init {
            val personaDao = PatitasRoomDatabase.getDatabase(application)
                .personaDao()
            repository = PersonaRepository(personaDao)
        }

    fun insertar(persona: PersonaEntity) = viewModelScope
        .launch(Dispatchers.IO){
        repository.insertar(persona)
    }

    fun actualizar(persona: PersonaEntity) = viewModelScope
        .launch(Dispatchers.IO){
            repository.actualizar(persona)
        }

    fun eliminarTodo() = viewModelScope.launch(Dispatchers.IO) {
        repository.eliminarTodo()
    }

    fun obtener(): LiveData<PersonaEntity>{
        return repository.obtener()
    }






    }