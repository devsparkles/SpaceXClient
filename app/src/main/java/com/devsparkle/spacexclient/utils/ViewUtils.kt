package com.devsparkle.spacexclient.utils

import android.view.View
import android.widget.ProgressBar

object ViewUtils {
    fun showViewHideProgressBar(view: View, progressBar: ProgressBar) {
        view.show()
        progressBar.hide()
    }

    fun hideViewShowProgressBar(view: View, progressBar: ProgressBar) {
        view.hide()
        progressBar.show()
    }
}

