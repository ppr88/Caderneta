package de.pedroribeiro.caderneta.model

import androidx.room.Embedded

class ExpenseWithCategory {
    @Embedded
    lateinit var expense: Expense

    @Embedded
    lateinit var category: Category
}