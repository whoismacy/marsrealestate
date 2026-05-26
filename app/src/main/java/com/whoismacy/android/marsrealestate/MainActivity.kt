package com.whoismacy.android.marsrealestate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.whoismacy.android.marsrealestate.data.model.Estate
import com.whoismacy.android.marsrealestate.ui.theme.MarsRealEstateTheme
import com.whoismacy.android.marsrealestate.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarsRealEstateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: EstateViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val state = uiState) {
        is UiState.Loading -> {
            FullScreenLoadingSpinner(modifier)
        }

        is UiState.Success -> {
            DisplayData(estates = state.data, modifier)
        }

        is UiState.Error -> {
            ErrorState(modifier = modifier, error = state.message)
        }
    }
}

@Composable
fun FullScreenLoadingSpinner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorState(
    error: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                "An Unexpected Error Occurred",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
            )
            Text(
                error,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
fun DisplayData(
    estates: List<Estate>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalItemSpacing = 12.dp,
        modifier = modifier,
    ) {
        items(estates) { item ->
            val rentalColor = if (item.type == "buy") Color.Green else Color.Magenta
            OutlinedCard(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f),
                shape = RoundedCornerShape(24.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(.8f),
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = item.imageUrl,
                        contentDescription = "Image of estate ${item.id}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        placeholder = ColorPainter(Color.LightGray),
                        error = ColorPainter(Color.Red),
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    Surface(
                        color = rentalColor.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(
                            text = item.type.uppercase(),
                            color = rentalColor,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold),
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text("Price:", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            formatToLocale(item.price),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                        )
                    }
                }
            }
        }
    }
}

fun formatToLocale(num: Int): String =
    NumberFormat
        .getNumberInstance(Locale.getDefault())
        .format(num)
