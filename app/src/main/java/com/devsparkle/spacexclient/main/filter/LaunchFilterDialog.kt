package com.devsparkle.spacexclient.main.filter


import android.app.Activity
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.devsparkle.spacexclient.R
import com.google.android.material.chip.Chip
import com.google.android.material.radiobutton.MaterialRadioButton


class LaunchFilterDialog : DialogFragment() {


    interface LaunchFilterDialogListener {
        fun onFilterByYear()
        fun onFilterBySuccess()
        fun onSortByDESC()
        fun onSortByASC()
    }

    companion object {
        val TAG: String? = LaunchFilterDialog::class.qualifiedName
        fun show(activity: AppCompatActivity, value: Int, range: IntRange) {
            LaunchFilterDialog().apply {
            }.show(activity.supportFragmentManager, TAG)
        }
    }

    private var listener: LaunchFilterDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.devsparkle.spacexclient.R.layout.filter_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_in_right)

        setUpButton(view)

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