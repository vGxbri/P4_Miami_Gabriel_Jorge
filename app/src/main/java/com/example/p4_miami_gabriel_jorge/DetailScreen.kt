package com.example.p4_miami_gabriel_jorge

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun DetailScreen(itemId: Int, viewModel: MiamiViewModel, onBack: () -> Unit) {
    val item = viewModel.getItemById(itemId) ?: return

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
            // Imagen principal (con degradado)
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)) {
                AsyncImage(
                    model = item.imageRes,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors =
                                        listOf(
                                            Color.Transparent,
                                            Color.Transparent,
                                            MaterialTheme
                                                .colorScheme
                                                .background
                                                .copy(
                                                    alpha =
                                                        0.6f
                                                ),
                                            MaterialTheme
                                                .colorScheme
                                                .background
                                        )
                                )
                            )
                )

                // Gradient superior para la status bar
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors =
                                        listOf(
                                            MaterialTheme
                                                .colorScheme
                                                .background
                                                .copy(
                                                    alpha =
                                                        0.5f
                                                ),
                                            Color.Transparent
                                        )
                                )
                            )
                )

                // Título sobre la imagen
                Column(
                    modifier =
                        Modifier
                            .align(Alignment.BottomStart)
                            .padding(
                                horizontal = 20.dp,
                                vertical = 16.dp
                            )
                ) {
                    Text(
                        text = item.name,
                        style =
                            MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                lineHeight = 36.sp
                            ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            // Contenido debajo
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Spacer(modifier = Modifier.height(8.dp))

                // Categoría y promocionado si es
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color =
                            MaterialTheme.colorScheme.secondary.copy(
                                alpha = 0.1f
                            ),
                        shape = RoundedCornerShape(12.dp),
                        modifier =
                            Modifier.border(
                                width = 1.dp,
                                color =
                                    MaterialTheme.colorScheme
                                        .secondary.copy(
                                            alpha = 0.35f
                                        ),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Text(
                            text = item.type,
                            modifier =
                                Modifier.padding(
                                    horizontal = 14.dp,
                                    vertical = 6.dp
                                ),
                            style =
                                MaterialTheme.typography.bodyMedium
                                    .copy(
                                        fontWeight =
                                            FontWeight
                                                .SemiBold
                                    ),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    if (item.sponsored) {
                        Box(
                            modifier =
                                Modifier
                                    .background(
                                        color =
                                            MaterialTheme
                                                .colorScheme
                                                .secondary,
                                        shape =
                                            RoundedCornerShape(
                                                12.dp
                                            )
                                    )
                                    .padding(
                                        horizontal = 10.dp,
                                        vertical = 6.dp
                                    )
                        ) {
                            Row(
                                verticalAlignment =
                                    Alignment.CenterVertically,
                                horizontalArrangement =
                                    Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector =
                                        Icons.Default.Info,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier =
                                        Modifier.size(14.dp)
                                )
                                Text(
                                    text =
                                        stringResource(
                                            R.string
                                                .sponsored
                                        ),
                                    style =
                                        MaterialTheme
                                            .typography
                                            .labelSmall
                                            .copy(
                                                color =
                                                    Color.White,
                                                fontWeight =
                                                    FontWeight
                                                        .Bold
                                            )
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Descripción
                Text(
                    text = item.description,
                    style =
                        MaterialTheme.typography.bodyLarge.copy(
                            lineHeight = 26.sp
                        ),
                    color =
                        MaterialTheme.colorScheme.onBackground.copy(
                            alpha = 0.8f
                        )
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }

        // Botón de volver
        IconButton(
            onClick = onBack,
            modifier =
                Modifier
                    .statusBarsPadding()
                    .padding(start = 12.dp, top = 8.dp)
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.surface
                    )
                    .border(
                        width = 1.dp,
                        color =
                            MaterialTheme.colorScheme.primary.copy(
                                alpha = 0.4f
                            ),
                        shape = CircleShape
                    )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}
