package com.example.a6homework1task.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a6homework1task.MainViewModel
import com.example.a6homework1task.R
import com.example.a6homework1task.databinding.FragmentMainBinding
import com.example.a6homework1task.model.TaskModel
import com.example.a6homework1task.ui.adapter.MainFragmentAdapter


class MainFragment : Fragment(), MainFragmentAdapter.Listener {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel
        get() = ViewModelProvider(this)[MainViewModel::class.java]

    private var adapter = MainFragmentAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initListener()
        initStart()
    }

    private fun initStart() {
        setFragmentResultListener(ADD_TASK_TO_MAIN) { _, bundle ->
            bundle.getString(TASK_KEY)?.let {
                viewModel.addTask(it)
            }
        }

    }

    private fun initRecyclerView() {
        viewModel.liveData.observe(viewLifecycleOwner) { list ->
            adapter.addTaskData(list)
            binding.rcViewMain.adapter = adapter
        }
    }

    private fun initListener() {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.secondFragment)
        }
    }


    override fun onLongClickItem(task: TaskModel) {
        showDeleteDialog(task)
    }

    override fun onClickItem(task: TaskModel) {
        //viewModel.setTaskDone(task)

    }


    private fun showDeleteDialog(task: TaskModel) {
        val alertBuilder = AlertDialog.Builder(requireContext())
        alertBuilder.setTitle(getString(R.string.delete))
        alertBuilder.setMessage(getString(R.string.delete_message))
        alertBuilder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            viewModel.removeTask(task)
            adapter.notifyDataSetChanged()
        }
        alertBuilder.setNeutralButton(getString(R.string.cancel))
        { _, _ ->
        }
        alertBuilder.show()
    }


    companion object {
        const val TASK_KEY = "task.key"
        const val ADD_TASK_TO_MAIN = "add_task_to_main.key"
    }
}