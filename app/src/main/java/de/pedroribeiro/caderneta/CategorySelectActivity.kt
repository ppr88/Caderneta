package de.pedroribeiro.caderneta

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


const val EXTRA_CATEGORY_ID = "de.pedroribeiro.caderneta.CATEGORY_ID"

class CategorySelectActivity : AppCompatActivity() {

    private val TAG = "CategorySelectActivity"
    private val GRID_SPAN = 3

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryGridView: RecyclerView
    private val categoryGridAdapter = CategoryGridAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_select)

        Log.d(TAG, "${this::class.qualifiedName}: onCreate executed")


        //set on click listener: once a category is selected from the grid,
        //start the next activity which collects the expense details
        categoryGridAdapter.onCategoryClickListener = {category ->
            val intent = Intent(this, NewExpenseActivity::class.java).apply {
                putExtra(EXTRA_CATEGORY_ID, category.id)
            }
            startActivity(intent)
        }

        //prepare grid that displays all categories and connect it to data provider (ViewModel)
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        categoryViewModel.allCategories.observe(this, Observer { categories ->
            categoryGridAdapter.categories = categories
        })
        categoryGridView = findViewById<RecyclerView>(R.id.categoryGridRecyclerView)
            .apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@CategorySelectActivity, GRID_SPAN)
                adapter = categoryGridAdapter
            }
    }
}


