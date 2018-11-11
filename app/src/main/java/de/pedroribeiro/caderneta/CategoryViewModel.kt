package de.pedroribeiro.caderneta

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import de.pedroribeiro.caderneta.model.Category
import de.pedroribeiro.caderneta.model.DataRepository

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DataRepository = DataRepository(application)
    val allCategories: LiveData<List<Category>> = repository.getAllCategories()

    fun insert(category: Category) = repository.insert(category)
}