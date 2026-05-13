package com.inmohub.frontend.features.auth.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import java.io.File

actual fun createDataStore(): DataStore<Preferences> {
    return createDataStore {
        val appDir = File(System.getProperty("user.home"), ".inmohub")
        if(!appDir.exists()) appDir.mkdir()
        File(appDir, "session.preferences_pb").absolutePath
    }
}