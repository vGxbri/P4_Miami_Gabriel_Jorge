// Gabriel Almarcha Martínez y Jorge Maqueda Miguel

package com.example.p4_miami_gabriel_jorge

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- PANTALLA DE BIENVENIDA ---
@Composable
fun WelcomeScreen(onEnter: () -> Unit) {
        Box(
                modifier =
                        Modifier.fillMaxSize()
                                .background(
                                        MaterialTheme.colorScheme.background
                                ), // Usa #F8F9FA o #0A0D12
                contentAlignment = Alignment.Center
        ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                                text = "MIAMI APP",
                                style = MaterialTheme.typography.displayLarge,
                                color = MaterialTheme.colorScheme.primary // #00B4D8
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                                onClick = onEnter,
                                colors =
                                        ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.secondary
                                        )
                        ) { Text("Entrar", color = Color.White) }
                }
        }
}

// --- PANTALLA PRINCIPAL ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MiamiViewModel, onItemClick: (Int) -> Unit) {
        // Observamos la categoría actual desde el ViewModel
        val currentCategory by viewModel.selectedCategory.collectAsState()
        val filteredItems = viewModel.getFilteredItems(currentCategory)

        Scaffold(
                topBar = {
                        // Usamos CenterAlignedTopAppBar para un look más moderno
                        CenterAlignedTopAppBar(
                                title = {
                                        Text(
                                                text = "Miami ${currentCategory.title}",
                                                fontWeight = FontWeight.Bold,
                                                style = MaterialTheme.typography.headlineSmall
                                        )
                                },
                                colors =
                                        TopAppBarDefaults.centerAlignedTopAppBarColors(
                                                containerColor =
                                                        MaterialTheme.colorScheme.secondary,
                                                titleContentColor = Color.White
                                        )
                        )
                }
        ) { innerPadding ->
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        // El resto del código del Grid
                        LazyVerticalStaggeredGrid(
                                columns = StaggeredGridCells.Fixed(2),
                                modifier = Modifier.fillMaxSize(),
                                contentPadding =
                                        PaddingValues(
                                                start = 12.dp,
                                                end = 12.dp,
                                                top = 12.dp,
                                                bottom = 100.dp // Espacio para el nav bar flotante
                                        ),
                                verticalItemSpacing = 8.dp,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                                items(
                                        items = filteredItems,
                                        key = { it.id },
                                        span = { item ->
                                                if (item.size == CardSize.WIDE)
                                                        StaggeredGridItemSpan.FullLine
                                                else StaggeredGridItemSpan.SingleLane
                                        }
                                ) { item ->
                                        MiamiCard(item = item, onClick = { onItemClick(item.id) })
                                }
                        }

                        // Floating Navigation Bar
                        Surface(
                                modifier =
                                        Modifier.align(Alignment.BottomCenter)
                                                .padding(horizontal = 24.dp)
                                                .padding(bottom = 24.dp),
                                shape = RoundedCornerShape(28.dp),
                                shadowElevation = 8.dp,
                                tonalElevation = 0.dp,
                                color = MaterialTheme.colorScheme.surface
                        ) {
                                NavigationBar(
                                        modifier = Modifier.height(64.dp),
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
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .onBackground,
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
                                                                                                        0.6f
                                                                                        )
                                                                ),
                                                        icon = {
                                                                Icon(
                                                                        modifier =
                                                                                Modifier.size(
                                                                                        32.dp
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
                                                                                category.title
                                                                )
                                                        }
                                                )
                                        }
                                }
                        }
                }
        }
}

@Composable
fun MiamiCard(item: MiamiItem, onClick: () -> Unit) {
        Card(
                modifier =
                        Modifier.fillMaxWidth()
                                .aspectRatio(item.aspectRatio)
                                .padding(4.dp)
                                .clickable { onClick() },
                shape = RoundedCornerShape(16.dp),
                colors =
                        CardDefaults.cardColors(
                                containerColor =
                                        MaterialTheme.colorScheme.surface // #FFFFFF o #161B22
                        ),
                elevation = CardDefaults.cardElevation(4.dp)
        ) {
                Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                                painter = painterResource(id = item.imageRes),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                        )

                        // Degradado dinámico
                        Box(
                                modifier =
                                        Modifier.fillMaxSize()
                                                .background(
                                                        Brush.verticalGradient(
                                                                colors =
                                                                        listOf(
                                                                                Color.Transparent,
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .background
                                                                                        .copy(
                                                                                                alpha =
                                                                                                        0.9f
                                                                                        )
                                                                        ),
                                                                startY = 150f
                                                        )
                                                )
                        )

                        Column(modifier = Modifier.align(Alignment.BottomStart).padding(12.dp)) {
                                Text(
                                        text = item.name,
                                        style =
                                                MaterialTheme.typography.headlineMedium.copy(
                                                        fontSize = 18.sp
                                                ),
                                        color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                        text = item.category.title,
                                        style =
                                                MaterialTheme.typography
                                                        .bodyMedium, // Fuente Synonym
                                        color = MaterialTheme.colorScheme.primary
                                )
                        }
                }
        }
}

// --- PANTALLA DE DETALLE ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(itemId: Int, viewModel: MiamiViewModel, onBack: () -> Unit) {
        val item = viewModel.getItemById(itemId) ?: return

        Scaffold(
                topBar = {
                        TopAppBar(
                                title = {
                                        Text(
                                                item.name,
                                                style = MaterialTheme.typography.headlineSmall
                                        )
                                },
                                navigationIcon = {
                                        IconButton(onClick = onBack) {
                                                Icon(
                                                        Icons.Default.ArrowBack,
                                                        contentDescription = null,
                                                        tint = MaterialTheme.colorScheme.primary
                                                )
                                        }
                                },
                                colors =
                                        TopAppBarDefaults.topAppBarColors(
                                                containerColor = MaterialTheme.colorScheme.surface
                                        )
                        )
                }
        ) { padding ->
                Column(
                        modifier =
                                Modifier.padding(padding)
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.background)
                ) {
                        Image(
                                painter = painterResource(id = item.imageRes),
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth().height(300.dp),
                                contentScale = ContentScale.Crop
                        )

                        Column(modifier = Modifier.padding(20.dp)) {
                                Text(
                                        text = item.name,
                                        style =
                                                MaterialTheme.typography.headlineLarge.copy(
                                                        fontSize = 28.sp
                                                ),
                                        color = MaterialTheme.colorScheme.secondary
                                )

                                Surface(
                                        color =
                                                MaterialTheme.colorScheme.secondary.copy(
                                                        alpha = 0.1f
                                                ),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.padding(vertical = 8.dp)
                                ) {
                                        Text(
                                                text = item.category.title,
                                                modifier =
                                                        Modifier.padding(
                                                                horizontal = 8.dp,
                                                                vertical = 4.dp
                                                        ),
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.secondary
                                        )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                        text = item.description,
                                        style =
                                                MaterialTheme.typography
                                                        .bodyLarge, // Fuente Synonym
                                        color =
                                                MaterialTheme.colorScheme.onBackground.copy(
                                                        alpha = 0.8f
                                                )
                                )
                        }
                }
        }
}
