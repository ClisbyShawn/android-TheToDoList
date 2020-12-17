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
import com.android.shawnclisby.thetodolist.databinding.FragmentHomeBinding
import com.android.shawnclisby.thetodolist.ui.TaskRecyclerAdapter


class HomeFragment : Fragment(), TaskRecyclerAdapter.TaskInteraction {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val tempList = listOf(
            Task(0, "Take out trash", priority = true, completed = true),
            Task(1, "Wash the dishes", priority = true, completed = false),
            Task(
                2,
                "Put the Christmas lights up before December 25th, 2020.",
                priority = false,
                completed = false
            ),
            Task(3, "Check the mailbox today.", priority = false, completed = true),
            Task(4, "Platinum God of War: Chains of Olympus", priority = false, completed = false),
            Task(
                5,
                "Build this app and test/add more features.",
                priority = true,
                completed = false
            )

        )

        val taskAdapter = TaskRecyclerAdapter(requireContext(), this)
        taskAdapter.submitList(tempList)

        binding.rvHomeTaskList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
        }

//        authViewModel.user.observe(viewLifecycleOwner, { response ->
//            response.data?.let { user ->
//                binding.tvMainUser.text = "$user"
//            }
//        })

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