package com.daniel.codetest.utils

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import com.daniel.codetest.BuildConfig
import com.daniel.codetest.R
import com.daniel.codetest.databinding.MessageDialogBinding
import com.daniel.codetest.enums.Environment


object CommonUtils {

    /** Globally accessible build environment */
    val environment = when (BuildConfig.BUILD_TYPE) {
        "release" -> Environment.PRODUCTION
        "qa" -> Environment.QA
        else -> Environment.DEVELOPMENT
    }

    /**
     * It is used for check the device's internet connectivity
     */
    fun isNetworkConnected(context: Context): Boolean {
        val result: Boolean
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return result
    }

    /**
     *  This function used to show waiting spinner
     *
     * @param context activity or fragment context
     */
    fun showSpinner(context: Context): Dialog {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.spinner)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
        return dialog
    }

    /**
     *  This function used to show waiting spinner
     *
     * @param context activity or fragment context
     */
    fun showError(context: Context, msg: String) {
        val dialog = Dialog(context)
        val binding: MessageDialogBinding = MessageDialogBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        binding.textMessage.text = msg
        binding.OkMessage.setOnClickListener {
            dialog.hide()
        }

        //now that the dialog is set up, it's time to show it
        dialog.show()
    }
}
