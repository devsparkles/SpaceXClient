package com.devsparkle.spacexclient.main

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.devsparkle.spacexclient.R
import com.devsparkle.spacexclient.base.resource.observeResource
import com.devsparkle.spacexclient.databinding.ActivityMainBinding
import com.devsparkle.spacexclient.domain.model.Company
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.main.adapter.LaunchAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    private val TAG: String = "MainActivity"

    private val viewModel by viewModel<MainViewModel>()


    private val launchAdapter by inject<LaunchAdapter> {
        parametersOf(
            { launch: Launch -> onLaunchSelected(launch) },
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this


        setUpLaunchRecyclerView()
        setUpResourceObserver()

        setUpCompany()
        setUpLaunches()
    }


    private fun setUpLaunchRecyclerView() = with(binding.launchRecyclerView) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        setHasFixedSize(true)
        this.layoutManager = layoutManager
        this.adapter = this@MainActivity.launchAdapter
    }

    private fun onLaunchSelected(launch: Launch) {
        Toast.makeText(binding.root.context, "Launch ${launch.missionName}", Toast.LENGTH_SHORT)
            .show()
    }

    private fun setUpResourceObserver() {
        viewModel.companyDetails.observeResource(
            this@MainActivity,
            loading = ::onCompanyLoading,
            success = ::onCompanyReceived,
            error = ::onCompanyError,
            successWithoutContent = {}
        )
        viewModel.launches.observe(
            this@MainActivity
        ) {
            onLaunchesReceived(it)
        }
    }

    private fun setUpCompany() {
        viewModel.getCompanyDetail()
    }

    private fun setUpLaunches() {
        viewModel.getAllLaunches()
    }

    private fun onCompanyLoading() {
        showMessage("company loading")
    }

    private fun onCompanyError(exception: Exception) {
        showMessage("error company loading")
        Log.d(TAG, exception.message ?: "")
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

    private fun onLaunchesReceived(lauches: List<Launch>?) {
        lauches?.let {
            launchAdapter.updateLaunches(lauches)
        } ?: run {
            showMessage("no launches to show")
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}