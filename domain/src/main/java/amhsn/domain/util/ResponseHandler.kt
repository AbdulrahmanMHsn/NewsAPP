package amhsn.domain.util

import java.net.SocketTimeoutException
import java.net.UnknownHostException


suspend fun <T> handleResponse(
    onFailure: (suspend () -> T)? = null,
    response: suspend () -> T,
): Response<T> {
    return try {
        Response.Success(response())
    } catch (t: Throwable) {
        return (onFailure?.let { invokeOnFailure ->
            try {
                Response.Error(t, errorMessage(t))
            } catch (t: Throwable) {
                t.printStackTrace()
                Response.Error(t, errorMessage(t))
            }
        } ?: run {
            t.printStackTrace()
            Response.Error(t, errorMessage(t))
        }) as Response<T>
    }
}

private suspend fun errorMessage(t: Throwable): String {
    return when (t) {
        is SocketTimeoutException -> "Failed to connect to the server, please try again!"
        is UnknownHostException -> "No Internet Connection, please try again!"
        else -> "Something went wrong!"
    }
}
