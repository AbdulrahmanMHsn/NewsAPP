package amhsn.newsapp.presentation.di

import amhsn.data.remote.api_service.NewsAPI
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Ktor {

    private val client = HttpClient(Android) {

        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(HttpTimeout) {
            connectTimeoutMillis = 5000
            requestTimeoutMillis = 5000
            socketTimeoutMillis = 5000
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger com.amhsn.Ktor", message)
                }
            }
            level = LogLevel.BODY
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status", "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            host = amhsn.data.BuildConfig.BASE_URL
            url {
                protocol = URLProtocol.HTTPS
            }

            header("X-Api-Key", "7ed48ad3352b4d6aac3ea1ea84e02421")
        }
    }

    @Singleton
    @Provides
    fun provideKtorClient(): HttpClient {
        return client
    }

    @Singleton
    @Provides
    fun provideApiService(client: HttpClient): NewsAPI {
        return NewsAPI(client)
    }
}
