package com.example.testapp_v1.view.main

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp_v1.R
import com.example.testapp_v1.model.inventories.Inventory
import kotlinx.android.synthetic.main.document_item_layout.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DocumentAdapter(
    private val inventories: List<Inventory>,
    private val onArchiveItemClickListener: OnArchiveItemClickListener
) : RecyclerView.Adapter<DocumentAdapter.NoteViewHolder>() {

    interface OnArchiveItemClickListener {
        fun onItemClick(currentID: String, comment: String, boolean: Boolean)

        fun onItemSend(currentID: String, date: String)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val holder = NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.document_item_layout, parent, false)
        )

        holder.view.setOnClickListener {
            val action = HomeFragmentDirections.toDetailsFragment()
            action.string = inventories[holder.adapterPosition].id.toString()
            action.title = inventories[holder.adapterPosition].docName
            Navigation.findNavController(it).navigate(action)
        }

        holder.view.image_view_more_vert.setOnClickListener {

            val popupMenu: PopupMenu = PopupMenu(holder.view.context, it)

            popupMenu.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.toArchiv) {
                    val builder = AlertDialog.Builder(holder.view.context!!)
                    val dialogLayout = LayoutInflater.from(parent.context)
                        .inflate(R.layout.alert_edit_text_layout, null)
                    val editText = dialogLayout.findViewById<EditText>(R.id.editText)
                    builder.setTitle("Comment")
                    builder.setView(dialogLayout)
                    builder.setPositiveButton("OK") { _, _ ->

                        if (editText.text.isNotEmpty()) {
                            onArchiveItemClickListener.onItemClick(
                                inventories[holder.adapterPosition].id.toString(),
                                editText.text.toString(),
                                true
                            )
                        } else {
                            editText.error = "Напиши какойто комментарий"
                        }
                    }
                    builder.setNegativeButton("Отмена") { _, _ -> }
                    builder.show()
                }
                if (item.itemId == R.id.toDetails) {

                    val action = HomeFragmentDirections.toDetailsFragment()
                    action.string = inventories[holder.adapterPosition].id.toString()
                    action.title = inventories[holder.adapterPosition].docName
                    action.followDetails = "details"
                    Navigation.findNavController(it).navigate(action)

                }
                if (item.itemId == R.id.toSend){
                    val current = Date()
                    val formatter = SimpleDateFormat("dd.MM.yyyy. HH:mm:ss")
                    val currentDate = formatter.format(current)

                    onArchiveItemClickListener.onItemSend(
                        inventories[holder.adapterPosition].id.toString(),
                        currentDate)
                    Toast.makeText(holder.view.context, "Отправлено", Toast.LENGTH_SHORT).show()
                }
                true
            }
            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.show()
        }

        return holder
    }

    override fun getItemCount() = inventories.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val date = inventories[position].dateTime
        val format = SimpleDateFormat("dd.MM.yyyy. HH:mm:ss")
        val dateFormate = format.parse(date)
        val long = dateFormate.time
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = formatter.format(long)

        holder.view.text_view_title.text = inventories[position].docName
        holder.view.text_view_date.text = currentDate.toString()
        holder.view.text_view_count.text = "("+inventories[position].counter+")"
    }

    class NoteViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}