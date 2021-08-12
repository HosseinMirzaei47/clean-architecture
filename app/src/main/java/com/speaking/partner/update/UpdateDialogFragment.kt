package com.speaking.partner.update

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.speaking.partner.R.string
import com.speaking.partner.databinding.FragmentUpdateDialogBinding
import com.speaking.partner.ui.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentUpdateDialogBinding
    private val viewModel by viewModels<UpdateViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateDialogBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onIgnoreClicked.observe(
            viewLifecycleOwner,
            EventObserver { findNavController().navigateUp() })

        viewModel.onUpdateClicked.observe(viewLifecycleOwner, EventObserver { openAppInMarket() })

        viewModel.isCancelable.observe(viewLifecycleOwner) {
            isCancelable = it
        }
    }

    private fun openAppInMarket() {
        try {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context?.packageName))
            )
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), getString(string.no_market_found), Toast.LENGTH_SHORT)
                .show()
        }
    }
}