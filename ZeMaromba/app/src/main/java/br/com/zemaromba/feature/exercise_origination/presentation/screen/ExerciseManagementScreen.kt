package br.com.zemaromba.feature.exercise_origination.presentation.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.zemaromba.R
import br.com.zemaromba.core_ui.ui.theme.ZeMarombaTheme


data class MuscleGroupCheckBox(
    val name: String,
    var isSelected: Boolean
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseManagementScreen(
    name: String,
    onChangeName: (newName: String) -> Unit,
    muscleGroups: List<MuscleGroupCheckBox>,
    onMuscleGroupSelection: (id: Int, isSelected: Boolean) -> Unit,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                        onClick = {
                            onNavigateBack()
                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                },
                title = {
                    Text(
                        text = "Exercício",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 20.sp
                    )
                },
                actions = {
                    IconButton(
                        modifier = Modifier,
                        onClick = {

                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                    IconButton(
                        modifier = Modifier,
                        onClick = {

                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_done),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                }
            )
        }
    ) { contentPadding ->
        val verticalScrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(paddingValues = contentPadding)
                .fillMaxWidth()
                .verticalScroll(verticalScrollState)
        ) {
            val focusManager = LocalFocusManager.current
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                value = name,
                onValueChange = {
                    onChangeName(it)
                },
                label = {
                    Text(text = "Nome")
                },
                placeholder = {
                    Text(text = "Ex: Supino inclinado")
                },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = "Grupos musculares:"
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                tonalElevation = 5.dp,
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 10.dp),
                ) {
                    val itemCount = if (muscleGroups.size % 2 == 0) {
                        muscleGroups.size / 2
                    } else {
                        muscleGroups.size / 2 + 1
                    }
                    repeat(itemCount) { rowIndex ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Checkbox(
                                    checked = muscleGroups[rowIndex * 2].isSelected,
                                    onCheckedChange = {
                                        onMuscleGroupSelection(rowIndex * 2, it)
                                    }
                                )
                                Text(
                                    text = muscleGroups[rowIndex * 2].name,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            if (muscleGroups.size >= ((rowIndex * 2) + 2)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Checkbox(
                                        checked = muscleGroups[rowIndex * 2 + 1].isSelected,
                                        onCheckedChange = {
                                            onMuscleGroupSelection(rowIndex * 2 + 1, it)
                                        }
                                    )
                                    Text(
                                        text = muscleGroups[rowIndex * 2 + 1].name,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    name = "pixel_5",
    device = "spec:parent=pixel_5",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "pixel_5",
    device = "spec:parent=pixel_5",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Nexus 4",
    device = "id:Nexus 4",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Nexus 4",
    device = "id:Nexus 4",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun ExercisesManagementScreenPreview() {
    val name = remember {
        mutableStateOf("")
    }
    val muscleGroups = remember {
        mutableStateOf(
            listOf(
                MuscleGroupCheckBox(
                    name = "Peitoral",
                    isSelected = false
                ),
                MuscleGroupCheckBox(
                    name = "Dorsal",
                    isSelected = false
                ),
                MuscleGroupCheckBox(
                    name = "Trapézio",
                    isSelected = false
                ),
                MuscleGroupCheckBox(
                    name = "Bíceps",
                    isSelected = false
                ),
                MuscleGroupCheckBox(
                    name = "Antebraço",
                    isSelected = false
                ),
                MuscleGroupCheckBox(
                    name = "Quadríceps",
                    isSelected = false
                ),
                MuscleGroupCheckBox(
                    name = "Abdomen",
                    isSelected = false
                ),
                MuscleGroupCheckBox(
                    name = "Posteriores",
                    isSelected = false
                ),
                MuscleGroupCheckBox(
                    name = "Adultores",
                    isSelected = false
                ),
                MuscleGroupCheckBox(
                    name = "Abdutores",
                    isSelected = false
                ),
                MuscleGroupCheckBox(
                    name = "Glúteos",
                    isSelected = false
                ),
                MuscleGroupCheckBox(
                    name = "Panturrilhas",
                    isSelected = false
                ),
                MuscleGroupCheckBox(
                    name = "Lombar",
                    isSelected = false
                ),
//                MuscleGroupCheckBox(
//                    name = "Abdomen",
//                    isSelected = false
//                ),
            )
        )
    }
    ZeMarombaTheme {
        ExerciseManagementScreen(
            name = name.value,
            muscleGroups = muscleGroups.value,
            onNavigateBack = {

            },
            onChangeName = {
                name.value = it
            },
            onMuscleGroupSelection = { id, isSelected ->
                muscleGroups.value =
                    muscleGroups.value
                        .toMutableList()
                        .apply {
                            this[id] = this[id].copy(isSelected = isSelected)
                        }
            }
        )
    }
}