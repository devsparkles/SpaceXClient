package com.devsparkle.spacexclient.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
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

    private lateinit var binding: ActivityMainBinding

    private val TAG: String = "MainACtivityu"

    private val viewModel by viewModel<MainViewModel>()


    private val launchAdapter by inject<LaunchAdapter> {
        parametersOf(
            { launch: Launch -> onLaunchSelected(launch) },
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.activity_main)

        setUpLaunchRecyclerView()
        setUpResourceObserver()
        setUpCompany()
        setUpLaunches()

    }


    private fun setUpLaunchRecyclerView() = with(binding.launchRecyclerView) {
        val layoutManager = GridLayoutManager(context, 2)

        setHasFixedSize(true)
        this.layoutManager = layoutManager
        this.adapter = this@MainActivity.launchAdapter
    }

    private fun onLaunchSelected(launch: Launch) {
        Toast.makeText(binding.root.context, "Launch ${launch.missionName}", Toast.LENGTH_SHORT)
    }

    private fun setUpResourceObserver() {
        viewModel.companyDetails.observeResource(
            this@MainActivity, success = ::onCompanyReceived
        )

    }

    private fun setUpCompany() {
        viewModel.getCompanyDetail()
    }

    private fun setUpLaunches() {

    }

    private fun onCompanyReceived(company: Company?) {

        company?.let {

            binding.clCompany.visibility = View.VISIBLE
            binding.tvCompanyDescription.text = getString(
                R.string.company_description,
                company.companyName,
                company.founderName,
                company.year,
                company.employees,
                company.launchSites,
                company.valuation
            )
        } ?: run {
            // no company loaded
            // hide company block
            binding.clCompany.visibility = View.GONE
        }


    }

}