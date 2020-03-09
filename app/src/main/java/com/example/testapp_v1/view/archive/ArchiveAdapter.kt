package com.example.testapp_v1.view.archive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp_v1.R
import com.example.testapp_v1.model.inventories.Inventory
import kotlinx.android.synthetic.main.document_item_layout.view.*

class ArchiveAdapter(
    private val inventories: List<Inventory>,
    private val onArchiveItemClickListener: OnArchiveItemClickListener
) : RecyclerView.Adapter<ArchiveAdapter.NoteViewHolder>() {

     interface OnArchiveItemClickListener {
       fun onItemClick(currentID :String,comment:String, boolean: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val holder = NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.document_item_layout, parent, false)
        )

        holder.view.setOnClickListener {
            val action = ArchiveFragmentDirections.toDetailsFragment()
            action.string = inventories[holder.adapterPosition].id.toString()
            action.title = inventories[holder.adapterPosition].docName
            action.followDetails = "details"
            Navigation.findNavController(it).navigate(action)
        }
        holder.view.image_view_more_vert.setOnClickListener {
            val popupMenu = PopupMenu(holder.view.context, it)
            popupMenu.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.toHomePage)
                    onArchiveItemClickListener.onItemClick(inventories[holder.adapterPosition].id.toString(),"",false)
                true
            }
            popupMenu.inflate(R.menu.archive_menu)
            popupMenu.show()
        }

        return holder
    }

    override fun getItemCount() = inventories.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.view.text_view_title.text = inventories[position].docName
    }

    class NoteViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}