package com.melvin.ongandroid.uitel

import android.app.Activity
import android.app.AlertDialog
import com.melvin.ongandroid.R

class LoadingDialog
    (val mActivity: Activity)
{
        private lateinit var isdialog: AlertDialog

        fun startLoading() {

//  Set View
            val inflater = mActivity.layoutInflater
            val dialogView = inflater.inflate(R.layout.loading_item, null)

//  Set Dialog
            val builder = AlertDialog.Builder(mActivity)
            builder.setView(dialogView)
            builder.setCancelable(false)
            isdialog = builder.create()
            isdialog.show()
        }

        fun isDismiss() {
            isdialog.dismiss()
        }
    }
