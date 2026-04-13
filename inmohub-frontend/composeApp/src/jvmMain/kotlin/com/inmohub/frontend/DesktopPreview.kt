package com.inmohub.frontend

import androidx.compose.desktop.ui.tooling.preview.Preview // ⚠️ Fíjate que el import es diferente, es específico de desktop
import androidx.compose.runtime.Composable

@Preview
@Composable
fun AppDesktopPreview() {
    App() // Llamamos a tu función compartida
}