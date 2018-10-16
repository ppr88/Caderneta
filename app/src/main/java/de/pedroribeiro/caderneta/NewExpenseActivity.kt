package de.pedroribeiro.caderneta

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.text.TextUtils
import android.content.Intent
import de.pedroribeiro.caderneta.model.Expense


class NewExpenseActivity : AppCompatActivity() {

    public val TAG = "CadernetaApp"

    private lateinit var bSaveExpense: Button
    private lateinit var eExpenseName: EditText
    private lateinit var eExpenseValue: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_expense)

        Log.d(TAG, "${this::class.qualifiedName}: onCreate executed")

        bSaveExpense = findViewById(R.id.bSaveExpense)
        eExpenseName = findViewById(R.id.eExpenseName)
        eExpenseValue = findViewById(R.id.eExpenseValue)


        /* onClick of save expense button:
         *  - validate input
         *  - save expense to database
         *  - return success or fail to main activity
         */
        bSaveExpense.setOnClickListener {view ->
            val replyIntent = Intent()
            if (eExpenseValue.text.isEmpty()) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }
            else {
                val value = eExpenseValue.text.toString().toDouble()
                val name = eExpenseName.text.toString()
                val expense = Expense(null, value, 8, name)
                val expenseViewModel = ViewModelProviders.of(this)
                        .get(ExpenseViewModel::class.java)
                expenseViewModel.insert(expense)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
}
