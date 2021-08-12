package comtest.dandeliontest.todotest

import android.R.anim
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import comtest.dandeliontest.todotest.databinding.ActivityMainBinding
import comtest.dandeliontest.todotest.model.models.task.StateConstants
import comtest.dandeliontest.todotest.notification.SystemBootBroadcastReceiver
import comtest.dandeliontest.todotest.ui.utils.DeepLinkConstants
import comtest.dandeliontest.todotest.ui.utils.EventObserver
import comtest.dandeliontest.todotest.ui.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {
            lifecycleOwner = this@MainActivity
            vm = viewModel
        }

        navController = findNavController(R.id.nav_host_container)

        registerDeviceRebootReceiver()

        viewModel.languageChanged.observe(this, EventObserver { restartActivity() })


        viewModel.navigateToSetting.observe(this, EventObserver {
            findNavController(this, R.id.nav_host_container).safeNavigate(
                (DeepLinkConstants.DEEPLINK_BASE_URI + DeepLinkConstants.DEEPLINK_SETTING_URI).toUri()
            )
        })

        viewModel.updateStatus.observe(this) {
            when (it) {
                MainViewModel.VERSION_CONTROL_TYPE_FORCE -> {
                    findNavController(this, R.id.nav_host_container).safeNavigate(
                        (DeepLinkConstants.DEEPLINK_BASE_URI + DeepLinkConstants.DEEPLINK_UPDATE_URI + DeepLinkConstants.DEEPLINK_UPDATE_ARGUMENT_IS_FORCE).toUri()
                    )
                }
                MainViewModel.VERSION_CONTROL_TYPE_DIALOG -> {
                    findNavController(this, R.id.nav_host_container).safeNavigate(
                        (DeepLinkConstants.DEEPLINK_BASE_URI + DeepLinkConstants.DEEPLINK_UPDATE_URI).toUri()
                    )
                }
                MainViewModel.VERSION_CONTROL_TYPE_CHANGELOG -> {
                    findNavController(this, R.id.nav_host_container).safeNavigate(
                        (DeepLinkConstants.DEEPLINK_BASE_URI + DeepLinkConstants.DEEPLINK_CHANGELOG_URI).toUri()
                    )
                }
            }
        }

        viewModel.currentDestination.observe(this, EventObserver {
            val options = navOptions {
                anim {
                    enter = R.anim.slide_in_from_right
                    exit = R.anim.slide_out_to_left
                    popEnter = R.anim.slide_in_from_left
                    popExit = R.anim.slide_out_to_right
                }
            }
            if (it.latestDestinationId.toInt() != StateConstants.NOT_SET) {
                findNavController(this, R.id.nav_host_container).navigate(
                    it.latestDestinationId.toInt(),
                    bundleOf("categoryId" to it.categoryId),
                    options
                )

            }
        })
    }

    private fun restartActivity() {
        val bundle = Bundle()
        onSaveInstanceState(bundle)
        val intent = Intent(this, javaClass)
        intent.putExtra("saved_state", bundle)
        intent.putExtra("from_setting", true)
        finish()
        startActivity(intent)
        overridePendingTransition(anim.fade_in, anim.fade_out)
    }

    private fun registerDeviceRebootReceiver() {
        val mReceiver = SystemBootBroadcastReceiver()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_BOOT_COMPLETED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            filter.addAction(Intent.ACTION_LOCKED_BOOT_COMPLETED)
        }
        registerReceiver(mReceiver, filter)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun attachBaseContext(newBase: Context?) {
        newBase?.let {
            super.attachBaseContext(getSettingHelper(newBase).getModifiedContext())
        }
    }

    private fun getSettingHelper(newBase: Context): SettingsHelper {
        return EntryPointAccessors.fromApplication(
            newBase,
            SettingHelperEntryPoint::class.java
        ).getSettingHelperEntryPoint()
    }
}
