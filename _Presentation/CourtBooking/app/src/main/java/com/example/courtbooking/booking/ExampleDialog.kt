package com.example.courtbooking.booking

import android.os.Bundle
import android.app.Dialog
import android.content.Context
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatDialogFragment

class ExampleDialog : AppCompatDialogFragment() {
    private var listener: ExampleDialog.ExampleDialogListener? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("Attention!")
            .setMessage("Do you want to increase the counter by 1?")
            .setNegativeButton("cancel",
                DialogInterface.OnClickListener { dialogInterface, i -> })
            .setPositiveButton("yes",
                DialogInterface.OnClickListener { dialogInterface, i -> listener?.onYesClicked() })
        return builder.create()
    }

    interface ExampleDialogListener {
        fun onYesClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            context as ExampleDialog.ExampleDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                context.toString()
                        + "must implement ExampleDialogListener"
            )
        }
    }
}