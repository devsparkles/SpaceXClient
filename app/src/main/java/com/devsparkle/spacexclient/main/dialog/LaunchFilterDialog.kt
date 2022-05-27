package com.devsparkle.spacexclient.main.dialog


import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.devsparkle.spacexclient.data.launch.filter.LaunchParameters
import com.devsparkle.spacexclient.data.launch.filter.Order
import com.google.android.material.radiobutton.MaterialRadioButton


class LaunchFilterDialog(
    val activityLaunch: LaunchFilterDialogListener,
    val params: LaunchParameters
) : DialogFragment() {

    val TAG = "launch_filter_dialog"

    interface LaunchFilterDialogListener {
        fun onLaunchFilterSave(   params: LaunchParameters)
        fun onLaunchFilterDismiss()
    }

    lateinit var toolbar: Toolbar


    companion object {
        val TAG: String? = LaunchFilterDialog::class.qualifiedName
    }

    private var listener: LaunchFilterDialogListener? = null


    fun display(fragmentManager: FragmentManager): LaunchFilterDialog {
        this.show(fragmentManager, TAG)
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.devsparkle.spacexclient.R.style.AppTheme_FullScreenDialog)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        activityLaunch.onLaunchFilterDismiss()
    }


    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
            dialog.window!!.setWindowAnimations(com.devsparkle.spacexclient.R.style.AppTheme_Slide)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            inflater.inflate(com.devsparkle.spacexclient.R.layout.filter_dialog, container, false)
        toolbar = view.findViewById(com.devsparkle.spacexclient.R.id.toolbar)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar(view)
        setupUI(view)
        setActionUI(view)
        setPreselectUI(view)
    }

    private fun setupToolbar(view: View) {
        toolbar.setNavigationOnClickListener { v: View? -> dismiss() }
        toolbar.title = getString(com.devsparkle.spacexclient.R.string.filter_and_sort)
        toolbar.inflateMenu(com.devsparkle.spacexclient.R.menu.filter_dialog)
        toolbar.setOnMenuItemClickListener { item: MenuItem? ->
            item?.let {
                if (item.itemId == com.devsparkle.spacexclient.R.id.action_save) onSave(
                    view
                )
            }
            true
        }
    }

    private fun onSave(view: View) {
        // save params
        if (view.findViewById<MaterialRadioButton>(com.devsparkle.spacexclient.R.id.radio_asc).isChecked) {
            params.order = Order.ASC.name
        }

        if (view.findViewById<MaterialRadioButton>(com.devsparkle.spacexclient.R.id.radio_desc).isChecked) {
            params.order = Order.DESC.name
        }

        activityLaunch.onLaunchFilterSave(params)

        dismiss()
    }

    /*
    set Ui elements and preselect their value with Params value
     */
    private fun setupUI(view: View) {

        // Set years
        val yearAutocomplete: AutoCompleteTextView =
            view.findViewById(com.devsparkle.spacexclient.R.id.yearTextView)
        val yearList = getYearList()
        this.context?.let {
            val adapter: ArrayAdapter<String?> = ArrayAdapter<String?>(
                it,
                android.R.layout.simple_spinner_item,
                yearList
            )
            yearAutocomplete.setAdapter(adapter)
        }


    }

    private fun setActionUI(view: View) {

        view.findViewById<MaterialRadioButton>(com.devsparkle.spacexclient.R.id.radio_asc)
            .setOnClickListener {
                params.order = Order.ASC.name
            }
        view.findViewById<MaterialRadioButton>(com.devsparkle.spacexclient.R.id.radio_desc)
            .setOnClickListener {
                params.order = Order.DESC.name
            }

        val yearAutocomplete: AutoCompleteTextView =
            view.findViewById(com.devsparkle.spacexclient.R.id.yearTextView)

        yearAutocomplete.onItemClickListener = OnItemClickListener { parent, arg1, position, arg3 ->
            val item = parent.getItemAtPosition(position)
            if (item is String) {
                params.launchYear = item
            }
        }

    }

    private fun setPreselectUI(view: View) {
        // pre selection years.
        if (params.launchYear != null) {
            val position: Int = getYearList().indexOf(params.launchYear.toString())
            val yearAutocomplete: AutoCompleteTextView =
                view.findViewById(com.devsparkle.spacexclient.R.id.yearTextView)
            yearAutocomplete.setText(yearAutocomplete.adapter.getItem(position).toString(), false)
        }

        // preselection asc or desc
        view.findViewById<MaterialRadioButton>(com.devsparkle.spacexclient.R.id.radio_asc).isChecked =
            params.order.equals(Order.ASC.name)

        view.findViewById<MaterialRadioButton>(com.devsparkle.spacexclient.R.id.radio_desc).isChecked =
            params.order.equals(Order.DESC.name)
    }


    private fun getYearList(): ArrayList<String?> {
        val years: ArrayList<String?> = ArrayList()
        years.add("2022")
        years.add("2021")
        years.add("2020")
        years.add("2019")
        years.add("2018")
        years.add("2017")
        years.add("2016")
        years.add("2015")
        return years
    }


    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (activity is LaunchFilterDialog.LaunchFilterDialogListener) {
            listener = activity
        }
    }

}