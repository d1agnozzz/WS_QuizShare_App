package com.ws.worldcinema

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.view.ContextThemeWrapper

object DialogManager {
    fun showErrorDialog(context: Context?, title: String?, message: String?) {
        AlertDialog.Builder(ContextThemeWrapper(context, R.style.DarkDialogStyle))
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("Ok") { dialog: DialogInterface?, which: Int -> }.show()
    }
}