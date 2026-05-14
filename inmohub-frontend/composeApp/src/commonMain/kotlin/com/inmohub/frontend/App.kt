package com.inmohub.frontend

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.inmohub.frontend.core.network.NetworkClient
import com.inmohub.frontend.core.themes.inmohubColorScheme
import com.inmohub.frontend.core.utils.JwtUtils
import com.inmohub.frontend.features.auth.data.local.SessionManager
import com.inmohub.frontend.features.auth.data.local.createDataStore
import com.inmohub.frontend.features.auth.presentation.LoginScreen
import com.inmohub.frontend.features.lead.presentation.desktop.DashboardScreen
import com.inmohub.frontend.features.property.presentation.PropertiesListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    // Singleton
    // remmber evita nuevas instancias si interfaz se redibuja
    val dataStore = remember { createDataStore() }
    val sessionManager = remember { SessionManager(dataStore) }

    // Ejecución de bloque asincrono una sola vez al iniciar aplicación
    LaunchedEffect(Unit) {
        NetworkClient.sessionManager = sessionManager
    }

    val hasSession by sessionManager.isSessionActive.collectAsState(initial = true)

    MaterialTheme(colorScheme = inmohubColorScheme) {
        if (!hasSession) {
            Navigator(LoginScreen())
        } else {
            var initialScreen by remember { mutableStateOf<Screen?>(null) }

            LaunchedEffect(Unit) {
                val token = sessionManager.getAccessToken()
                if (token == null) {
                    initialScreen = LoginScreen()
                } else {
                    val role = JwtUtils.getUserRoleFromToken(token)
                    initialScreen = when (role) {
                        "ADMIN", "AGENT" -> DashboardScreen()
                        "CLIENT", "OWNER" -> PropertiesListScreen()
                        else -> LoginScreen()
                    }
                }
            }

            if (initialScreen == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = inmohubColorScheme.secondary)
                }
            } else {
                // let operador seguro de kotlin, ejecuta lo que le pasen
                initialScreen?.let { Navigator(it) }
            }
        }
    }
}