package com.devsparkle.spacexclient.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devsparkle.spacexclient.R
import com.devsparkle.spacexclient.base.resource.observeResource
import com.devsparkle.spacexclient.databinding.ActivityMainBinding
import com.devsparkle.spacexclient.domain.model.Company
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.main.adapter.LaunchAdapter
import com.devsparkle.spacexclient.main.filter.LaunchFilterDialog
import com.devsparkle.spacexclient.utils.EndlessRecyclerViewScrollListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"

    private var isFirstLaunch = true
    lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<MainViewModel>()
    private val adapter by inject<LaunchAdapter> {
        parametersOf(
            { launch: Launch -> onLaunchSelected(launch) },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setUpButton()
        setupSwipeToRefresh()
        setUpRecyclerView()
        setUpResourceObserver()
        onFirstLaunch()
    }

    private fun onFirstLaunch() {
        if (isFirstLaunch) {
            viewModel.getCompany()
            viewModel.getLaunches()
            isFirstLaunch = false
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeToRefresh.setOnRefreshListener {
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
    private fun onCompanyLoading() {
        showMessage("company loading")
    }

    private fun onCompanyError(e: Exception) {
        showMessage("error company loading")
        Timber.d(TAG, e.message ?: "")
    }

    private fun onCompanyReceived(company: Company?) {
        company?.let {
            binding.tvCompanyDescription.text = getString(
                R.string.company_description,
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
    private fun onLaunchLoading() {
        showMessage("launch loading")
    }

    private fun onLaunchError(e: Exception) {
        showMessage("error launch loading")
        Timber.d(TAG, e.message ?: "")
    }

    private fun onLaunchSuccess(launches: List<Launch>?) {
        launches?.let {
            adapter.updateLaunches(launches)
        } ?: run {
            showMessage("no launches to show")
        }
    }


    //region filterdialog
    private fun setUpButton() = with(binding) {
        filterImage.setOnClickListener {
            showFilterDialog()
        }
    }

    private fun showFilterDialog() {
        LaunchFilterDialog().show(supportFragmentManager, "fragment_filter_dialog")
    }
    //endregion

    private fun setUpRecyclerView() = with(binding.launchRecyclerView) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

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
        binding.launchRecyclerView.addOnScrollListener(scrollListener)
        setHasFixedSize(true)
        this.layoutManager = layoutManager
        this.adapter = this@MainActivity.adapter
    }


    private fun onLaunchSelected(launch: Launch) {
        Toast.makeText(binding.root.context, "Launch ${launch.missionName}", Toast.LENGTH_SHORT)
            .show()
    }
    //endregion

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}