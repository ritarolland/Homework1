package com.example.homework1.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.example.homework1.Importance
import com.example.homework1.R
import com.example.homework1.TodoItem
import com.example.homework1.TodoViewModel
import com.example.homework1.databinding.FragmentAddBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val todoViewModel: TodoViewModel by activityViewModels()

    private val calendar = Calendar.getInstance()
    private var currentDate:Date? = null
    private var currentId: String? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val EDIT_ID = "edit_item_id_key"

        fun newInstance(id: String): AddFragment {
            val fragment = AddFragment().apply {
                arguments = bundleOf(EDIT_ID to id)
            }
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner = binding.spinnerImportance
        spinner.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.importance_options,
            android.R.layout.simple_spinner_item
        )
        val idToEdit = arguments?.getString(EDIT_ID)
        currentId = idToEdit

        if(idToEdit != null) {
            val todo : TodoItem? = todoViewModel.getItemById(idToEdit)
            todo?.let {
                binding.editText.setText(it.text)
                val importanceOptions = resources.getStringArray(R.array.importance_options)
                val position = importanceOptions.indexOf(it.importance.toString())
                if (position != -1) {
                    spinner.setSelection(position)
                }

                if(todo.deadline!=null) {
                    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val formattedDeadline =  formatter.format(todo.deadline)
                    binding.date.text = formattedDeadline
                }

                binding.switchCalendar.setOnCheckedChangeListener(null)
                binding.switchCalendar.isChecked = todo.deadline != null

            }
        }



        binding.switchCalendar.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showDatePicker()
            } else {
                binding.date.visibility = View.INVISIBLE
                currentDate = null
            }
        }

        binding.delete.setOnClickListener{
            if(currentId != null) {
                val todo = todoViewModel.getItemById(currentId.toString())
                if (todo != null) {
                    todoViewModel.removeTodoItem(todo)
                }
            }
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.save.setOnClickListener {

            val selectedImportance = spinner.selectedItem.toString()
            val newTask = TodoItem(
                currentId,
                binding.editText.text.toString(),
                getImportanceFromString(selectedImportance),
                currentDate,
                false,
                Date()
            )
            todoViewModel.addOrEditTodoItem(newTask)
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun showDatePicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.date.text = selectedDate
                binding.date.visibility = View.VISIBLE

                currentDate = selectedCalendar.time
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    private fun getImportanceFromString(importance: String): Importance {
        return when (importance) {
            "Низкая" -> Importance.LOW
            "Обычная" -> Importance.NORMAL
            "Высокая" -> Importance.HIGH
            else -> Importance.NORMAL
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}