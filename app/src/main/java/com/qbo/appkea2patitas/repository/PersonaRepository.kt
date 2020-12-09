package com.qbo.appkea2patitas.repository

import androidx.lifecycle.LiveData
import com.qbo.appkea2patitas.db.dao.PersonaDao
import com.qbo.appkea2patitas.db.entity.PersonaEntity

class PersonaRepository(private val personaDao: PersonaDao) {

    suspend fun insertar(persona: PersonaEntity){
        personaDao.insertar(persona)
    }

    suspend fun actualizar(persona: PersonaEntity){
        personaDao.actualizar(persona)
    }

    suspend fun eliminarTodo(){
        personaDao.eliminarTodo()
    }

    fun obtener(): LiveData<PersonaEntity>{
        return personaDao.obtener()
    }

}