package com.example.fitcorpus.core.auth

import com.example.fitcorpus.data.remote.api.AuthApi
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton
import javax.inject.Provider

@Singleton
class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val authApiProvider: Provider<AuthApi>
) : Authenticator {
    
    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) {
            return null
        }
        
        val refreshToken = tokenManager.getRefreshToken() ?: return null
        
        return runBlocking {
            try {
                val refreshResponse = authApiProvider.get().refreshToken(
                    com.example.fitcorpus.data.remote.dto.RefreshTokenRequest(refreshToken)
                )
                
                refreshResponse.body()?.let { tokenResponse ->
                    tokenManager.saveAccessToken(tokenResponse.accessToken)
                    tokenResponse.refreshToken?.let { newRefreshToken ->
                        tokenManager.saveRefreshToken(newRefreshToken)
                    }

                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${tokenResponse.accessToken}")
                        .build()
                }
            } catch (e: Exception) {
                tokenManager.clearTokens()
                null
            }
        }
    }
    
    private fun responseCount(response: Response): Int {
        var result = 1
        var current = response.priorResponse
        while (current != null) {
            result++
            current = current.priorResponse
        }
        return result
    }
}