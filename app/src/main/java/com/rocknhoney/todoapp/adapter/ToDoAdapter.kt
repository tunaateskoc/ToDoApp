package com.rocknhoney.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rocknhoney.todoapp.R
import com.rocknhoney.todoapp.listener.OnItemClickListener
import com.rocknhoney.todoapp.model.toDo

class ToDoAdapter(private var listener: OnItemClickListener) :
    RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffUtil = object : DiffUtil.ItemCallback<toDo>() {
        override fun areItemsTheSame(oldItem: toDo, newItem: toDo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: toDo, newItem: toDo): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var toDos: List<toDo>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_row, parent, false)
        return ToDoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val titleText = holder.itemView.findViewById<TextView>(R.id.todo_title_tv)
        val descriptionText = holder.itemView.findViewById<TextView>(R.id.todo_description_tv)
        val dateText = holder.itemView.findViewById<TextView>(R.id.todo_date_tv)

        val toDo = toDos[position]
        holder.itemView.apply {
            titleText.text = "${toDo.title}"
            descriptionText.text = "${toDo.description}"
            dateText.text = "${toDo.date}"
        }
        holder.itemView.setOnClickListener {
            listener.onItemClicked(toDo)
        }
    }

    override fun getItemCount(): Int {
        return toDos.size
    }
}