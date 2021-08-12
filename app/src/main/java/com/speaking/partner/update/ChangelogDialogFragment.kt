package com.speaking.partner.update

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.speaking.partner.databinding.FragmentChangelogDialogBinding
import com.speaking.partner.ui.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangelogDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentChangelogDialogBinding
    private val viewModel by viewModels<ChangelogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangelogDialogBinding.inflate(
            inflater, container, false
        ).apply {
            vm = viewModel
            isCancelable = true
            lifecycleOwner = viewLifecycleOwner
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onConfirmedClick.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigateUp()
        })
    }
}