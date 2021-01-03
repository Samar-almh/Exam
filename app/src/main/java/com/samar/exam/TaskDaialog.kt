package com.samar.exam

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.util.*


private const val REQUEST_DATE = 0
private const val DIALOG_DATE = "DialogDate"

class TaskDaialog: DialogFragment(),DatePickerFragment.Callbacks {


    val task=Task()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view=activity?.layoutInflater?.inflate(R.layout.fragment_task,null)
        val titleEditText=view?.findViewById(R.id.task_title) as EditText
        val detailsEditText=view?.findViewById(R.id.task_details) as EditText
        val dateButton =view?.findViewById(R.id.task_date) as Button

        dateButton.setOnClickListener {
            DatePickerFragment().apply {
                DatePickerFragment.newInstance(task.date).apply {
                    setTargetFragment(this@TaskDaialog, REQUEST_DATE)
                    show(this@TaskDaialog.requireFragmentManager(), DIALOG_DATE)
                }}}

        dateButton.setText(task.date.toString())

        return AlertDialog.Builder(requireContext(),R.style.ThemeOverlay_AppCompat_Dialog_Alert)
            .setView(view)
            .setPositiveButton("Add"){ dialog,_ ->
                val task=Task(
                    UUID.randomUUID(),
                    titleEditText.text.toString(),
                    detailsEditText.text.toString(),
                    task.date.toString()
                )
                targetFragment?.let {
                    (it as Callbacks).addTask(task)
                }
            }.setNegativeButton("Cancel"){dialog,_ ->
                dialog.cancel()
            }.create()

    }


    interface Callbacks {
        fun addTask(task: Task)

    }
    override fun onDateSelected(date: Date) {
        task.date=date
    }


}