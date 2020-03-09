package com.example.testapp_v1.view.ocData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp_v1.R
import com.example.testapp_v1.model.inventories.Inventory
import com.example.testapp_v1.model.tdsDataEntity.TdsData
import kotlinx.android.synthetic.main.document_item_layout.view.*
import kotlinx.android.synthetic.main.tds_item_layout.view.*

class OCDataAdapter(
    private val tdsData: TdsData
) : RecyclerView.Adapter<OCDataAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.tds_item_layout, parent, false)
        )
        return holder
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.numText?.text = tdsData?.num
        holder.barcodeText?.text = tdsData?.barcode
        holder.nameText?.text = tdsData?.name
        holder.unitNameText?.text = tdsData?.unitName
        holder.ownerText?.text = tdsData?.owner
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val numText = view.num_textView
        val barcodeText = view.barcode_textView
        val nameText = view.name_textView
        val unitNameText = view.unitName_textView
        val ownerText = view.owner_textView
    }

}