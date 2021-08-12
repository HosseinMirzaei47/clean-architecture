package com.speaking.partner.task.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.speaking.partner.task.databinding.FragmentHomeBinding
import com.speaking.partner.ui.snackbar.bindToSnackBarManager
import com.speaking.partner.ui.utils.EventObserver
import com.speaking.partner.ui.utils.autoCleared
import com.speaking.partner.ui.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    val viewModel: HomeViewModel by viewModels()
    private var allCategoriesController by autoCleared<HomeCategoriesController>()
    private var binding by autoCleared<FragmentHomeBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }

        allCategoriesController = HomeCategoriesController(
            onClick = {
                viewModel.onCategoriesClicked(it as Long)
            }
        )
        binding.categoriesRecyclerview.setController(allCategoriesController)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindToSnackBarManager(viewModel.snackbarManager)

        viewModel.allCategories.observe(viewLifecycleOwner, {
            allCategoriesController.setData(it)
        })

        viewModel.onOpenSettingsClicked.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
            )
        })

        viewModel.onCategoriesClick.observe(viewLifecycleOwner, EventObserver {
            if (it == 0L) {
                findNavController().safeNavigate(
                    HomeFragmentDirections.actionHomeFragmentToCategoryFragment(
                        it
                    )
                )
            } else {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToTasksFragment(
                        it
                    )
                )
            }

        })


        /*    private fun onDeleteTagClick(tagId: Long) {
            MaterialAlertDialogBuilder(requireContext(), R.style.DeleteTaskDialog)
                .setTitle(resources.getString(R.string.delete_category_dialog_title))
                .setMessage(resources.getString(R.string.delete_category_dialog_message))
                .setNegativeButton(resources.getString(R.string.cancel)) { _, _ -> }
                .setPositiveButton(resources.getString(R.string.delete)) { _, _ ->
                    viewModel.deleteTag(tagId)
                }.show()
        }*/

    }
}