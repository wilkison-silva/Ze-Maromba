package br.com.zemaromba.presentation.training_plan.screen.state

import br.com.zemaromba.presentation.model.SetView

data class SetListState(
    val setListView: List<SetView> = emptyList(),
    val trainingName: String = "",
    val showListOptionsBottomSheet: Boolean = false,
    val selectedSet: SetView? = null,
    val isLoadingTraining: Boolean = true,
    val isRetrievingSets: Boolean = true
) {
    val showMessage: Boolean = setListView.isEmpty()
}