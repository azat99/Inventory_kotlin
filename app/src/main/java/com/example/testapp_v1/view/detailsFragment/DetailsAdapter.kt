package com.example.testapp_v1.view.detailsFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp_v1.R
import com.example.testapp_v1.model.detailEntity.Details
import com.example.testapp_v1.model.tdsDataEntity.TdsData
import kotlinx.android.synthetic.main.details_item_layout.view.*
import kotlinx.android.synthetic.main.tds_item_layout.view.*

class DetailsAdapter(
    private var details: List<Details>,
    private var tdsData: TdsData?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TDS_ITEM = 1
    private val DETAIL_ITEM = 2
    private var pos = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return if (viewType == TDS_ITEM) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.tds_item_layout, parent, false)

            TdsViewHolder(view) //object of  will return
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.details_item_layout, parent, false)

            DetailsViewHolder(view) //object of  will return
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 && tdsData != null) {
            return TDS_ITEM
        }

        return DETAIL_ITEM
    }

    fun showFoundBarcode(tds: TdsData) {
        this.tdsData = tds
        notifyDataSetChanged()
    }

    fun showCheckedBarcodes(barcode: List<Details>) {
        this.details = barcode
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (tdsData == null) {
            details.size
        } else {
            details.size + 1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == TDS_ITEM) {
            val viewHolder = holder as TdsViewHolder
            viewHolder.numText.text = tdsData?.num
            viewHolder.barcodeText.text = tdsData?.barcode
            viewHolder.nameText.text = tdsData?.name
            viewHolder.unitNameText.text = tdsData?.unitName
            viewHolder.ownerText.text = tdsData?.owner
        } else {
            val viewHolder = holder as DetailsViewHolder
            if (tdsData == null) {
                pos = position
                viewHolder.barcodeText.text = details[position].barcode
                viewHolder.ownerName.text = details[position].name
                viewHolder.linearLayout.setOnClickListener {
                    viewHolder.ownerName.maxLines = Int.MAX_VALUE
                    viewHolder.ownerName.text = details[position].name
                }

            } else {
                pos = position - 1
                viewHolder.barcodeText.text = details[position - 1].barcode
                viewHolder.ownerName.text = details[position - 1].name
                 viewHolder.linearLayout.setOnClickListener {
                     viewHolder.ownerName.maxLines = Int.MAX_VALUE
                     viewHolder.ownerName.text = details[position-1].name
                 }
            }
        }
    }

    class DetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val barcodeText = view.barcode_text_view!!
        val ownerName = view.name_text_view!!
        val linearLayout = view.ll!!
    }

    class TdsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val numText = view.num_textView!!
        val barcodeText = view.barcode_textView!!
        val nameText = view.name_textView!!
        val unitNameText = view.unitName_textView!!
        val ownerText = view.owner_textView!!
    }
}
