package com.example.happy.common.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

object SingleToast {

    private var toast: Toast? = null

    fun showToast(context: Context, message: String?, duration: Int = Toast.LENGTH_SHORT) {
        if (message == null) return

        toast?.cancel()

        toast = Toast.makeText(context, message, duration)
        toast?.show()
    }

    fun showToast(context: Context, @StringRes message: Int?, duration: Int = Toast.LENGTH_SHORT) {
        if (message == null) return

        toast?.cancel()

        toast = Toast.makeText(context, message, duration)
        toast?.show()
    }

}

fun Context.showToast(message: String?, duration: Int = Toast.LENGTH_SHORT): Unit =
    SingleToast.showToast(this, message, duration)