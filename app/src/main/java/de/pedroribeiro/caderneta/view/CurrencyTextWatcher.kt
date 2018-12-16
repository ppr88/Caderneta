package de.pedroribeiro.caderneta.view

import android.text.Editable
import android.text.TextWatcher

/**
 * A specialized TextWatcher designed specifically for converting EditText values to a pretty-print
 * string currency value.
 * @param editText The EditText box to which this TextWatcher is being applied.
 * Used for replacing user-entered text with formatted text as well as handling cursor position for
 * inputting monetary values
 */
class CurrencyTextWatcher(private val editText: CurrencyEditText) : TextWatcher {

    private var ignoreIteration: Boolean = false
    private var lastGoodInput: String = ""

    /**
     * After each letter is typed, this method will take in the current text, process it, and take
     * the resulting formatted string and place it back in the EditText box the TextWatcher is
     * applied to.
     * @param editable text to be transformed
     */
    override fun afterTextChanged(editable: Editable) {
        //Use the ignoreIteration flag to stop our edits to the text field from triggering an
        //endlessly recursive call to afterTextChanged
        if (!ignoreIteration) {
            ignoreIteration = true

            //Start by converting the editable to something easier to work with, then remove all
            //non-digit characters
            var newText = editable.toString()
            var textToDisplay: String


            if (newText.isEmpty()) {
                lastGoodInput = ""
                editText.rawValue = 0
                editText.setText("")
                return
            }

            //Remove all non-numeric symbols
            newText = newText.replace("[^0-9]".toRegex(), "")
            if (newText.isNotEmpty()) {
                //Store a copy of the raw input to be retrieved later by getRawValue
                editText.rawValue = newText.toLong()
            }
            try {
                textToDisplay = CurrencyTextFormatter.formatText(newText, editText.currentLocale,
                        editText.defaultLocale, editText.decimalDigits)
            } catch (exception: IllegalArgumentException) {
                textToDisplay = lastGoodInput
            }

            editText.setText(textToDisplay)

            //Store the last known good input so if there are any issues with new input later,
            //we can fall back gracefully.
            lastGoodInput = textToDisplay

            //locate the position to move the cursor to, which will always be the last digit.
            val currentText = editText.text.toString()
            val cursorPosition = indexOfLastDigit(currentText) + 1


            //Move the cursor to the end of the numerical value to enter the next number in a
            //right-to-left fashion, like you would on a calculator.
            if (currentText.length >= cursorPosition) {
                editText.setSelection(cursorPosition)
            }

        } else {
            ignoreIteration = false
        }
    }

    //Thanks to Lucas Eduardo for this contribution to update the cursor placement code.
    private fun indexOfLastDigit(str: String): Int {
        var result = 0

        for (i in 0 until str.length) {
            if (Character.isDigit(str[i])) {
                result = i
            }
        }

        return result
    }

    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}
}