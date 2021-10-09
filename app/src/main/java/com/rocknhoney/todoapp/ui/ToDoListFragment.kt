package com.rocknhoney.todoapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rocknhoney.todoapp.R
import com.rocknhoney.todoapp.adapter.ToDoAdapter
import com.rocknhoney.todoapp.listener.OnItemClickListener
import com.rocknhoney.todoapp.model.toDo
import com.rocknhoney.todoapp.viewmodel.ToDoViewModel


class ToDoListFragment : Fragment(), OnItemClickListener {

    private lateinit var toDoViewModel: ToDoViewModel

    private val toDoAdapter: ToDoAdapter = ToDoAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_to_do_list, container, false)

        initAddButton(root)
        initToDoRecyclerView(root)
        initObserver()
        return root
    }

    private fun initAddButton(root: View) {
        val addBtn = root.findViewById<FloatingActionButton>(R.id.todo_add_btn)

        addBtn.setOnClickListener {
            findNavController().navigate(ToDoListFragmentDirections.actionToDoListFragmentToTodoDetailFragment(toDo("","",""),1))
        }

    }

    private fun initToDoRecyclerView(root: View) {
        val toDoRecyclerView = root.findViewById<RecyclerView>(R.id.todo_recycler_view)

//        val linearLayoutManager = LinearLayoutManager(this)
//        linearLayoutManager.reverseLayout = true
//        linearLayoutManager.stackFromEnd = true

        toDoRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        toDoRecyclerView.adapter = toDoAdapter
    }

    private fun initObserver() {
        toDoViewModel = ViewModelProvider(this).get(ToDoViewModel::class.java)
        toDoViewModel.readAllToDos.observe(requireActivity(), Observer { toDos ->
            toDos.let {
                toDoAdapter.toDos = it
            }
        })
    }

    override fun onItemClicked(toDo: toDo) {
        findNavController().navigate(ToDoListFragmentDirections.actionToDoListFragmentToTodoDetailFragment(toDo,2))
    }
}