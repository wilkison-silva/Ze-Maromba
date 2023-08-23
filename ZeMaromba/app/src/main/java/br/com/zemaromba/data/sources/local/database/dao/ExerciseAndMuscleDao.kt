package br.com.zemaromba.data.sources.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.zemaromba.data.model.ExerciseAndMuscleGroupEntity

@Dao
interface ExerciseAndMuscleDao {

    @Insert
    suspend fun insert(exerciseAndMuscleRef: ExerciseAndMuscleGroupEntity)

    @Query("DELETE FROM ExerciseAndMuscleGroup WHERE ExerciseAndMuscleGroup.exercise_id = :exerciseId")
    suspend fun deleteByExerciseId(exerciseId: Long)

}