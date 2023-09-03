package br.com.zemaromba.presentation.components.bottom_sheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.zemaromba.R
import br.com.zemaromba.presentation.components.button.PrimaryButton
import br.com.zemaromba.presentation.core_ui.ui.theme.Dimens
import br.com.zemaromba.presentation.exercises.screen.MuscleGroupSelector
import br.com.zemaromba.presentation.exercises.screen.state.MuscleGroupCheckBoxState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MuscleGroupSelectorBottomSheet(
    onApplySelectedMuscleGroups: () -> Unit,
    onMuscleGroupSelection: (index: Int, isSelected: Boolean) -> Unit,
    muscleGroupCheckBoxStates: List<MuscleGroupCheckBoxState>
) {
    val skipPartiallyExpanded = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded.value
    )

    ModalBottomSheet(
        onDismissRequest = {
            onApplySelectedMuscleGroups()
        },
        sheetState = bottomSheetState,
    ) {
        MuscleGroupSelector(
            modifier = Modifier
                .padding(all = Dimens.Space.space_20dp)
                .fillMaxWidth(),
            muscleGroupCheckBoxStateList = muscleGroupCheckBoxStates,
            onMuscleGroupSelected = { index, isSelected ->
                onMuscleGroupSelection(index, isSelected)
            }
        )
        PrimaryButton(
            modifier = Modifier
                .padding(
                    start = Dimens.Space.space_20dp,
                    end = Dimens.Space.space_20dp,
                    bottom = Dimens.Space.space_64dp,
                    top = Dimens.Space.space_16dp
                )
                .fillMaxWidth(),
            onClick = {
                scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        onApplySelectedMuscleGroups()
                    }
                }
            },
            title = stringResource(R.string.button_title_filter)
        )
    }
}