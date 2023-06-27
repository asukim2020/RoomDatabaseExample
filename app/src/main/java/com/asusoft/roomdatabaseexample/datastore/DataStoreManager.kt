package com.ipageon.atnt.database.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object DataStoreManager {

    lateinit var dataStore: DataStore<Preferences>

    val INT_DEFAULT = 0
    val DOUBLE_DEFAULT = 0.0
    val STRING_DEFAULT = ""
    val BOOLEAN_DEFAULT = false
    val FLOAT_DEFAULT = 0.0F
    val LONG_DEFAULT = 0L

    fun setInt(key: String, data: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit {
                it[intPreferencesKey(key)] = data
            }
        }
    }

    fun getInt(key: String): Int {
        return getIntFlow(key).asLiveData().value ?: INT_DEFAULT
    }

    private fun getIntFlow(key: String): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[intPreferencesKey(key)] ?: INT_DEFAULT
        }
    }


    fun setDouble(key: String, data: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit {
                it[doublePreferencesKey(key)] = data
            }
        }
    }

    fun getDouble(key: String): Double {
        return getDoubleFlow(key).asLiveData().value ?: DOUBLE_DEFAULT
    }

    private fun getDoubleFlow(key: String): Flow<Double> {
        return dataStore.data.map { preferences ->
            preferences[doublePreferencesKey(key)] ?: DOUBLE_DEFAULT
        }
    }


    fun setString(key: String, data: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit {
                it[stringPreferencesKey(key)] = data
            }
        }
    }

    fun getString(key: String): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)] ?: STRING_DEFAULT
        }
    }


    fun setBoolean(key: String, data: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit {
                it[booleanPreferencesKey(key)] = data
            }
        }
    }

    fun getBoolean(key: String): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)] ?: BOOLEAN_DEFAULT
        }
    }


    fun setFloat(key: String, data: Float) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit {
                it[floatPreferencesKey(key)] = data
            }
        }
    }

    fun getFloat(key: String): Flow<Float> {
        return dataStore.data.map { preferences ->
            preferences[floatPreferencesKey(key)] ?: FLOAT_DEFAULT
        }
    }


    fun setLong(key: String, data: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit {
                it[longPreferencesKey(key)] = data
            }
        }
    }

    fun getLong(key: String): Flow<Long> {
        return dataStore.data.map { preferences ->
            preferences[longPreferencesKey(key)] ?: LONG_DEFAULT
        }
    }
}