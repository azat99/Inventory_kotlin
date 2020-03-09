package com.example.testapp_v1.view.archive


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp_v1.R
import com.example.testapp_v1.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class ArchiveFragment : Fragment(),ArchiveAdapter.OnArchiveItemClickListener {

    private lateinit var sPref: SharedPreferences
    private val SAVED_ID = "filial_id"

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_archive, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity!!.title = " Архив"

        sPref = context!!.getSharedPreferences("filial_table", Context.MODE_PRIVATE)
        val textId = sPref.getString(SAVED_ID, "").toString()

        recycler_view_notes.setHasFixedSize(true)
        recycler_view_notes.layoutManager = LinearLayoutManager(context)

        homeViewModel.getAllArchiveListFromInventory(textId).observe(viewLifecycleOwner, Observer {
            recycler_view_notes.adapter = ArchiveAdapter(it,this)
        })
    }

    override fun onItemClick(currentID: String,comment:String, boolean: Boolean) {
        homeViewModel.updateArmFromInventory(currentID,comment,boolean)
    }

}
