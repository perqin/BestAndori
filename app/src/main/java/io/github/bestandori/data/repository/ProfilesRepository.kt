package io.github.bestandori.data.repository

import io.github.bestandori.data.model.ProfileModel
import io.github.bestandori.data.model.Server
import io.github.bestandori.data.storage.FileStorage

object ProfilesRepository {
    private lateinit var fileStorage: FileStorage

    fun init(fileStorage: FileStorage) {
        this.fileStorage = fileStorage
    }

    suspend fun loadProfile(id: Int): ProfileModel {
        return ProfileModel.fromSerialized(id, fileStorage.load(filenameOf(id)))
    }

    suspend fun saveProfile(profile: ProfileModel) {
        fileStorage.save(filenameOf(profile.id), profile.serialize())
    }

    /**
     * [basic] Whether the cards and the items are loaded. Load on false
     */
    suspend fun loadAllProfiles(basic: Boolean): List<ProfileModel> {
        return fileStorage.list().map {
            ProfileModel.fromSerialized(it.split('.')[0].toInt(), fileStorage.load(it), basic)
        }
    }

    suspend fun createNewEmptyProfile() {
        // TODO: Id conflict
        val newId = fileStorage.list().size + 1
        saveProfile(ProfileModel(newId, "Unnamed", Server.JP, null))
    }

    private fun filenameOf(id: Int) = "$id.json"
}