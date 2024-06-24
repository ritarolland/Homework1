package com.example.homework1.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework1.Importance
import com.example.homework1.R
import com.example.homework1.SwipeToDeleteCallback
import com.example.homework1.TodoAdapter
import com.example.homework1.TodoItem
import com.example.homework1.TodoViewModel
import com.example.homework1.databinding.FragmentMainBinding
import java.util.Date

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter : TodoAdapter
    private val todoViewModel: TodoViewModel by activityViewModels()

    private val tasks : MutableList<Any> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingButton.setOnClickListener { openAddFragment() }
        taskAdapter = TodoAdapter(
            tasks,
            onTaskClick = { todoItem ->
                /*val bundle = Bundle().apply {
                    putString("id_key", todoItem.id)
                }
                val fragment = AddFragment().apply {
                    arguments = bundle
                }
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()*/
                todoItem.id?.let { AddFragment.newInstance(it) }?.let { changeFragment(it) }
            },
            onEndItemClick = {
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, AddFragment())
                transaction.addToBackStack(null)
                transaction.commit()
            }, updateCompletedTasksCount = {updateCompletedTasksCount()})
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskAdapter
        }

        updateCompletedTasksCount()

        todoViewModel.todos.observe(viewLifecycleOwner, Observer { todos ->
            updateTodoList(todos)
        })
        todoViewModel.completedTasksCount.observe(viewLifecycleOwner, Observer { count ->
            updateCompletedTasksCount()
        })

        binding.eye.setOnClickListener{
            todoViewModel.toggleShowCompletedTasks()
        }

        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwipedLeft(position: Int) {
                val item = taskAdapter.items?.get(position) as TodoItem
                todoViewModel.removeTodoItem(item)
            }

            override fun onSwipedRight(position: Int) {
                val item = taskAdapter.items?.get(position) as TodoItem
                todoViewModel.checkItem(item, true)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    @SuppressLint("SetTextI18n")
    private fun updateCompletedTasksCount() {
        val completedTasksCount = todoViewModel.countCompletedTasks()
        binding.done.text = "Выполнено - $completedTasksCount"
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTodoList(newList: List<Any>) {
        tasks.clear()
        tasks.addAll(newList)
        taskAdapter.notifyDataSetChanged()
    }

    private fun openAddFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AddFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun changeFragment(fragment: Fragment) {
        val fragmentTransaction = requireActivity()
            .supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}