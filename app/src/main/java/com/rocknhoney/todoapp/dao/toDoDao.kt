package com.rocknhoney.todoapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rocknhoney.todoapp.model.toDo

@Dao
interface toDoDao {
    @Query("SELECT * FROM toDo")
    fun getAllToDos(): LiveData<List<toDo>>

    @Insert
    suspend fun insertToDo(toDo: toDo)

    @Update
    suspend fun updateToDo(toDo: toDo)

    @Delete
    suspend fun deleteToDo(toDo: toDo)
}