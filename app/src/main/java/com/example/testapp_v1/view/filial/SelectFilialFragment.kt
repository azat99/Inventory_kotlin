package com.example.testapp_v1.view.filial


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.example.testapp_v1.R
import com.example.testapp_v1.model.api.InternetCheck
import com.example.testapp_v1.model.filialsEntity.Data
import com.example.testapp_v1.viewmodel.SelectFilialViewModel
import kotlinx.android.synthetic.main.fragment_select_filial.*
import org.koin.android.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class SelectFilialFragment : Fragment() {

    private lateinit var sPref: SharedPreferences
    private val SAVED_TEXT = "filial_name"
    private val SAVED_ID = "filial_id"

    private lateinit var list: List<Data>

    var pos :Int = 0

    private val selectFilialViewModel: SelectFilialViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_select_filial, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        InternetCheck{internet ->
            if(!internet){
                Toast.makeText(context,"Нет подключения к интернету!",Toast.LENGTH_SHORT).show()
            }else{
                selectFilialViewModel.putFilialDataToDatabase()
                selectFilialViewModel.getDataFromDatabase().observe(viewLifecycleOwner, Observer {
                    list = it

                    val listForSpinner = mutableListOf("")
                    for (i in list) {
                        listForSpinner.add(i.name)
                    }
                    listForSpinner.removeAt(0)

                    val adapter = ArrayAdapter<String>(
                        context!!,
                        android.R.layout.simple_spinner_item,
                        listForSpinner
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
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

                btn.setOnClickListener {
                    sPref = context!!.getSharedPreferences("filial_table",Context.MODE_PRIVATE)
                    val ed: SharedPreferences.Editor = sPref.edit()
                    ed.putString(SAVED_TEXT, list[pos].name)
                    ed.putString(SAVED_ID, list[pos].id)
                    ed.apply()

                    selectFilialViewModel.putBranchDataToDatabase()
                    selectFilialViewModel.putTdsDataToDatabase()

                    Navigation.findNavController(activity!!,R.id.nav_host_fragment).navigate(R.id.toHome,
                        null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.selectFilialFragment,
                                true).build()
                    )
                }
            }
        }





    }

}



