package com.example.testapp_v1.view.main


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp_v1.R
import com.example.testapp_v1.model.inventories.Inventory
import com.example.testapp_v1.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(), DocumentAdapter.OnArchiveItemClickListener {

    private lateinit var sPref: SharedPreferences
    private val SAVED_TEXT = "filial_name"
    private val SAVED_ID = "filial_id"

    private val homeViewModel: HomeViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        checkPrefName()
        val title = context!!.getSharedPreferences("filial_table", MODE_PRIVATE)
            .getString(SAVED_TEXT, "")
        activity!!.title = " $title"

        recycler_view_notes.setHasFixedSize(true)
        recycler_view_notes.layoutManager = LinearLayoutManager(context)

        addNewDocument_Button.setOnClickListener {
            showAlertDialog()
        }

        val fId = sPref.getString(SAVED_ID, "").toString()

        homeViewModel.getAllListFromInventory(fId).observe(viewLifecycleOwner, Observer { it ->
            Collections.reverse(it)
            recycler_view_notes.adapter = DocumentAdapter(it, this)
        })
    }

    private fun checkPrefName() {
        sPref = context!!.getSharedPreferences("filial_table", Context.MODE_PRIVATE)
        val textId = sPref.getString(SAVED_ID, "")

        if (sPref.all.isEmpty()) {
            if (textId!!.isEmpty()) {
                Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(
                    R.id.toFilial,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(
                            R.id.navigation_home,
                            true
                        ).build()
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showAlertDialog() {
        val builder = AlertDialog.Builder(context!!)
        val inflater = layoutInflater
        builder.setTitle("Имя документа")
        val dialogLayout = inflater.inflate(R.layout.alert_edit_text_layout, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.editText)
        editText.requestFocus()
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { _, _ ->

            if (editText.text.isNotEmpty()) {
                val current = Date()
                val formatter = SimpleDateFormat("dd.MM.yyyy. HH:mm:ss")
                val currentDate = formatter.format(current)

                homeViewModel.putAllDataToDatabase(
                    Inventory(
                        editText.text.toString(),
                        currentDate,
                        sPref.getString(SAVED_TEXT, "").toString(),
                        sPref.getString(SAVED_ID, "").toString(),
                        false,
                        null,
                        null,
                        0
                    )
                )
            } else {
                editText.error = "Напиши имя документа"
            }
        }
        builder.setNegativeButton("Отмена") { _, _ -> }
        builder.show()
    }

    override fun onItemClick(currentID: String, comment: String, boolean: Boolean) {
        homeViewModel.updateArmFromInventory(currentID, comment, boolean)
    }

    override fun onItemSend(currentID: String, date: String) {
        homeViewModel.updateEndDate(currentID, date)
    }


}