package com.devsparkle.spacexclient.utils

import android.view.View
import android.widget.ProgressBar

object ViewUtils {
    fun showViewHideProgressBar(view: View, progressBar: ProgressBar) {
        view.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    fun hideViewShowProgressBar(view: View, progressBar: ProgressBar) {
        view.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }
}

