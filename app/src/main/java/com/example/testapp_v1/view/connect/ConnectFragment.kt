package com.example.testapp_v1.view.connect

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

import com.example.testapp_v1.R

class ConnectFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.connect_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        activity?.title = " Подключение к 1С"
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }

}
