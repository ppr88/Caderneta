package de.pedroribeiro.caderneta

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import de.pedroribeiro.caderneta.model.Expense
import java.text.NumberFormat
import java.text.SimpleDateFormat

class ExpenseListAdapter() : RecyclerView.Adapter<ExpenseListAdapter.ExpenseListViewHolder>() {

    var expenses: List<Expense>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ExpenseListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.iExpenseListCategory)
        val category: TextView = itemView.findViewById(R.id.tExpenseListCategory)
        val description: TextView = itemView.findViewById(R.id.tExpenseListDescription)
        val value: TextView = itemView.findViewById(R.id.tExpenseListValue)
        val date: TextView = itemView.findViewById(R.id.tExpenseListDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.expense_list_item,
                parent, false)
        return ExpenseListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpenseListViewHolder, position: Int) {

         val list = expenses
        if (list != null && list.isNotEmpty()) {
            //TODO: set the icon
            //TODO: prepare category name translation
            holder.category.text = list[position].category.name
            holder.description.text = list[position].description
            //TODO: format currency
            val numberFormat = NumberFormat.getInstance()
            numberFormat.minimumFractionDigits = 2
            numberFormat.maximumFractionDigits = 2
            holder.value.text = numberFormat.currency.symbol + numberFormat.format(list[position].value)
            val dateFormat = SimpleDateFormat("dd MMM, yyyy")
            holder.date.text = dateFormat.format(list[position].spendDate)
        }
        else {
            //TODO: change the item view to something else
            holder.category.text = "Start by adding an expense"
        }
    }

    override fun getItemCount(): Int = expenses?.size ?: 0
}