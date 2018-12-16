package de.pedroribeiro.caderneta.view

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import com.google.android.material.textfield.TextInputEditText
import de.pedroribeiro.caderneta.R
import java.util.*


class CurrencyEditText(context: Context, attrs: AttributeSet) : TextInputEditText(context, attrs) {


    private var textWatcher: CurrencyTextWatcher? = null
    private var hintCache: String? = null

    /**
     * Retrieve the raw value that was input by the user in their currencies lowest
     * denomination (e.g. pennies).
     *
     * IMPORTANT: Remember that the location of the decimal varies by currentCurrency/Locale.
     * This returns the raw given value, and does not account for locality of the user.
     * It is up to the calling application to handle that level of conversion.
     * For example, if the text of the field is $13.37, this method will return a long with a
     * value of 1337, as penny is the lowest denomination for USD. It will be up to the calling
     * application to know that it needs to handle this value as pennies and not some other
     * denomination.
     */
    var rawValue = 0L
        internal set

    /**
     * The currently held default Locale to fall back on in the event of a failure with the Locale
     * field (typically due to the locale being set to a non-standards-compliant value).
     *
     * Defaults to Locale.US.
     *
     * NOTE: When overriding, de absolutely sure that this value is supported by ISO 3166. See
     * Java.util.Locale.getISOCountries() for a list of currently supported ISO 3166 locales
     * (note that this list may not be identical on all devices)
     */
    var defaultLocale: Locale = Locale.US

    /**
     * The current locale used by this instance of CurrencyEditText. By default, will be the users
     * device locale unless that locale is not ISO 3166 compliant, in which case the defaultLocale
     * will be used.
     */
    var currentLocale: Locale = retrieveLocale()
        /**
         * Override the locale used by CurrencyEditText (which is the users device locale by default).
         *
         * Will also update the hint text if a custom hint was not provided.
         *
         * IMPORTANT - This method does NOT update the currently set Currency object used by
         * this CurrencyEditText instance. If your use case dictates that Currency and Locale
         * should never break from their default pairing, use 'configureViewForLocale(locale)' instead
         * of this method.
         * @param locale The deviceLocale to set the CurrencyEditText box to adhere to.
         */
        set(locale) {
            field = locale
            refreshView()
        }

    /**
     * The number of decimal digits this CurrencyEditText instance is currently configured to use.
     * This value will be based on the current Currency object unless the value was overwritten.
     */
    var decimalDigits = 0
        /**
         * Sets the number of decimal digits the currencyTextFormatter will use, overriding
         * the number of digits specified by the current currency. Note, however,
         * that calls to setCurrency() and configureViewForLocale() will override this value.
         *
         * Note that this method will also invoke the formatter to update the current view if the
         * current value is not null/empty.
         *
         * @param digits The number of digits to be shown following the decimal in the formatted text.
         * Value must be between 0 and 340 (inclusive).
         * @throws IllegalArgumentException If provided value does not fall within the range
         * (0, 340) inclusive.
         */
        set(digits) {
            if (digits < 0 || digits > 340) {
                throw IllegalArgumentException("Decimal Digit value must be between 0 and 340")
            }
            field = digits

            refreshView()
        }


    /**
     * Sets up the CurrencyEditText view to be configured for a given locale, using that
     * locales default currency (so long as the locale is ISO-3166 compliant). If there is
     * an issue retrieving the locales currency, the defaultLocale field will be used.
     *
     * This is the most 'fool proof' way of configuring a CurrencyEditText view when not
     * relying on the default implementation, and is the recommended approach for handling
     * locale/currency setup if you choose not to rely on the default behavior.
     *
     * Note that this method will set the decimalDigits field, potentially overriding
     * values from previous setDecimalDigits calls.
     */
    fun configureViewForLocale(locale: Locale) {
        this.currentLocale = locale
        val currentCurrency = getCurrencyForLocale(locale)
        decimalDigits = currentCurrency.defaultFractionDigits
        refreshView()
    }


    /**
     * Convenience method to get the current Hint back as a string rather than a CharSequence
     */
    val hintString: String?
        get() {
            return super.getHint()?.toString()
        }

    private val defaultHintValue: String
        get() {
            try {
                return Currency.getInstance(currentLocale).getSymbol()
            } catch (e: Exception) {
                Log.w("CurrencyEditText", String.format("An error occurred while getting currency symbol for hint using locale '%s', falling back to defaultLocale", currentLocale))
                try {
                    return Currency.getInstance(defaultLocale).getSymbol()
                } catch (e1: Exception) {
                    Log.w("CurrencyEditText", String.format("An error occurred while getting currency symbol for hint using default locale '%s', falling back to USD", defaultLocale))
                    return Currency.getInstance(Locale.US).getSymbol()
                }

            }

        }


    /**
     * Sets the value to be formatted and displayed in the CurrencyEditText view.
     *
     * @param value - The value to be converted, represented in the target currencies lowest
     * denomination (e.g. pennies).
     */
    fun setValue(value: Long) {
        val formattedText = format(value)
        setText(formattedText)
    }

    /**
     * Pass in a value to have it formatted using the same rules used during data entry.
     * @param value A string which represents the value you'd like formatted. It is expected that this
     * string will be in the same format returned by the getRawValue() method (i.e. a series of
     * digits, such as "1000" to represent "$10.00"). Note that formatCurrency will take in ANY
     * string, and will first strip any non-digit characters before working on that string.
     * If the result of that processing reveals an empty string, or a string whose number of digits
     * is greater than the max number of digits, an exception will be thrown.
     * @return A deviceLocale-formatted string of the passed in value, represented as currentCurrency.
     */
    fun formatCurrency(value: String): String {
        return format(value)
    }

    /**
     * Pass in a value to have it formatted using the same rules used during data entry.
     * @param rawVal A long which represents the value you'd like formatted. It is expected that this
     * value will be in the same format returned by the getRawValue() method (i.e. a series of
     * digits, such as "1000" to represent "$10.00").
     * @return A deviceLocale-formatted string of the passed in value, represented as currentCurrency.
     */
    fun formatCurrency(rawVal: Long): String {
        return format(rawVal)
    }

    init {
        this.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED

        val currentCurrency = getCurrencyForLocale(currentLocale)
        decimalDigits = currentCurrency.getDefaultFractionDigits()
        initCurrencyTextWatcher()
        processAttributes(context, attrs)
    }

    /*
    PRIVATE HELPER METHODS
     */
    private fun refreshView() {
        setText(format(rawValue))
        updateHint()
    }

    private fun format(value: Long): String {
        return CurrencyTextFormatter.formatText(value.toString(), currentLocale, defaultLocale,
                decimalDigits)
    }

    private fun format(value: String): String {
        return CurrencyTextFormatter.formatText(value, currentLocale, defaultLocale, decimalDigits)
    }

    private fun initCurrencyTextWatcher() {
        if (textWatcher != null) {
            this.removeTextChangedListener(textWatcher)
        }
        textWatcher = CurrencyTextWatcher(this)
        this.addTextChangedListener(textWatcher)
    }

    private fun processAttributes(context: Context, attrs: AttributeSet) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.CurrencyEditText)
        hintCache = hintString
        updateHint()

        decimalDigits = array.getInteger(R.styleable.CurrencyEditText_decimal_digits, decimalDigits)

        array.recycle()
    }

    private fun updateHint() {
        if (hintCache == null) {
            hint = defaultHintValue
        }
    }

    private fun retrieveLocale(): Locale {
        var locale: Locale
        try {
            locale = resources.configuration.locale
        } catch (e: Exception) {
            Log.w("CurrencyEditText", String.format("An error occurred while retrieving users device locale, using fallback locale '%s'", defaultLocale), e)
            locale = defaultLocale
        }

        return locale
    }

    private fun getCurrencyForLocale(locale: Locale?): Currency {
        var currency: Currency
        try {
            currency = Currency.getInstance(locale)
        } catch (e: Exception) {
            try {
                Log.w("CurrencyEditText", String.format("Error occurred while retrieving currency information for locale '%s'. Trying default locale '%s'...", currentLocale, defaultLocale))
                currency = Currency.getInstance(defaultLocale)
            } catch (e1: Exception) {
                Log.e("CurrencyEditText", "Both device and configured default locales failed to report currentCurrency data. Defaulting to USD.")
                currency = Currency.getInstance(Locale.US)
            }

        }

        return currency
    }
}