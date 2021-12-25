package amhsn.domain.util

sealed class Response<out T> {
    data class Success<out T>(val result: T) : Response<T>()
    data class Error<out T>(val throwable: Throwable, val result: T?) : Response<T>()
    data class Loading<out T>(val loaded:Boolean) : Response<T>()
}
