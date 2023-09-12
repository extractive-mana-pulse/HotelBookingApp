package com.example.androidjobtask.presentation.adapters

import android.app.DatePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidjobtask.R
import com.example.androidjobtask.presentation.data.Item
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.skydoves.expandablelayout.ExpandableLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TouristAdapter(private val items: MutableList<Item>) : RecyclerView.Adapter<TouristAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ex: ExpandableLayout = itemView.findViewById(R.id.expandable)
        val clear: ImageButton = itemView.findViewById(R.id.clear)
        val textView: TextView = itemView.findViewById(R.id.touristName)
        val birthday: TextInputEditText = itemView.findViewById(R.id.DateOfBirth)
        var g = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_add_cricketer, parent, false)
        return ItemViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]

        holder.apply {
            holder.textView.text = item.text

            holder.ex.setOnClickListener {
                if (holder.g == 0){
                    g=1
                    ex.expand()
                }else{
                    g=0
                    ex.collapse()
                }
            }
            clear.setOnClickListener {
                if (position >= 0 && position < items.size) {
                    items.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(holder.birthday,myCalendar)

        }

        val textInputLayout = holder.itemView.findViewById<TextInputLayout>(R.id.textInputLayoutDateOfBirth)

        textInputLayout.setEndIconOnClickListener {
            DatePickerDialog(holder.itemView.context, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun updateLabel(birthday: TextInputEditText, myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        birthday.setText(sdf.format(myCalendar.time))
    }

    fun addItem(item: Item) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun removeItem(position: Int) {
        if (position >= 0 && position < items.size) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}

