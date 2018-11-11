package de.pedroribeiro.caderneta

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import de.pedroribeiro.caderneta.model.Category

class CategoryGridAdapter : RecyclerView.Adapter<CategoryGridAdapter.CategoryGridViewHolder>() {

    //Kotlin magic for: variable that holds a function that receives a Category and returns nothing
    var onCategoryClickListener: ((Category) -> Unit)? = null

    var categories: List<Category>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class CategoryGridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val icon: ImageView = itemView.findViewById(R.id.iCategoryIcon)
        val label: TextView = itemView.findViewById(R.id.tCategoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryGridViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_grid_item,
                parent, false)
        return CategoryGridViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryGridViewHolder, position: Int) {

        val list = categories
        if (list != null && list.isNotEmpty()) {

            val category = list[position]

            //just getting the context from any view
            val context = holder.icon.context

            //the icon name of the categories is stored in the database
            //now I get the resource ID from this icon name...
            val categoryIconId = context.resources.getIdentifier(category.iconId, "drawable", context.packageName)
            //...and finally set the resource to the ImageView
            holder.icon.setImageResource(categoryIconId)

            //the string name of the categories is stored in the database
            //now I get the resource ID from this string name...
            val categoryTextId = context.resources.getIdentifier(category.labelId, "string", context.packageName)
            //...and finally set the text to the categories TextView
            holder.label.text = context.getString(categoryTextId)

            holder.itemView.setOnClickListener {
                onCategoryClickListener?.invoke(category)
            }
        }
        else {
            //TODO: show default categories
        }
    }

    override fun getItemCount(): Int = categories?.size ?: 0
}