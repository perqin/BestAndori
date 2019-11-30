package io.github.bestandori

import android.app.Application
import io.github.bestandori.data.repository.ProfilesRepository
import io.github.bestandori.data.storage.NaiveFileStorage

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ProfilesRepository.init(NaiveFileStorage(filesDir.resolve("profiles")))
    }
}