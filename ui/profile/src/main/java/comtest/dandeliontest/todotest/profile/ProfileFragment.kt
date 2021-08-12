package comtest.dandeliontest.todotest.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType.Spline
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAMarker
import com.github.aachartmodel.aainfographics.aatools.AAColor
import comtest.dandeliontest.todotest.model.models.task.ChartItem
import comtest.dandeliontest.todotest.profile.databinding.FragmentProfileBinding
import comtest.dandeliontest.todotest.ui.snackbar.bindToSnackBarManager
import comtest.dandeliontest.todotest.ui.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel>()

    private var aaChartModel = AAChartModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(
            inflater, container, false
        ).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindToSnackBarManager(viewModel.snackbarManager)

        viewModel.navigateAllCategoriesManagement.observe(viewLifecycleOwner, EventObserver {

        })

        viewModel.chartItems.observe(viewLifecycleOwner) {
            initChart(it)
        }
    }

    private fun initChart(chartItemList: List<ChartItem>) {
        val allTasks = mutableListOf<Int>()
        val completedTasks = mutableListOf<Int>()
        val weekDaysList = mutableListOf<String>()
        val seriesElement = mutableListOf<AASeriesElement>()

        chartItemList.asReversed().forEach {
            allTasks.add(it.allTasks)
            completedTasks.add(it.isDoneTasks)
            val string = getString(it.titleRes)
            weekDaysList.add(string)
        }

        seriesElement.add(
            AASeriesElement()
                .name(requireContext().getString(R.string.allTasks))
                .color(AAColor.rgbaColor(32, 99, 155))
                .marker(AAMarker())
                .data(allTasks.toTypedArray())
        )
        seriesElement.add(
            AASeriesElement()
                .name(requireContext().getString(R.string.completed))
                .color(AAColor.rgbaColor(60, 174, 163))
                .marker(AAMarker())
                .data(completedTasks.toTypedArray())
        )

        aaChartModel = AAChartModel()
            .chartType(Spline)
            .animationType(AAChartAnimationType.SwingTo)
            .dataLabelsEnabled(false)
            .yAxisGridLineWidth(1f)
            .yAxisLineWidth(1f)
            .yAxisTitle("")
            .markerSymbol(AAChartSymbolType.Circle)
            .yAxisAllowDecimals(false)
            .touchEventEnabled(true)
            .legendEnabled(true)
            .markerSymbolStyle(AAChartSymbolStyleType.BorderBlank)
            .markerRadius(6f)
            .categories(*weekDaysList.toTypedArray())
            .series(seriesElement.toTypedArray())

        val aaOptions = aaChartModel.aa_toAAOptions()

        aaOptions.tooltip!!
            .valueDecimals(0)

        binding.lineChart.aa_drawChartWithChartModel(aaChartModel)
        binding.lineChart.aa_drawChartWithChartOptions(aaOptions)
    }
}