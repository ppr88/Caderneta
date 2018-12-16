package de.pedroribeiro.caderneta

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import com.google.android.material.textfield.TextInputEditText
import de.pedroribeiro.caderneta.model.Category
import de.pedroribeiro.caderneta.model.Expense
import android.view.inputmethod.EditorInfo
import android.widget.TextView




class NewExpenseActivity : AppCompatActivity() {

    public val TAG = "CadernetaApp"

    private lateinit var bSaveExpense: Button
    private lateinit var eExpenseName: TextInputEditText
    private lateinit var eExpenseValue: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_expense)

        Log.d(TAG, "${this::class.qualifiedName}: onCreate executed")

        //ID of the category selected by the user in the previous screen
        val categoryId = intent.getIntExtra(EXTRA_CATEGORY_ID, Category.CATEGORY_ID_OTHER)


        bSaveExpense = findViewById(R.id.bSaveExpense)
        eExpenseName = findViewById(R.id.eExpenseName)
        eExpenseValue = findViewById(R.id.eExpenseValue)


        /* onClick of save expense button:
         *  - validate input
         *  - save expense to database
         *  - return success or fail to main activity
         */
        bSaveExpense.setOnClickListener {
            val replyIntent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            if (eExpenseValue.text.isEmpty()) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }
            else {
                val value = eExpenseValue.text.toString().toDouble()
                val name = eExpenseName.text.toString()
                val expense = Expense(null, value, categoryId, name)
                val expenseViewModel = ViewModelProviders.of(this)
                        .get(ExpenseViewModel::class.java)
                expenseViewModel.insert(expense)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            startActivity(replyIntent)
        }

        /* if user clicks on "Done" in the soft keyboard:
         *  - perform a click on the save expense button
         */
        eExpenseValue.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                bSaveExpense.performClick()
                true
            }
            else false
        }
    }
}
