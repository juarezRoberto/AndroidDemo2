package com.juarez.upaxdemo.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.juarez.upaxdemo.common.Constants

fun ImageView.loadPosterImage(posterPath: String) {
    Glide
        .with(this.context)
        .load("${Constants.IMG_BASE_URL}$posterPath")
        .into(this)
}

fun ImageView.loadFirebaseImage(url: String) {
    Glide
        .with(this.context)
        .load(url)
        .into(this)
}

fun Fragment.toast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), text, length).show()
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.isLocationEnabled(): Boolean {
    val locationManager =
        requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return LocationManagerCompat.isLocationEnabled(locationManager)
}

enum class PermissionResult {
    GRANTED, DENIED, RATIONALE
}

fun Fragment.requestPermission(
    permission: String,
    onResult: (result: PermissionResult) -> Unit,
) {
    when {
        ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED -> onResult(PermissionResult.GRANTED)
        shouldShowRequestPermissionRationale(permission) -> onResult(PermissionResult.RATIONALE)
        else -> onResult(PermissionResult.DENIED)
    }
}

enum class AlertAction {
    POSITIVE, NEGATIVE
}

fun Fragment.showRequestPermissionRationaleAlert(
    message: String,
    onSelect: (action: AlertAction) -> Unit,
) {
    val builder = AlertDialog.Builder(requireContext())
    builder.setTitle(Constants.ALERT_TITLE)
    builder.setMessage(message)
    builder.setPositiveButton(Constants.ALERT_OK) { dialog, _ ->
        dialog.dismiss()
        onSelect(AlertAction.POSITIVE)
    }
    builder.setNegativeButton(Constants.ALERT_CANCEL) { dialog, _ ->
        dialog.dismiss()
        onSelect(AlertAction.NEGATIVE)
    }
    builder.show()
}

fun Fragment.showAlert(
    title: String = Constants.ALERT_TITLE,
    message: String,
    onSelect: (action: AlertAction) -> Unit = {},
) {
    val builder = AlertDialog.Builder(requireContext())
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton(Constants.ALERT_OK) { dialog, _ ->
        dialog.dismiss()
        onSelect(AlertAction.POSITIVE)
    }
    builder.setNegativeButton(Constants.ALERT_CANCEL) { dialog, _ ->
        dialog.dismiss()
        onSelect(AlertAction.NEGATIVE)
    }
    builder.show()
}