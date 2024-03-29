package br.com.zemaromba.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.zemaromba.domain.model.Exercise
import br.com.zemaromba.domain.model.MuscleGroup

@Entity(tableName = "Exercise")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "exercise_id")
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "favorite")
    val isFavorite: Boolean,
    @ColumnInfo(name = "url_video")
    val urlLink: String?,
    @ColumnInfo(name = "editable")
    val mayExclude: Boolean,
    @ColumnInfo(name = "native_from_app")
    val isNativeFromApp: Boolean,
) {
    fun toExercise(exercisesAndMuscleGroup: List<ExerciseAndMuscleGroupEntity>): Exercise {
        val muscleGroupList = mutableListOf<MuscleGroup>()
        exercisesAndMuscleGroup.forEach { exerciseIdAndMuscleGroupName ->
            MuscleGroup.values().find {
                it.name == exerciseIdAndMuscleGroupName.muscleName
            }?.let {
                muscleGroupList.add(it)
            }
        }
        return Exercise(
            id = this.id,
            name = this.name,
            isFavorite = this.isFavorite,
            muscleGroupList = muscleGroupList,
            urlLink = this.urlLink,
            mayExclude = this.mayExclude,
            isNativeFromApp = this.isNativeFromApp
        )
    }
}