package com.android.shawnclisby.thetodolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.shawnclisby.androidauth.viewModels.AuthViewModel
import com.android.shawnclisby.thetodolist.data.TaskViewModel
import com.android.shawnclisby.thetodolist.data.TaskViewModelFactory
import com.android.shawnclisby.thetodolist.data.models.OrderBy
import com.android.shawnclisby.thetodolist.data.models.SortOrder
import com.android.shawnclisby.thetodolist.data.models.Task
import com.android.shawnclisby.thetodolist.databinding.FragmentHomeBinding
import com.android.shawnclisby.thetodolist.ui.HomeViewModel
import com.android.shawnclisby.thetodolist.ui.TaskRecyclerAdapter
import com.android.shawnclisby.thetodolist.util.*

class HomeFragment : Fragment(), TaskRecyclerAdapter.TaskInteraction {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var authViewModel: AuthViewModel
    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(
            requireActivity().application
        )
    }
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
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
                    } else false
                }
            }

            fabHomeNewTask.setOnClickListener {
                navigateToTaskDetail()
            }

            chipHomeShowHideCompleted.setOnClickListener {
                taskViewModel.toggleFilter()
            }

            chipHomeTitle.setOnClickListener {
                taskViewModel.toggleTitleOrder()
            }

            chipHomeDate.setOnClickListener {
                taskViewModel.toggleDateOrder()
            }
        }

        homeViewModel.searchBar.observe(viewLifecycleOwner, { searchBar ->
            searchBar.showHide?.let { showHide ->
                if (showHide) applyShowSearchBarAnimation()
                else applyHideSearchBarAnimation()
            }
        })

        homeViewModel.filterContainer.observe(viewLifecycleOwner, { filterContainer ->
            filterContainer.showHide?.let { showHide ->
                if (showHide) applyShowFilterAnimation()
                else applyHideFilterAnimation()
            }
        })

        taskViewModel.taskList.observe(viewLifecycleOwner, { tasks ->
            homeViewModel.isListEmpty(tasks.size)
            taskAdapter.submitList(tasks)
        })

        taskViewModel.hideCompleted.observe(viewLifecycleOwner, { hideCompleted ->
            setCompletedChipText(hideCompleted)
        })

        taskViewModel.sortOrder.observe(viewLifecycleOwner, { sortOrder ->
            setChipIcons(sortOrder)
        })

        homeViewModel.emptyList.observe(viewLifecycleOwner, { listIsEmpty ->
            if (listIsEmpty) binding.tvHomeEmptyText.show()
            else binding.tvHomeEmptyText.gone()
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTaskItemClicked(task: Task) {
        navigateToTaskDetail(task)
    }

    override fun onTaskCompletionChanged(task: Task) {
        taskViewModel.update(task)
    }

    private fun navigateToTaskDetail(task: Task? = null) {
        taskViewModel.taskDetail(task)
        findNavController().navigate(R.id.action_homeFragment_to_addEditFragment)
    }

    /* region View and Animation Logic */

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
            constraintHomeFilterContainer.lowBounceStiffnessTranslationY(0f.toDps(requireContext()))
            bottomAppBar.lowBounceStiffnessTranslationY((-56f).toDps(requireContext()))
            viewHomeOverlay.show()
        }
    }

    private fun applyHideFilterAnimation() {
        binding.apply {
            viewHomeOverlay.gone()
            constraintHomeFilterContainer.lowBounceStiffnessTranslationY(56f.toDps(requireContext()))
            bottomAppBar.lowBounceStiffnessTranslationY(0f.toDps(requireContext()))
        }
    }

    private fun setCompletedChipText(hideCompleted: Boolean) {
        binding.apply {
            chipHomeShowHideCompleted.apply {
                if (hideCompleted) {
                    text = getString(R.string.show_completed_text)
                    chipIcon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_completed_box)
                } else {
                    text = getString(R.string.hide_completed_text)
                    chipIcon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_uncompleted_box)
                }
            }
        }
        binding.rvHomeTaskList.scrollToPosition(0)
    }

    private fun setChipIcons(sortOrder: SortOrder) {
        when (sortOrder) {
            is SortOrder.DateOrder -> setDateChip(sortOrder.orderBy)
            is SortOrder.TitleOrder -> setTitleChip(sortOrder.orderBy)
        }
        binding.rvHomeTaskList.scrollToPosition(0)
    }

    private fun setTitleChip(orderBy: OrderBy) {
        binding.apply {
            chipHomeDate.chipIcon = null
            if (orderBy == OrderBy.ASC)
                chipHomeTitle.chipIcon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_ascending_arrow)
            else chipHomeTitle.chipIcon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_descending_arrow)
        }
    }

    private fun setDateChip(orderBy: OrderBy) {
        binding.apply {
            chipHomeTitle.chipIcon = null
            if (orderBy == OrderBy.ASC)
                chipHomeDate.chipIcon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_ascending_arrow)
            else chipHomeDate.chipIcon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_descending_arrow)
        }
    }

    /* endregion View and Animation Logic */
}