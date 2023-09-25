package com.example.a6homework1task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a6homework1task.model.TaskModel

class MainViewModel : ViewModel() {


    private var data = mutableListOf<TaskModel>()
    private val _liveData = MutableLiveData<MutableList<TaskModel>>(mutableListOf())
    val liveData: LiveData<MutableList<TaskModel>>
        get() = _liveData


    fun addTask(title: String) {
        data.add(TaskModel(id = data.size, title = title))

    }


    fun removeTask(task: TaskModel) {
        data.remove(task)
       data.removeAt(task.id)
    }
// checked method
}

