//package amhsn.data.utils
//
//import amhsn.domain.util.Response
//import io.ktor.client.features.*
//import io.ktor.client.statement.*
//import kotlinx.serialization.json.Json
//import java.net.SocketTimeoutException
//import java.net.UnknownHostException
//
//suspend fun <T> handleResponse(
//    onFailure: (suspend () -> T)? = null,
//    response: suspend () -> T,
//): Response<T> {
//    return try {
//        Response.Success(response())
//    } catch (t: Throwable) {
//        return (onFailure?.let { invokeOnFailure ->
//            try {
//                Response.Error(t, errorMessage(t), invokeOnFailure)
//            } catch (t: Throwable) {
//                t.printStackTrace()
//                Response.Error(t, errorMessage(t))
//            }
//        } ?: run {
//            t.printStackTrace()
//            Response.Error(t, errorMessage(t))
//        }) as Response<T>
//    }
//}
//
//private suspend fun errorMessage(t: Throwable): String {
//    return when (t) {
//        is ClientRequestException -> getError(t.response).message.toString()
//        is SocketTimeoutException -> "Failed to connect to the server, please try again!"
//        is UnknownHostException -> "No Internet Connection, please try again!"
//        else -> "Something went wrong!"
//    }
//}
//
//private suspend fun getError(response: HttpResponse): Error {
//    val json = Json { ignoreUnknownKeys = true }
//    return json.decodeFromString(Error.serializer(), response.readText(Charsets.UTF_8))
//}