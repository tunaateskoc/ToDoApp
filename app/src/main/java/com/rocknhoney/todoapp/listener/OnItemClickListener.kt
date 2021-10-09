package com.rocknhoney.todoapp.listener

import com.rocknhoney.todoapp.model.toDo

interface OnItemClickListener {

    fun onItemClicked(toDo: toDo)
}