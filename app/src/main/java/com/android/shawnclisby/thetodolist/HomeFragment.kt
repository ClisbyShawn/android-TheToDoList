package com.android.shawnclisby.thetodolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.shawnclisby.androidauth.viewModels.AuthViewModel
import com.android.shawnclisby.thetodolist.data.TaskViewModel
import com.android.shawnclisby.thetodolist.data.models.Task
import com.android.shawnclisby.thetodolist.databinding.FragmentHomeBinding
import com.android.shawnclisby.thetodolist.ui.TaskRecyclerAdapter
import com.android.shawnclisby.thetodolist.util.*

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
                            homeViewModel.onSearchToggled.invoke()
                            true
                        }

                        else -> false
                    }
                }

                setNavigationOnClickListener {
                    homeViewModel.onFilterToggled.invoke()
                }
            }

            tieHomeSearch.apply {
                addTextChangedListener { newText ->
                    taskViewModel.searchString.value = newText.toString().trim()
                }

                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        homeViewModel.onSearchToggled.invoke()
                        true
                    }
                    false
                }
            }

            fabHomeNewTask.setOnClickListener {
               //Navigate to Add/Edit Task Fragment
            }

            chipHomeShowHideCompleted.setOnClickListener {
                taskViewModel.toggleFilter()
            }
        }

        homeViewModel.searchBar.observe(viewLifecycleOwner, { searchBar ->
            searchBar.showHide?.let { showHide ->
                if (showHide) applyShowSearchBarAnimation()
                else applyHideSearchBarAnimation()
            }
        })

        homeViewModel.filterContainer.observe(viewLifecycleOwner, { filterContainer ->
            filterContainer.showHide?.let { showHide->
                if (showHide) applyShowFilterAnimation()
                else applyHideFilterAnimation()
            }
        })

        taskViewModel.taskList.observe(viewLifecycleOwner, { tasks ->
            taskAdapter.submitList(tasks)
        })

        taskViewModel.hideCompleted.observe(viewLifecycleOwner,{ hideCompleted->
            setCompletedChipText(hideCompleted)
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

    private fun applyShowFilterAnimation() {
        binding.apply {
            constraintHomeFilterContainer.animateByTranslationYAlphaShow((-54f).toDps(requireContext()))
            bottomAppBar.animateByTranslationYAlphaShow((-53f).toDps(requireContext()))
            viewHomeOverlay.show()
        }
    }

    private fun applyHideFilterAnimation() {
        binding.apply {
            viewHomeOverlay.gone()
            constraintHomeFilterContainer.animateByTranslationYAlphaHide(54f.toDps(requireContext()))
            bottomAppBar.animateByTranslationYAlphaShow(53f.toDps(requireContext()))
        }
    }

    private fun setCompletedChipText(hideCompleted: Boolean) {
        binding.apply {
            chipHomeShowHideCompleted.apply {
                if (hideCompleted){
                    text = getString(R.string.show_completed_text)
                    chipIcon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_completed_box)
                } else {
                    text = getString(R.string.hide_completed_text)
                    chipIcon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_uncompleted_box)
                }
            }
        }

    }
}