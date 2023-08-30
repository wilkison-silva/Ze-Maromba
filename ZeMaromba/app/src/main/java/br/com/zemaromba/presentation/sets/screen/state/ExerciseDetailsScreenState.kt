package br.com.zemaromba.presentation.sets.screen.state

data class ExerciseDetailsScreenState(
    val seriesValue: String = "",
    val repetitionsValue: String = "",
    val weightValue: String = "",
    val restingTimeValue: String = "",
    val fillDetailsLater: Boolean = false
) {
    val isAllTextInputsNotEmpty = seriesValue.isNotBlank() &&
            repetitionsValue.isNotBlank() &&
            weightValue.isNotBlank() &&
            restingTimeValue.isNotBlank()
}