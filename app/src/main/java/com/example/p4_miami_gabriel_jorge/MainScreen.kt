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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MiamiViewModel, onItemClick: (Int) -> Unit) {
        val currentCategory by viewModel.selectedCategory.collectAsState()
        val filteredItems by viewModel.filteredItems.collectAsState()

        Scaffold(
                topBar = {
                        CenterAlignedTopAppBar(
                                title = {
                                        Text(
                                                text = "MIAMI ${currentCategory.title}",
                                                fontWeight = FontWeight.Bold,
                                                style = MaterialTheme.typography.headlineSmall
                                        )
                                },
                                colors =
                                        TopAppBarDefaults.centerAlignedTopAppBarColors(
                                                containerColor = Color.Transparent,
                                                titleContentColor =
                                                        MaterialTheme.colorScheme.primary
                                        )
                        )
                },
                containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
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
                                                if (item.sponsored) StaggeredGridItemSpan.FullLine
                                                else StaggeredGridItemSpan.SingleLane
                                        }
                                ) { item ->
                                        MiamiCard(item = item, onClick = { onItemClick(item.id) })
                                }
                        }

                        // Gradient behind Navbar
                        Box(
                                modifier =
                                        Modifier.align(Alignment.BottomCenter)
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

                        Surface(
                                modifier =
                                        Modifier.align(Alignment.BottomCenter)
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
                                                Modifier.height(64.dp).padding(horizontal = 8.dp),
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
                                                                                Color.Transparent, // Hide default indicator
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
                                                                                                Modifier.height(
                                                                                                                40.dp
                                                                                                        )
                                                                                                        .width(
                                                                                                                64.dp
                                                                                                        ) // Customize width here
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
                                                                                        ), // Reduced icon size slightly to fit better
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
        val cardShape = RoundedCornerShape(20.dp)

        Card(
                modifier =
                        Modifier.fillMaxWidth()
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
                        AsyncImage(
                                model = item.imageRes,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                        )

                        Box(
                                modifier =
                                        Modifier.fillMaxSize()
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

                        if (item.sponsored) {
                                Box(
                                        modifier =
                                                Modifier.align(Alignment.TopStart)
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
                                                        text = "Promocionado",
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

                        Column(
                                modifier =
                                        Modifier.align(Alignment.BottomStart)
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
