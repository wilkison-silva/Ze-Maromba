package br.com.zemaromba.presentation.components.chips

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.domain.model.ExerciseFilter
import br.com.zemaromba.presentation.core_ui.ui.theme.Spacing
import br.com.zemaromba.presentation.exercises.viewmodel.ExerciseFilterChip

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterChipsGroup(
    modifier: Modifier,
    exerciseFilters: List<ExerciseFilterChip>,
    onSelected: (chipType: ExerciseFilter) -> Unit,
    surfaceColor: Color = MaterialTheme.colorScheme.surface
) {
    FlowRow(
        modifier = modifier.background(color = surfaceColor),
        horizontalArrangement = Arrangement.spacedBy(Spacing.space_8dp)
    ) {
        repeat(exerciseFilters.size) { index ->
            FilterChip(
                label = {
                    Text(
                        text = stringResource(id = exerciseFilters[index].type.nameRes),
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                selected = exerciseFilters[index].isSelected,
                onClick = {
                    onSelected(exerciseFilters[index].type)
                },
                leadingIcon = {
                    if (exerciseFilters[index].isSelected) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = null,
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
    val exerciseFilters = remember {
        mutableStateOf(
            listOf(
                ExerciseFilterChip(type = ExerciseFilter.ALL, isSelected = true),
                ExerciseFilterChip(type = ExerciseFilter.MUSCLE_GROUP, isSelected = false),
                ExerciseFilterChip(type = ExerciseFilter.FAVORITE, isSelected = false)
            ),
        )
    }
    ZeMarombaTheme {
        FilterChipsGroup(
            modifier = Modifier.fillMaxWidth(),
            exerciseFilters = exerciseFilters.value,
            onSelected = {
                exerciseFilters.value = exerciseFilters.value.toMutableList()
            }
        )
    }
}