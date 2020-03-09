package com.example.testapp_v1.view.settings

import android.app.ProgressDialog
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.testapp_v1.R
import com.example.testapp_v1.model.api.InternetCheck
import com.example.testapp_v1.model.filialsEntity.Data
import com.example.testapp_v1.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


@Suppress("DEPRECATION")
class SettingsFragment : Fragment() {

    private lateinit var list: List<Data>

    private var pos: Int = 0
    private var array = mutableListOf("")

    private val settingsViewModel: SettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        activity!!.title = " Настройки"

        relative_layout_update.setOnClickListener {
            updateData()
        }

        relative_layout_change_filial.setOnClickListener {
            changeFilial()
        }
        relative_layout_connens_to1C.setOnClickListener {
            val action = SettingsFragmentDirections.toConnectFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun updateData() {
        InternetCheck {
            when {
                !it -> {
                    Toast.makeText(context, "Нет подключения к интернету!", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    val progressDialog = ProgressDialog(activity)
                    progressDialog.max = 100
                    progressDialog.setMessage("Its updating....")
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                    progressDialog.show()
                    settingsViewModel.putDataToBranchDatabase()
                    settingsViewModel.putDataToFilialDatabase()
                    settingsViewModel.putDataToTdsDatabase()
                    GlobalScope.launch {
                        delay(3000)
                        progressDialog.dismiss()
                    }
                }
            }
        }

    }

    private fun changeFilial() {
        val builder = AlertDialog.Builder(context!!)
        val inflater = layoutInflater
        builder.setTitle("Document Name")
        val dialogLayout = inflater.inflate(R.layout.change_filial_layout, null)
        val spinner = dialogLayout.findViewById<Spinner>(R.id.spinner)
        builder.setView(dialogLayout)
        val adapter = ArrayAdapter<String>(
            context!!,
            android.R.layout.simple_spinner_item,
            array
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        settingsViewModel.getAllFilialList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            list = it
            listToAdapter()
            spinner.adapter = adapter
        })

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                pos = position

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        builder.setPositiveButton("OK") { _, i ->
            settingsViewModel.checkEndDate()
            settingsViewModel.foundLiveData.observe(viewLifecycleOwner, Observer {
                if (it.isEmpty()) {
                    Toast.makeText(context, "true", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "false", Toast.LENGTH_SHORT).show()
                }
            })


        }
        builder.setNegativeButton("Отмена") { _, i -> }
        builder.show()
    }

    private fun listToAdapter() {
        array.clear()
        for (i in list) {
            array.add(i.name)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }

}
