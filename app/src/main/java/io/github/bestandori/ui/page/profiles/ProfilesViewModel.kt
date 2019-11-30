package io.github.bestandori.ui.page.profiles

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import io.github.bestandori.data.model.Server
import io.github.bestandori.data.repository.ProfilesRepository
import io.github.bestandori.data.sp.SpRepository
import io.github.bestandori.util.PK_ACTIVE_PROFILE_ID
import io.github.bestandori.util.Resource
import io.github.bestandori.util.ResourceLiveData
import kotlinx.coroutines.launch

class ProfilesViewModel(application: Application) : AndroidViewModel(application) {
    private val activeProfileId = SpRepository.liveSp.getInt(PK_ACTIVE_PROFILE_ID, -1)
    private val profiles = ResourceLiveData {
        ProfilesRepository.loadAllProfiles(true)
    }
    fun getProfiles(): LiveData<Resource<List<Profile>>> = MediatorLiveData<Resource<List<Profile>>>().apply {
        val synthesizer = {
            val activeProfileId = this@ProfilesViewModel.activeProfileId.value
            val profiles = this@ProfilesViewModel.profiles.value
            val synthesizedProfiles = profiles.data?.map { profileModel ->
                Profile(profileModel.id, profileModel.name, profileModel.server, profileModel.cardsCount, profileModel.id == activeProfileId)
            }
            value = Resource(profiles.status, synthesizedProfiles, profiles.error)
        }
        addSource(activeProfileId) { synthesizer() }
        addSource(profiles) { synthesizer() }
    }

    fun reload() {
        viewModelScope.launch {
            profiles.reload()
        }
    }

    fun switchProfile(id: Int) {
        SpRepository.update(PK_ACTIVE_PROFILE_ID, id)
    }

    /**
     * Add empty Profile locally
     */
    fun addProfile() {
        viewModelScope.launch {
            ProfilesRepository.createNewEmptyProfile()
            profiles.reload()
        }
    }
}

data class Profile(
    val id: Int,
    val name: String,
    val server: Server,
    val cardsCount: Int,
    val active: Boolean
)
