package com.rocknhoney.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rocknhoney.todoapp.database.toDoDatabase
import com.rocknhoney.todoapp.model.toDo
import com.rocknhoney.todoapp.repository.toDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application): AndroidViewModel(application) {
    private val repository: toDoRepository
    val readAllToDos: LiveData<List<toDo>>

    init {
        val db = toDoDatabase.getInstance(context = application)!!.todoDao()
        repository = toDoRepository(db)
        readAllToDos = repository.getAllToDos()
    }

    suspend fun addToDo(toDo: toDo){
        repository.insertToDo(toDo)
    }
   suspend fun updateToDo(toDo: toDo){
       repository.updateToDo(toDo)
    }
    suspend fun deleteToDo(toDo: toDo){
        repository.deleteToDo(toDo)
    }
}