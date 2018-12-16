package de.pedroribeiro.caderneta.view

import android.util.Log

import java.text.DecimalFormat
import java.util.Currency
import java.util.Locale

object CurrencyTextFormatter {

    @JvmOverloads
    fun formatText(`val`: String, locale: Locale, defaultLocale: Locale = Locale.US, decimalDigits: Int? = null): String {
        var `val` = `val`
        //special case for the start of a negative number
        if (`val` == "-") return `val`

        var currencyDecimalDigits: Int
        if (decimalDigits != null) {
            currencyDecimalDigits = decimalDigits
        } else {
            val currency = Currency.getInstance(locale)
            try {
                currencyDecimalDigits = currency.defaultFractionDigits
            } catch (e: Exception) {
                Log.e("CurrencyTextFormatter", "Illegal argument detected for currency: $currency, using currency from defaultLocale: $defaultLocale")
                currencyDecimalDigits = Currency.getInstance(defaultLocale).defaultFractionDigits
            }

        }

        var currencyFormatter: DecimalFormat
        try {
            currencyFormatter = DecimalFormat.getCurrencyInstance(locale) as DecimalFormat
        } catch (e: Exception) {
            try {
                Log.e("CurrencyTextFormatter", "Error detected for locale: $locale, falling back to default value: $defaultLocale")
                currencyFormatter = DecimalFormat.getCurrencyInstance(defaultLocale) as DecimalFormat
            } catch (e1: Exception) {
                Log.e("CurrencyTextFormatter", "Error detected for defaultLocale: $defaultLocale, falling back to USD.")
                currencyFormatter = DecimalFormat.getCurrencyInstance(Locale.US) as DecimalFormat
            }

        }

        //retain information about the negativity of the value before stripping all non-digits
        var isNegative = false
        if (`val`.contains("-")) {
            isNegative = true
        }

        //strip all non-digits so the formatter always has a 'clean slate' of numbers to work with
        `val` = `val`.replace("[^\\d]".toRegex(), "")
        //if there's nothing left, that means we were handed an empty string. Also, cap the raw input so the formatter doesn't break.
        if (`val` != "") {

            //if we're given a value that's smaller than our decimal location, pad the value.
            if (`val`.length <= currencyDecimalDigits) {
                val formatString = "%" + currencyDecimalDigits + "s"
                `val` = String.format(formatString, `val`).replace(' ', '0')
            }

            //place the decimal in the proper location to construct a double which we will give the formatter.
            //This is NOT the decimal separator for the currency value, but for the double which drives it.
            val preparedVal = StringBuilder(`val`).insert(`val`.length - currencyDecimalDigits, '.').toString()

            //Convert the string into a double, which will be passed into the currency formatter
            var newTextValue = java.lang.Double.valueOf(preparedVal)

            //reapply the negativity
            newTextValue *= (if (isNegative) -1 else 1).toDouble()

            //finally, do the actual formatting
            currencyFormatter.minimumFractionDigits = currencyDecimalDigits
            `val` = currencyFormatter.format(newTextValue)
        } else {
            throw IllegalArgumentException("Invalid amount of digits found (either zero or too many) in argument val")
        }
        return `val`
    }

}