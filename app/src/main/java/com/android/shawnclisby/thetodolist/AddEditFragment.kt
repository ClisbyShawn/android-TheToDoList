package com.android.shawnclisby.thetodolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.shawnclisby.thetodolist.data.TaskViewModel
import com.android.shawnclisby.thetodolist.data.TaskViewModelFactory
import com.android.shawnclisby.thetodolist.databinding.FragmentAddEditBinding

class AddEditFragment : Fragment() {

    private var _binding: FragmentAddEditBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(
            requireActivity().application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditBinding.inflate(inflater, container, false)

        binding.apply {
            taskViewModel.taskData?.observe(viewLifecycleOwner) { task->
                task?.let { editTask->
                    tieAddEditTitle.setText(editTask.title)
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}