package com.daniel.codetest.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.daniel.codetest.Globals
import com.daniel.codetest.R

/**
 * Class used to save and read relatively small collection of key-values info,
 * which will be required for the application at any later point of time
 */
class SharedPrefsManager constructor(private val myContext: Context) {

    private val generalPrefPackageIdentifier = myContext.resources.getString(R.string.generalPrefPackageIdentifier)
    var sharedPrefs: SharedPreferences = myContext.getSharedPreferences(generalPrefPackageIdentifier, Context.MODE_PRIVATE)
        private set

    companion object {
        const val KEY_CONFIG_CHANGED = "configChanged"

        const val DEFAULT_KEY_CONFIG_CHANGED = false
    }

    var isConfigChanged: Boolean
        get() = sharedPrefs[KEY_CONFIG_CHANGED] ?: DEFAULT_KEY_CONFIG_CHANGED
        set(value) {
            sharedPrefs[KEY_CONFIG_CHANGED] = value
        }

    /**
     * Extension function to listen the edit() and apply() function calls on every SharedPreferences operation
     *
     * @param operation This contains operation type with key and value pair
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    /**
     * Creates or Updates a key value pair in SharedPrefs. Generic method that accepts any data type.
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     */
    private operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            null -> edit { it.remove(key) }
            else -> Log.e(Globals.APPLICATION_NAME, myContext.getString(R.string.unsupported_property_type))
        }
    }

    /**
     * This function retrieve saved values from shared preferences by referring key
     *
     * @param key The name of the preference to retrieve.
     * @param defaultValue If there is no any value related to referring key this will return this default value
     */
    private inline operator fun <reified T : Any> SharedPreferences.get(
        key: String, defaultValue: T? = null
    ): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: 0) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: 0) as T?
            else -> {
                Log.e(Globals.APPLICATION_NAME, myContext.getString(R.string.unsupported_property_type)); null
            }
        }
    }

}