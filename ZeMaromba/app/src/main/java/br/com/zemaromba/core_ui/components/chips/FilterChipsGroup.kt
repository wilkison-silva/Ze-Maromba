package br.com.zemaromba.core_ui.components.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.zemaromba.core_ui.ui.theme.ZeMarombaTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterChipsGroup(
    modifier: Modifier,
    chips: List<String>,
    selectedChips: List<Boolean>,
    onSelected: (chipId: Int) -> Unit,
    surfaceColor: Color = MaterialTheme.colorScheme.surface
) {
    FlowRow(
        modifier = modifier.background(color = surfaceColor),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(chips.size) {
            FilterChip(
                label = {
                    Text(
                        text = chips[it],
                        fontSize = 14.sp
                    )
                },
                selected = selectedChips[it],
                onClick = {
                    onSelected(it)
                },
                leadingIcon = {
                    if (selectedChips[it]) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "",
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color.Transparent,
                    labelColor = MaterialTheme.colorScheme.onSurface,
                    iconColor = MaterialTheme.colorScheme.onSurface,
                    selectedContainerColor = MaterialTheme.colorScheme.secondary,
                    selectedLabelColor = MaterialTheme.colorScheme.onSecondary,
                    selectedLeadingIconColor = MaterialTheme.colorScheme.onSecondary
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilterChipsGroupPreview() {
    val chipsTitle = remember {
        mutableStateOf(listOf("Todos", "Grupo muscular", "Favoritos", "Melhores", "Personalizados"))
    }
    val selectedChips = remember {
        mutableStateOf(listOf(true, false, false, false, false))
    }
    ZeMarombaTheme {
        FilterChipsGroup(
            modifier = Modifier.fillMaxWidth(),
            chips = chipsTitle.value,
            selectedChips = selectedChips.value,
            onSelected = {
                selectedChips.value =
                    selectedChips
                        .value
                        .map { false }
                        .toMutableList()
                        .apply {
                            this[it] = !this[it]
                        }
            }
        )
    }
}