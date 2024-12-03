package com.example.shipmentdemoapp.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore by preferencesDataStore("user_prefs")

class TokenManager @Inject constructor(private val context: Context) {



        companion object {
            private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        }


        suspend fun saveRefreshToken(token: String) {
            Log.d("TokenManager", "Saving refresh token: $token")
            context.dataStore.edit { preferences ->
                preferences[REFRESH_TOKEN_KEY] = token
            }

            Log.d("TokenManager", "Refresh token saved successfully")
        }

    suspend fun clearAllData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }


        val refreshToken: Flow<String?> = context.dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN_KEY]
        }
    }
