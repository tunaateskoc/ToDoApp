package com.rocknhoney.todoapp.repository

import androidx.lifecycle.LiveData
import com.rocknhoney.todoapp.dao.toDoDao
import com.rocknhoney.todoapp.model.toDo

class toDoRepository(private val toDoDao: toDoDao){

    suspend fun insertToDo(toDo: toDo) = toDoDao.insertToDo(toDo)

    suspend fun updateToDo(toDo: toDo) = toDoDao.updateToDo(toDo)

    suspend fun deleteToDo(toDo: toDo) = toDoDao.deleteToDo(toDo)

    fun getAllToDos(): LiveData<List<toDo>> = toDoDao.getAllToDos()

}