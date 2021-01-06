package com.android.shawnclisby.thetodolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.shawnclisby.androidauth.viewModels.AuthViewModel
import com.android.shawnclisby.thetodolist.data.TaskViewModel
import com.android.shawnclisby.thetodolist.data.models.Task
import com.android.shawnclisby.thetodolist.databinding.FragmentHomeBinding
import com.android.shawnclisby.thetodolist.ui.TaskRecyclerAdapter
import com.android.shawnclisby.thetodolist.util.hideKeyboard
import com.android.shawnclisby.thetodolist.util.lowBounceStiffnessTranslationY
import com.android.shawnclisby.thetodolist.util.showKeyboard

class HomeFragment : Fragment(), TaskRecyclerAdapter.TaskInteraction {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var authViewModel: AuthViewModel
    private lateinit var taskViewModelFactory: TaskViewModelFactory
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)

        taskViewModelFactory = TaskViewModelFactory(requireActivity().application)
        taskViewModel =
            ViewModelProvider(this,taskViewModelFactory)
                .get(TaskViewModel::class.java)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val taskAdapter = TaskRecyclerAdapter(requireContext(), this)

        binding.apply {

            rvHomeTaskList.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = taskAdapter
            }

            bottomAppBar.apply {
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.search -> {
                            homeViewModel.onToggled.invoke()
                            true
                        }

                        else -> false
                    }
                }

                setNavigationOnClickListener {
                    taskViewModel.toggleFilter()
                }
            }

            tieHomeSearch.apply {
                addTextChangedListener { newText ->
                    taskViewModel.searchString.value = newText.toString().trim()
                }

                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        homeViewModel.onToggled.invoke()
                        true
                    }
                    false
                }
            }

            fabHomeNewTask.setOnClickListener {
               //Navigate to Add/Edit Task Fragment
            }
        }

        homeViewModel.searchBar.observe(viewLifecycleOwner, { searchBar ->
            searchBar.showHide?.let { showHide ->
                if (showHide) applyShowSearchBarAnimation()
                else applyHideSearchBarAnimation()
            }
        })

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

    private fun applyShowSearchBarAnimation() {
        binding.apply {
            rvHomeTaskList.lowBounceStiffnessTranslationY(165f)
            tilHomeSearch.lowBounceStiffnessTranslationY(5f)

            tieHomeSearch.apply {
                requestFocus()
                showKeyboard(requireContext())
                imeOptions = EditorInfo.IME_ACTION_DONE
            }
        }
    }

    private fun applyHideSearchBarAnimation() {
        binding.apply {
            rvHomeTaskList.lowBounceStiffnessTranslationY(0f)
            tilHomeSearch.lowBounceStiffnessTranslationY(-205f)
            tieHomeSearch.hideKeyboard(requireContext())
        }
    }
}