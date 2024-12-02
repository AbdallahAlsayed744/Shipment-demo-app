package com.example.shipmentdemoapp.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserManager @Inject constructor(private val context: Context) {

    companion object {
        private val USER_ID_KEY = stringPreferencesKey("user_id")
    }

    suspend fun saveUserId(userId: Int) {
        Log.d("UserManager", "Saving user ID: $userId")
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId.toString()
        }
        Log.d("UserManager", "User ID saved successfully")
    }


    suspend fun clearAllData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }


    val userId: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_ID_KEY]
    }
}