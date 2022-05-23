package com.devsparkle.spacexclient.main

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.devsparkle.spacexclient.R
import com.devsparkle.spacexclient.base.resource.observeResource
import com.devsparkle.spacexclient.databinding.ActivityMainBinding
import com.devsparkle.spacexclient.domain.model.Company
import com.devsparkle.spacexclient.domain.model.Launch
import com.devsparkle.spacexclient.main.adapter.LaunchAdapter
import com.devsparkle.spacexclient.main.filter.LaunchFilterDialog
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {


    private val TAG: String = "MainActivity"
    lateinit var binding: ActivityMainBinding
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
        setFilterIcon()
        setUpButton()
    }

    private fun setUpButton() = with(binding) {
        filterImage.setOnClickListener {
            showEditDialog()
        }
    }

    fun setFilterIcon() {
        if (viewModel.isFilterApplied()) {
            val newColor = ContextCompat.getColor(this, R.color.space_x_green_dark)
            binding.filterImage.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP)
        } else {
            binding.filterImage.clearColorFilter()
        }
    }


    private fun showEditDialog() {
        val fm: FragmentManager = supportFragmentManager
        val editNameDialogFragment =
            LaunchFilterDialog()
        editNameDialogFragment.show(fm, "fragment_filter_dialog")
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