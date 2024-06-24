package com.example.homework1

import android.annotation.SuppressLint
import com.example.homework1.delegates.endTextItemDelegate
import com.example.homework1.delegates.normalItemDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class TodoAdapter(
    todos: List<Any>,
    onTaskClick: (TodoItem) -> Unit,
    onEndItemClick: () -> Unit,
    private val updateCompletedTasksCount: () -> Unit
) : ListDelegationAdapter<List<Any>>() {

    init {
        delegatesManager.addDelegate(normalItemDelegate(onTaskClick, updateCompletedTasksCount))
        delegatesManager.addDelegate(endTextItemDelegate(onEndItemClick))
        setItems(todos)
    }

}
