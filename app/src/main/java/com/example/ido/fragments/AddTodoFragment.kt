package com.example.ido.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.ido.databinding.FragmentAddTodoBinding
import com.example.ido.utils.TodoData
import com.google.android.material.textfield.TextInputEditText


class AddTodoFragment : DialogFragment() {
    private lateinit var binding: FragmentAddTodoBinding
    private lateinit var listener: DialogNextBtnClickListener

    private var todoData: TodoData? = null

    fun setListener(listener: DialogNextBtnClickListener) {
        this.listener = listener

    }

    companion object {
        const val TAG = "AddTodoFragment"

        @JvmStatic
        fun newInstance(taskId: String, task: String) = AddTodoFragment().apply {
            arguments = Bundle().apply {
                putString("taskId", taskId)
                putString("task", task)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            todoData = TodoData(
                arguments?.getString("taskId").toString(),
                arguments?.getString("task").toString()
            )
            binding.todoEt.setText(todoData?.task)
        }
        registerEvents()
    }

    private fun registerEvents() {
        binding.todoNextBtn.setOnClickListener {
            val todoTask = binding.todoEt.text.toString()
            if (todoTask.isNotEmpty()) {
                if (todoData == null){
                    listener.onSaveTask(todoTask, binding.todoEt)
                }
                else{
                    todoData?.task = todoTask
                    listener.onUpdateTask(todoData!!, binding.todoEt)
                }

            } else {
                Toast.makeText(context, "Please add a Task", Toast.LENGTH_SHORT).show()
            }
        }
        binding.todoClose.setOnClickListener {
            dismiss()
        }
    }

    interface DialogNextBtnClickListener {
        fun onSaveTask(todo: String, todoEt: TextInputEditText)
        fun onUpdateTask(todoData: TodoData, todoEt: TextInputEditText)
    }


}