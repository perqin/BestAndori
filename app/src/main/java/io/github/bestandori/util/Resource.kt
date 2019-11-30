package io.github.bestandori.util

data class Resource<T>(
    val status: Status,
    val data: T?,
    val error: Exception?
) {
    companion object {
        fun <T> init() = Resource<T>(Status.INITIAL, null, null)
        fun <T> load(data: T? = null) = Resource(Status.LOADING, data, null)
        fun <T> succeed(data: T) = Resource(Status.SUCCESSFUL, data, null)
        fun <T> fail(e: Exception) = Resource<T>(Status.FAILED, null, e)
    }
}

enum class Status {
    INITIAL, LOADING, SUCCESSFUL, FAILED
}
