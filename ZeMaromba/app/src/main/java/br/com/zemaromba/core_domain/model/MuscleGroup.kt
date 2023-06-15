package br.com.zemaromba.core_domain.model

import br.com.zemaromba.R

enum class MuscleGroup(val nameRes: Int) {
    CHEST(nameRes = R.string.chest),
    DORSAL(nameRes = R.string.dorsal),
    DELTOID(nameRes = R.string.deltoides),
    TRAPEZIUS(nameRes = R.string.trapezius),
    BICEPS(nameRes = R.string.biceps),
    TRICEPS(nameRes = R.string.triceps),
    FOREARM(nameRes = R.string.forearm),
    QUADRICEPS(nameRes = R.string.quadriceps),
    HAMSTRINGS(nameRes = R.string.hamstrings),
    ADDUCTORS(nameRes = R.string.adductors),
    ABDUCTORS(nameRes = R.string.abductors),
    GLUTES(nameRes = R.string.glutes),
    CALVES(nameRes = R.string.calves),
    ABDOMEN(nameRes = R.string.abdomen),
    LUMBAR(nameRes = R.string.lumbar)
}