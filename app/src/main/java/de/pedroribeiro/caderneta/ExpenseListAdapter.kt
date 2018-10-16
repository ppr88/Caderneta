package de.pedroribeiro.caderneta

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import de.pedroribeiro.caderneta.model.ExpenseWithCategory

class ExpenseListAdapter : RecyclerView.Adapter<ExpenseListAdapter.ExpenseListViewHolder>() {

    var expenses: List<ExpenseWithCategory>? = null
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
            //just getting the context from any view
            val context = holder.category.context

            //the icon name of the category is stored in the database
            //now I get the resource ID from this icon name...
            val categoryIconId = context.resources.getIdentifier(list[position].category.iconId, "drawable", context.packageName)
            //...and finally set the resource to the ImageView
            holder.icon.setImageResource(categoryIconId)

            //the string name of the category is stored in the database
            //now I get the resource ID from this string name...
            val categoryTextId = context.resources.getIdentifier(list[position].category.labelId, "string", context.packageName)
            //...and finally set the text to the category TextView
            holder.category.text = context.getString(categoryTextId)

            holder.description.text = list[position].expense.description
            holder.value.text = DateCurrencyFormat.format(list[position].expense.value)
            holder.date.text = DateCurrencyFormat.format(list[position].expense.spendDate)
        }
        else {
            //TODO: change the item view to something else
            holder.category.text = "Start by adding an expense"
        }
    }

    override fun getItemCount(): Int = expenses?.size ?: 0
}