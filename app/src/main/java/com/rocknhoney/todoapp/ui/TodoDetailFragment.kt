package com.rocknhoney.todoapp.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rocknhoney.todoapp.R
import com.rocknhoney.todoapp.model.toDo
import com.rocknhoney.todoapp.viewmodel.ToDoViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TodoDetailFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: ToDoViewModel

    private val args by navArgs<TodoDetailFragmentArgs>()

    private lateinit var toDo: toDo

    private var style: Int = 1

    private lateinit var addButton: Button

    private lateinit var updateButton: Button

    private lateinit var deleteButton: Button

    private lateinit var titleEditText: EditText

    private lateinit var descriptionEditText: EditText

    private lateinit var dateEditText: EditText

    private val myCalendar = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.todo_detail_fragment, container, false)

        initObserver()
        initView(root)
        initListeners()
        return root
    }

    private fun initObserver() {
        viewModel = ViewModelProvider(this).get(ToDoViewModel::class.java)
    }

    private fun initView(root: View) {
        addButton = root.findViewById(R.id.todo_add_btn)
        updateButton = root.findViewById(R.id.todo_update_btn)
        deleteButton = root.findViewById(R.id.todo_delete_btn)
        titleEditText = root.findViewById(R.id.todo_title_et)
        descriptionEditText = root.findViewById(R.id.todo_description_et)
        dateEditText = root.findViewById(R.id.todo_date_et)

        style = args.DetailFragmentType

        if (style == 1) {
            updateButton.visibility = View.GONE
            deleteButton.visibility = View.GONE
            addButton.visibility = View.VISIBLE
        } else {
            updateButton.visibility = View.VISIBLE
            deleteButton.visibility = View.VISIBLE
            addButton.visibility = View.GONE
            toDo = args.todo
            titleEditText.setText(toDo.title)
            descriptionEditText.setText(toDo.description)
            dateEditText.setText(toDo.date)
        }
    }

    private fun initListeners() {
        addButton.setOnClickListener {
            val toDo = toDo(
                titleEditText.text.toString(),
                descriptionEditText.text.toString(),
                dateEditText.text.toString()
            )
            lifecycleScope.launch {
                viewModel.addToDo(toDo)
            }
            findNavController().popBackStack()
            Toast.makeText(requireActivity(), "ToDo Added", Toast.LENGTH_LONG).show()
        }
        updateButton.setOnClickListener {
            toDo.title = titleEditText.text.toString()
            toDo.description = descriptionEditText.text.toString()
            toDo.date = dateEditText.text.toString()
            lifecycleScope.launch {
                viewModel.updateToDo(toDo)
            }
            findNavController().popBackStack()
            Toast.makeText(requireActivity(), "ToDo Updated", Toast.LENGTH_LONG).show()
        }
        deleteButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.deleteToDo(toDo)
            }
            findNavController().popBackStack()
            Toast.makeText(requireActivity(), "ToDo Deleted", Toast.LENGTH_LONG).show()
        }
        dateEditText.setOnClickListener {
            val dialog = DatePickerDialog(
                requireContext(),
                this,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dateEditText.setText(
            StringBuilder()
                .append(dayOfMonth).append("/").append(month + 1).append("/").append(year)
                .append(" ")
        )
    }
}