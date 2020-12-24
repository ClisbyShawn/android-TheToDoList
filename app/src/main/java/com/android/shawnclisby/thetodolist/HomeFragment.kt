package com.android.shawnclisby.thetodolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.shawnclisby.androidauth.viewModels.AuthViewModel
import com.android.shawnclisby.thetodolist.data.Task
import com.android.shawnclisby.thetodolist.data.TaskViewModel
import com.android.shawnclisby.thetodolist.databinding.FragmentHomeBinding
import com.android.shawnclisby.thetodolist.ui.TaskRecyclerAdapter


class HomeFragment : Fragment(), TaskRecyclerAdapter.TaskInteraction {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var authViewModel: AuthViewModel
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val taskAdapter = TaskRecyclerAdapter(requireContext(), this)

        binding.rvHomeTaskList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }

        taskViewModel.taskList.observe(viewLifecycleOwner, { tasks ->
            taskAdapter.submitList(tasks)
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTaskItemClicked(task: Task) {
        Toast.makeText(requireContext(), "Clicked ${task.id}", Toast.LENGTH_SHORT).show()
    }

    override fun onTaskCompletionChanged(id: Int, checked: Boolean) {
        Toast.makeText(
            requireContext(),
            "Clicked ${id}, completion:${checked}",
            Toast.LENGTH_SHORT
        ).show()
    }
}