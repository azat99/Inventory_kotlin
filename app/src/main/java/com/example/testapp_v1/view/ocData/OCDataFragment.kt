package com.example.testapp_v1.view.ocData

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp_v1.R
import com.example.testapp_v1.viewmodel.OCDataViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_oc_data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class OCDataFragment : Fragment() {

    private val ocDataViewModel: OCDataViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_oc_data, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        editText_oc_data.requestFocus()

        activity?.title =  " Основные средства"

        with(recyclerview_oc_data) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
        ocDataViewModel.foundLiveData.observe(this, Observer {
            if (it == null) {
                Snackbar.make(view!!, "Такого баркода в базе нету", Snackbar.LENGTH_LONG).show()
            } else {
                recyclerview_oc_data?.adapter = OCDataAdapter(it)
            }

        })

        editText_oc_data.doAfterTextChanged {
            if (it?.length == 13) {
                ocDataViewModel.onSearchPressedWith(it.toString())
                editText_oc_data.text.clear()
                editText_oc_data.requestFocus()
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }

}