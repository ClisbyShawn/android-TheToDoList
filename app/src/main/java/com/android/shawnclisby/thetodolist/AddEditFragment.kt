package com.android.shawnclisby.thetodolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.shawnclisby.thetodolist.data.TaskViewModel
import com.android.shawnclisby.thetodolist.data.TaskViewModelFactory
import com.android.shawnclisby.thetodolist.databinding.FragmentAddEditBinding
import java.util.*

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

            cvAddEditDueDate.apply {
                date = Date().time
                setOnDateChangeListener { _, year, month, dayOfMonth ->
                    val cal = Calendar.getInstance()
                    cal.set(year, month, dayOfMonth)
                    taskViewModel.updateDate(cal.timeInMillis)
                }
            }

            fabAddEditSaveEdit.setOnClickListener {
                val title = tieAddEditTitle.text.toString().trim()
                val description = tieAddEditDescription.text.toString().trim()
                val priority = chkbxAddEditPriority.isChecked
                taskViewModel.saveEditTask(title, description, priority)
                navigateBackHome()
            }

            taskViewModel.taskData.observe(viewLifecycleOwner) { task ->
                task?.let { editTask ->
                    fabAddEditSaveEdit.setImageResource(R.drawable.ic_edit)
                    tieAddEditTitle.setText(editTask.title)
                    tieAddEditDescription.setText(editTask.description)
                    chkbxAddEditPriority.isChecked = editTask.priority
                    editTask.dueDate?.let {
                        tvAddEditDueDate.text = editTask.dueDateFormat
                        cvAddEditDueDate.date = it
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateBackHome() {
        findNavController().navigate(R.id.action_addEditFragment_to_homeFragment)
    }
}