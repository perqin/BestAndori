package io.github.bestandori.util

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ResourceLiveData<T>(private val loadResource: (suspend () -> T)? = null) : LiveData<Resource<T>>() {
    init {
        value = Resource.init()
    }

    @UiThread
    suspend fun reload(loadResourceOneShot: (suspend () -> T)? = null) {
        // Force-reload is not supported yet
        if (value.status != Status.LOADING) {
            value = Resource.load(value.data)
            value = withContext(Dispatchers.IO) {
                try {
                    Resource.succeed((loadResourceOneShot?:loadResource)!!())
                } catch (e: Exception) {
                    Resource.fail<T>(e)
                }
            }
        }
    }

    override fun getValue(): Resource<T> {
        return super.getValue()!!
    }
}
