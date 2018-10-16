package de.pedroribeiro.caderneta

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object DateCurrencyFormat {

    //TODO: fetch these properties from settings
    private const val CURRENCY = "EUR"
    private const val DATE_FORMAT = "dd MMM, yyyy"

    private val numberFormat = NumberFormat.getInstance()
    private val dateFormat = SimpleDateFormat()

    init {
        numberFormat.currency = Currency.getInstance(CURRENCY)
        dateFormat.applyPattern(DATE_FORMAT)
    }

    /**
     * Given a date, format it as string
     */
    fun format(date: Date): String {
        return dateFormat.format(date)
    }

    /**
     * Given a monetary value as double, format it as string with currency symbol and two
     * decimal digits
     */
    fun format(value: Double): String {
        numberFormat.minimumFractionDigits = 2
        numberFormat.maximumFractionDigits = 2
        return numberFormat.currency.symbol + numberFormat.format(value)
    }
}