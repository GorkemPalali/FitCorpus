package com.example.fitcorpus.core.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.fitcorpus.domain.model.UserRole
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "fitcorpus_session")

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    
    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "fitcorpus_tokens",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    
    private val dataStore = context.dataStore
    
    private var accessToken: String? = null
    
    fun saveAccessToken(token: String) {
        accessToken = token
    }
    
    fun getAccessToken(): String? = accessToken
    
    fun saveRefreshToken(token: String) {
        sharedPreferences.edit()
            .putString(KEY_REFRESH_TOKEN, token)
            .apply()
    }
    
    fun getRefreshToken(): String? {
        return sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
    }
    
    suspend fun saveRole(role: String) {
        dataStore.edit { preferences ->
            preferences[KEY_ROLE] = role
        }
    }
    
    fun getRole(): Flow<UserRole?> {
        return dataStore.data.map { preferences ->
            preferences[KEY_ROLE]?.let { 
                try {
                    UserRole.valueOf(it.uppercase())
                } catch (e: IllegalArgumentException) {
                    null
                }
            }
        }
    }
    
    suspend fun clearTokens() {
        accessToken = null
        sharedPreferences.edit()
            .remove(KEY_REFRESH_TOKEN)
            .apply()
        dataStore.edit { preferences ->
            preferences.remove(KEY_ROLE)
        }
    }
    
    fun isLoggedIn(): Boolean {
        return accessToken != null && getRefreshToken() != null
    }
    
    companion object {
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private val KEY_ROLE = stringPreferencesKey("user_role")
    }
}