package com.speaking.partner.task.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.speaking.partner.shared.AppConstants
import com.speaking.partner.task.R
import com.speaking.partner.task.databinding.FragmentSettingsBinding
import com.speaking.partner.ui.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val viewModel by viewModels<SettingsViewModel>()
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(
            inflater, container, false
        ).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        checkVersionCode()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onBackPressed.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigateUp()
        })

        viewModel.onPrivacyClick.observe(viewLifecycleOwner, EventObserver {
            openInBrowser(AppConstants.PRIVACY_POLICY_LINK)
        })

        viewModel.onFeedBackClick.observe(viewLifecycleOwner, EventObserver {
            sendEmail()
        })

        viewModel.onRateUsClick.observe(viewLifecycleOwner, EventObserver {
            rateOurApp()
        })
    }

    private fun openInBrowser(url: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent = builder.build();
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }

    private fun rateOurApp() {
        val uri: Uri = Uri.parse("market://details?id=" + requireContext().packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)

        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            openInBrowser("http://play.google.com/store/apps/details?id=" + requireContext().packageName)
        }
    }

    private fun sendEmail() {
        val mailto = "mailto:" + AppConstants.FEEDBACK_EMAIL_ADDRESS +
                "?cc=" + "" +
                "&subject=" + Uri.encode(getString(R.string.feedback_email_subject)) +
                "&body=" + Uri.encode("")
        val i = Intent(Intent.ACTION_SENDTO)
        i.data = Uri.parse(mailto)
        try {
            startActivity(i)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                context,
                getString(R.string.no_email_error),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun checkVersionCode() {
        val pInfo = requireActivity().packageManager.getPackageInfo(
            requireActivity().packageName, 0
        )
        val version = pInfo.versionName
        viewModel.onSetVersionCode(version)
    }
}