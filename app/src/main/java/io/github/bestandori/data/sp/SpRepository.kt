package io.github.bestandori.data.sp

import android.content.Context
import androidx.preference.PreferenceManager
import me.ibrahimsn.library.LiveSharedPreferences

object SpRepository {
    lateinit var liveSp: LiveSharedPreferences

    fun init(context: Context) {
        liveSp = LiveSharedPreferences(PreferenceManager.getDefaultSharedPreferences(context))
    }

    fun update(key: String, int: Int) {
        liveSp.getPreferences().edit().putInt(key, int).apply()
    }
}
