package amhsn.data.remote

import amhsn.data.BuildConfig
import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*

object Ktor{

val client = HttpClient(Android) {

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
        host = BuildConfig.BASE_URL
        url {
            protocol = URLProtocol.HTTPS
        }

        header("X-Api-Key", "ee267f17eeb74ac0965887a772ef6f90")
    }

}
}