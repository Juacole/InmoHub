package com.inmohub.frontend.features.auth.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

lateinit var applicationContext: Context

actual fun createDataStore(): DataStore<Preferences> {
    return createDataStore {
        applicationContext.filesDir.resolve("inmohub_session.preferences_pb").absolutePath
    }
}