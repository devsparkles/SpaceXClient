package com.devsparkle.spacexclient.main.dialog


import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.devsparkle.spacexclient.R
import com.google.android.material.radiobutton.MaterialRadioButton


class LaunchFilterDialog : DialogFragment() {

    val TAG = "launch_filter_dialog"

    interface LaunchFilterDialogListener {
        fun onFilterByYear()
        fun onFilterBySuccess()
        fun onSortByDESC()
        fun onSortByASC()
    }

    lateinit var toolbar: Toolbar


    companion object {
        val TAG: String? = LaunchFilterDialog::class.qualifiedName
        fun show(activity: AppCompatActivity, value: Int, range: IntRange) {
            LaunchFilterDialog().apply {
            }.show(activity.supportFragmentManager, TAG)
        }
    }

    private var listener: LaunchFilterDialogListener? = null


    fun display(fragmentManager: FragmentManager): LaunchFilterDialog {
        val exampleDialog: LaunchFilterDialog =
            LaunchFilterDialog()
        exampleDialog.show(fragmentManager, TAG)
        return exampleDialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }


    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
            dialog.window!!.setWindowAnimations(R.style.AppTheme_Slide)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.filter_dialog, container, false)
        toolbar = view.findViewById(R.id.toolbar)

        return view
    }

    fun redraw() {
        view?.invalidate()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpButton(view)
        setupToolbar()
    }

    private fun setupToolbar(){
        toolbar.setNavigationOnClickListener { v: View? -> dismiss() }
        toolbar.title = getString(R.string.filter_and_sort)
        toolbar.inflateMenu(R.menu.filter_dialog)
        toolbar.setOnMenuItemClickListener { item: MenuItem? ->
            item?.let {
                if (item.itemId == R.id.action_save) saveParamsAndDismiss()
            }
            true
        }
    }

    private fun saveParamsAndDismiss(){
        // save params
        dismiss()
    }

    private fun setUpButton(view: View) {
        view.findViewById<MaterialRadioButton>(R.id.radio_asc).setOnClickListener {

        }
        view.findViewById<MaterialRadioButton>(R.id.radio_desc).setOnClickListener {

        }

    }

    fun setupSortBy(view: View, sortBy: String) {
        when (sortBy) {
            "year,desc" -> view.findViewById<RadioButton>(R.id.radio_desc).isChecked = true
            "year,asc" -> view.findViewById<RadioButton>(R.id.radio_asc).isChecked = true

        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (activity is LaunchFilterDialog.LaunchFilterDialogListener) {
            listener = activity
        }
    }

}