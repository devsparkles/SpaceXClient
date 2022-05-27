package com.devsparkle.spacexclient.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devsparkle.spacexclient.R
import com.devsparkle.spacexclient.base.resource.observeResource
import com.devsparkle.spacexclient.data.launch.filter.LaunchParameters
import com.devsparkle.spacexclient.databinding.ActivityMainBinding
import com.devsparkle.spacexclient.domain.model.Company
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.main.adapter.LaunchAdapter
import com.devsparkle.spacexclient.main.dialog.LaunchFilterDialog
import com.devsparkle.spacexclient.utils.EndlessRecyclerViewScrollListener
import com.devsparkle.spacexclient.utils.ViewUtils.hideViewShowProgressBar
import com.devsparkle.spacexclient.utils.ViewUtils.showViewHideProgressBar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber


class MainActivity : AppCompatActivity(), LaunchFilterDialog.LaunchFilterDialogListener {

    private val TAG: String = "MainActivity"

    private var isFirstLaunch = true
    lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<MainViewModel>()
    private val adapter by inject<LaunchAdapter> {
        parametersOf(
            { launch: Launch -> onLaunchSelected(launch) },
        )
    }

    var filterDialogTapped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, com.devsparkle.spacexclient.R.layout.activity_main)
        binding.lifecycleOwner = this
        setupSwipeToRefresh()
        setUpRecyclerView()
        setUpResourceObserver()
        onFirstLaunch()
        setupToolbar()
    }

    private fun setupToolbar() = with(binding) {
        toolbar.background = ColorDrawable(resources.getColor(R.color.colorPrimary))
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.title = getString(R.string.space_x)
        toolbar.inflateMenu(R.menu.launch)
        toolbar.setOnMenuItemClickListener { item: MenuItem? ->
            item?.let {
                if (item.itemId == R.id.action_filter) showFilterDialog()
            }
            true
        }
    }

    private fun onFirstLaunch() {
        if (isFirstLaunch) {
            viewModel.getCompany()
            viewModel.getLaunches()
            isFirstLaunch = false
        }
    }

    private fun setupSwipeToRefresh() = with(binding) {
        swipeToRefresh.setOnRefreshListener {
            viewModel.getCompany()
            viewModel.getLaunches()
        }
    }

    private fun setUpResourceObserver() {
        viewModel.companyDetails.observeResource(
            this@MainActivity,
            loading = ::onCompanyLoading,
            success = ::onCompanyReceived,
            error = ::onCompanyError,
            successWithoutContent = {}
        )
        viewModel.launchListLiveData.observeResource(
            this@MainActivity,
            loading = ::onLaunchLoading,
            success = ::onLaunchSuccess,
            error = ::onLaunchError,
            successWithoutContent = {}
        )
    }

    //region company
    private fun onCompanyLoading() = with(binding) {
        hideViewShowProgressBar(tvCompanyDescription, progressBarCompany)
    }

    private fun onCompanyError(e: Exception) = with(binding) {
        showViewHideProgressBar(tvCompanyDescription, progressBarCompany)
        showMessage("error company loading")
        Timber.d(TAG, e.message ?: "")
    }

    private fun onCompanyReceived(company: Company?) = with(binding) {
        company?.let {
            showViewHideProgressBar(tvCompanyDescription, progressBarCompany)
            binding.tvCompanyDescription.text = getString(
                com.devsparkle.spacexclient.R.string.company_description,
                it.companyName,
                it.founderName,
                it.year,
                it.employees,
                it.launchSites,
                it.valuation
            )
        } ?: run {
            showMessage("no company to show")
        }
    }
    //endregion

    //region launch
    private fun onLaunchLoading() = with(binding) {
        hideViewShowProgressBar(launchRecyclerView, progressBarLaunch)
    }

    private fun onLaunchError(e: Exception) = with(binding) {
        showViewHideProgressBar(launchRecyclerView, progressBarLaunch)
        showMessage("error launch loading")
        Timber.d(TAG, e.message ?: "")
    }

    private fun onLaunchSuccess(launches: List<Launch>?) = with(binding) {
        binding.swipeToRefresh.isRefreshing = false
        showViewHideProgressBar(launchRecyclerView, progressBarLaunch)
        launches?.let {
            adapter.updateLaunches(launches)
        } ?: run {
            showMessage("no launches to show")
        }
    }

    //region filterdialog
    private fun showFilterDialog() {
        if (!filterDialogTapped) {
            val filterDialog = LaunchFilterDialog( this, viewModel.getLaunchParameters())
            filterDialog.display(supportFragmentManager)
            filterDialogTapped = true
        }
    }

    override fun onLaunchFilterDismiss() {
        filterDialogTapped = false
    }

    override fun onLaunchFilterSave( params: LaunchParameters) {
        // set the new filter parameters
        viewModel.setLaunchParameters(params)
        // get the new launches from the backend
        viewModel.getLaunches()
    }
    //endregion

    private fun setUpRecyclerView() = with(binding) {
        val layoutManager =
            LinearLayoutManager(launchRecyclerView.context, LinearLayoutManager.VERTICAL, false)

        val scrollListener: EndlessRecyclerViewScrollListener =
            object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    val params = viewModel.getLaunchParameters()
                    params.page = page
                    viewModel.setLaunchParameters(params)
                    viewModel.getLaunches()
                }
            }
        scrollListener.resetState()
        launchRecyclerView.addOnScrollListener(scrollListener)
        launchRecyclerView.setHasFixedSize(true)
        launchRecyclerView.layoutManager = layoutManager
        launchRecyclerView.adapter = this@MainActivity.adapter
    }


    private fun onLaunchSelected(launch: Launch) = with(binding) {
        Toast.makeText(root.context, "Launch ${launch.missionName}", Toast.LENGTH_SHORT)
            .show()
    }
    //endregion

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}