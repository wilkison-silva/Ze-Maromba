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
    @ColumnInfo(name = "video_id")
    val videoId: String?,
    @ColumnInfo(name = "editable")
    val isEditable: Boolean,
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
            videoId = this.videoId,
            isEditable = this.isEditable
        )
    }
}