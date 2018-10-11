package de.pedroribeiro.caderneta

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.pedroribeiro.caderneta.model.Expense

class ExpenseListAdapter() : RecyclerView.Adapter<ExpenseListAdapter.ExpenseListViewHolder>() {

    var expenses: List<Expense>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ExpenseListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expenseNameView: TextView = itemView.findViewById(R.id.expenseListTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.expense_list_item,
                parent, false)
        return ExpenseListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpenseListViewHolder, position: Int) {
        val list = expenses
        if (list != null && list.isNotEmpty()) {
            holder.expenseNameView.text = list[position].toString()
        }
        else {
            holder.expenseNameView.text = "Start by adding an expense"
        }
    }

    override fun getItemCount(): Int = expenses?.size ?: 0
}