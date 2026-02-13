package com.example.p4_miami_gabriel_jorge

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen(onEnter: () -> Unit) {

        Box(modifier = Modifier.fillMaxSize()) {
                // Imagen de fondo a pantalla completa
                Image(
                        painter = painterResource(id = R.drawable.bg_miami),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                )

                // Gradient oscuro desde abajo para legibilidad
                Box(
                        modifier =
                                Modifier.fillMaxSize()
                                        .background(
                                                Brush.verticalGradient(
                                                        colors =
                                                                listOf(
                                                                        MaterialTheme.colorScheme
                                                                                .background.copy(
                                                                                alpha = 0.30f
                                                                        ),
                                                                        Color.Transparent,
                                                                        MaterialTheme.colorScheme
                                                                                .background.copy(
                                                                                alpha = 0.70f
                                                                        ),
                                                                        MaterialTheme.colorScheme
                                                                                .background.copy(
                                                                                alpha = 0.90f
                                                                        ),
                                                                        MaterialTheme.colorScheme
                                                                                .background
                                                                )
                                                )
                                        )
                )

                // Contenido principal
                Column(
                        modifier =
                                Modifier.fillMaxSize()
                                        .systemBarsPadding()
                                        .padding(horizontal = 28.dp)
                                        .padding(bottom = 48.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.Start
                ) {
                        // Títulos con animación de fade-in
                        Column(verticalArrangement = Arrangement.spacedBy((-10).dp)) {
                                Text(
                                        text = stringResource(R.string.discover),
                                        style =
                                                MaterialTheme.typography.headlineLarge.copy(
                                                        fontWeight = FontWeight.SemiBold,
                                                        fontSize = 28.sp
                                                ),
                                        color = MaterialTheme.colorScheme.secondary
                                )
                                Text(
                                        text = stringResource(R.string.miami).uppercase(),
                                        style =
                                                MaterialTheme.typography.displayLarge.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 64.sp,
                                                        lineHeight = 68.sp
                                                ),
                                        color = MaterialTheme.colorScheme.secondary
                                )
                        }

                        // Botón de entrar
                        val buttonShape = RoundedCornerShape(20.dp)

                        Surface(
                                onClick = onEnter,
                                modifier = Modifier.fillMaxWidth().height(58.dp),
                                shape = buttonShape,
                                color = MaterialTheme.colorScheme.background,
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                        ) {
                                Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.fillMaxSize()
                                ) {
                                        Text(
                                                text =
                                                        stringResource(
                                                                R.string.explore_context,
                                                                stringResource(R.string.miami)
                                                        ),
                                                style =
                                                        MaterialTheme.typography.titleLarge.copy(
                                                                fontWeight = FontWeight.Bold,
                                                                letterSpacing = 1.sp
                                                        ),
                                                color = MaterialTheme.colorScheme.primary,
                                                textAlign = TextAlign.Center
                                        )
                                }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Subtítulo debajo del botón
                        Text(
                                text = stringResource(R.string.authors),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                        )
                }
        }
}
