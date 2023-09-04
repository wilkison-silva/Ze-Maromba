package br.com.zemaromba.data.sources.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.zemaromba.data.model.ExerciseAndMuscleGroupEntity
import br.com.zemaromba.data.model.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert
    suspend fun insert(exerciseEntity: ExerciseEntity): Long

    @Update
    suspend fun update(exerciseEntity: ExerciseEntity)

    @Query("DELETE FROM Exercise WHERE Exercise.exercise_id = :exerciseId")
    suspend fun deleteById(exerciseId: Long): Int

    @Query("UPDATE Exercise SET favorite = :favorite WHERE Exercise.exercise_id = :exerciseId")
    suspend fun updateFavoriteField(exerciseId: Long, favorite: Boolean)

    @Query("SELECT * FROM Exercise " +
            "JOIN ExerciseAndMuscleGroup ON Exercise.exercise_id = ExerciseAndMuscleGroup.exercise_id " +
            "ORDER BY Exercise.name")
    fun getExercisesWithMuscleGroups(): Flow<Map<ExerciseEntity, List<ExerciseAndMuscleGroupEntity>>>

    @Query("SELECT * FROM Exercise JOIN ExerciseAndMuscleGroup " +
            "ON Exercise.exercise_id = ExerciseAndMuscleGroup.exercise_id " +
            "WHERE Exercise.exercise_id = :exerciseId")
    suspend fun getExerciseWithMuscleGroups(exerciseId: Long): Map<ExerciseEntity, List<ExerciseAndMuscleGroupEntity>>

}