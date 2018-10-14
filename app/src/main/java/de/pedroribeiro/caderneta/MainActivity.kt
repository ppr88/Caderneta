package de.pedroribeiro.caderneta

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var expenseListView: RecyclerView
    private val expenseListAdapter = ExpenseListAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //prepare list that displays all expenses and connect it to data provider (ViewModel)
        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel::class.java)
        expenseViewModel.allExpenses.observe(this, Observer { expenses ->
            expenseListAdapter.expenses = expenses
        })
        expenseListView = findViewById<RecyclerView>(R.id.expenseListRecyclerView)
                .apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = expenseListAdapter
        }



        // clicking on FAB opens activity to add a new expense
        fab.setOnClickListener {
            Log.i(TAG, "FAB clicked")
            startActivity(Intent(this, NewExpenseActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
