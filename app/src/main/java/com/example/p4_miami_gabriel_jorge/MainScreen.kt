// Gabriel Almarcha Martínez y Jorge Maqueda Miguel

package com.example.p4_miami_gabriel_jorge

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MiamiViewModel, onItemClick: (Int) -> Unit) {
    val currentCategory by viewModel.selectedCategory.collectAsState()
    val filteredItems by viewModel.filteredItems.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    var languageMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            // Barra superior con título de categoría y selector de idioma
            TopAppBar(
                title = {
                    Text(
                        text =
                            "${stringResource(R.string.miami).uppercase()} ${
                                stringResource(
                                    currentCategory.titleRes
                                )
                            }",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                actions = {
                    Box {
                        IconButton(
                            onClick = { languageMenuExpanded = true }
                        ) {
                            Icon(
                                imageVector =
                                    Icons.Default.Language,
                                contentDescription = "Language",
                                tint =
                                    MaterialTheme.colorScheme
                                        .primary
                            )
                        }

                        DropdownMenu(
                            expanded = languageMenuExpanded,
                            onDismissRequest = {
                                languageMenuExpanded = false
                            }
                        ) {
                            AppLanguage.entries.forEach { language ->
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            horizontalArrangement =
                                                Arrangement
                                                    .spacedBy(
                                                        8.dp
                                                    ),
                                            verticalAlignment =
                                                Alignment
                                                    .CenterVertically
                                        ) {
                                            Text(
                                                text =
                                                    language.flag,
                                                fontSize =
                                                    20.sp
                                            )
                                            Text(
                                                text =
                                                    language.displayName,
                                                fontWeight =
                                                    if (language ==
                                                        currentLanguage
                                                    )
                                                        FontWeight
                                                            .Bold
                                                    else
                                                        FontWeight
                                                            .Normal
                                            )
                                        }
                                    },
                                    onClick = {
                                        viewModel
                                            .changeLanguage(
                                                language
                                            )
                                        languageMenuExpanded =
                                            false
                                    }
                                )
                            }
                        }
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor =
                            MaterialTheme.colorScheme.primary
                    )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            
            // Grid de elementos (Masonry Layout)
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding =
                    PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 110.dp
                    ),
                verticalItemSpacing = 12.dp,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = filteredItems,
                    key = { it.id },
                    span = { item ->
                        // Elementos patrocinados
                        if (item.sponsored) StaggeredGridItemSpan.FullLine
                        else StaggeredGridItemSpan.SingleLane
                    }
                ) { item ->
                    MiamiCard(item = item, onClick = { onItemClick(item.id) })
                }
            }

            // Gradient decorativo en la parte inferior (detrás del nav bar)
            Box(
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            Brush.verticalGradient(
                                colors =
                                    listOf(
                                        Color.Transparent,
                                        MaterialTheme
                                            .colorScheme
                                            .background
                                    )
                            )
                        )
            )

            // Barra de navegación flotante
            Surface(
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 24.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(28.dp)
                        ),
                shape = RoundedCornerShape(28.dp),
                shadowElevation = 12.dp,
                tonalElevation = 0.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                NavigationBar(
                    modifier =
                        Modifier
                            .height(64.dp)
                            .padding(horizontal = 8.dp),
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp,
                    windowInsets = WindowInsets(0.dp)
                ) {
                    MiamiCategory.entries.forEach { category ->
                        val selected = currentCategory == category

                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                viewModel.updateCategory(category)
                            },
                            colors =
                                NavigationBarItemDefaults.colors(
                                    indicatorColor =
                                        Color.Transparent,
                                    selectedIconColor =
                                        MaterialTheme
                                            .colorScheme
                                            .background,
                                    unselectedIconColor =
                                        MaterialTheme
                                            .colorScheme
                                            .onBackground
                                            .copy(
                                                alpha =
                                                    0.5f
                                            )
                                ),
                            icon = {
                                Box(
                                    contentAlignment =
                                        Alignment.Center,
                                    modifier =
                                        Modifier.then(
                                            if (selected
                                            ) {
                                                Modifier
                                                    .height(
                                                        40.dp
                                                    )
                                                    .width(
                                                        64.dp
                                                    )
                                                    .background(
                                                        color =
                                                            MaterialTheme
                                                                .colorScheme
                                                                .primary,
                                                        shape =
                                                            RoundedCornerShape(
                                                                20.dp
                                                            )
                                                    )
                                            } else {
                                                Modifier
                                            }
                                        )
                                ) {
                                    Icon(
                                        modifier =
                                            Modifier.size(
                                                28.dp
                                            ),
                                        painter =
                                            painterResource(
                                                id =
                                                    if (selected
                                                    )
                                                        category.iconSelected
                                                    else
                                                        category.icon
                                            ),
                                        contentDescription =
                                            stringResource(
                                                category.titleRes
                                            )
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Componente visual para cada elemento de la lista.
 */
@Composable
fun MiamiCard(item: MiamiItem, onClick: () -> Unit) {
    val cardShape = RoundedCornerShape(20.dp)

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .aspectRatio(item.aspectRatio)
                .clip(cardShape)
                .border(
                    width = 1.dp,
                    color =
                        MaterialTheme.colorScheme.secondary.copy(
                            alpha = 0.35f
                        ),
                    shape = cardShape
                )
                .clickable { onClick() },
        shape = cardShape,
        colors =
            CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Imagen del elemento
            AsyncImage(
                model = item.imageRes,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradient oscuro en la foto para que se lea bien el título
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
                                            .surface
                                            .copy(
                                                alpha =
                                                    0.7f
                                            ),
                                        MaterialTheme
                                            .colorScheme
                                            .surface
                                    )
                            )
                        )
            )

            // Etiqueta de "Patrocinado" si es
            if (item.sponsored) {
                Box(
                    modifier =
                        Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp)
                            .background(
                                color =
                                    MaterialTheme.colorScheme
                                        .secondary,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = stringResource(R.string.sponsored),
                            style =
                                MaterialTheme.typography.labelSmall
                                    .copy(
                                        color = Color.White,
                                        fontWeight =
                                            FontWeight
                                                .Bold
                                    )
                        )
                    }
                }
            }

            // Información de texto: nombre y tipo
            Column(
                modifier =
                    Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                Text(
                    text = item.name,
                    style =
                        MaterialTheme.typography.bodyLarge.copy(
                            fontSize =
                                if (item.sponsored) 24.sp
                                else 16.sp,
                            fontWeight =
                                if (item.sponsored) FontWeight.Bold
                                else FontWeight.SemiBold,
                            lineHeight =
                                if (item.sponsored) 28.sp else 20.sp
                        ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = item.type,
                    style =
                        if (item.sponsored)
                            MaterialTheme.typography.titleMedium
                        else MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
