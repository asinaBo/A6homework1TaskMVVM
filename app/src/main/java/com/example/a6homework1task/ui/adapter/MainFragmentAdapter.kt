package com.example.a6homework1task.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.persistableBundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.a6homework1task.databinding.ItemMainTaskBinding
import com.example.a6homework1task.model.TaskModel

class MainFragmentAdapter(
    private val listener: Listener
) :
    Adapter<MainFragmentAdapter.ViewHolder>() {

    private val _tasks = mutableListOf<TaskModel>()
    private val tasks get() = _tasks

    fun addTaskData(taskList: List<TaskModel>) {
        _tasks.clear()
        _tasks.addAll(taskList)
        notifyItemRangeInserted(_tasks.size, taskList.size - _tasks.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemMainTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    inner class ViewHolder(private val binding: ItemMainTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(taskModel: TaskModel) {
            binding.checkboxTask.text = taskModel.title
            binding.checkboxTask.isChecked = taskModel.checked

            itemView.setOnLongClickListener() {
                listener.onLongClickItem(taskModel)
                true
            }
            itemView.setOnClickListener() {
                listener.onClickItem(taskModel)
            }
        }
    }

    interface Listener {
        fun onLongClickItem(task: TaskModel)
        fun onClickItem(task: TaskModel)
    }
}
