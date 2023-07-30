package br.com.zemaromba.core_data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import br.com.zemaromba.core_data.model.ExerciseAndMuscleGroupEntity
import br.com.zemaromba.core_data.model.ExerciseEntity
import br.com.zemaromba.core_data.model.relations.SetWithExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Upsert
    suspend fun insert(exerciseEntity: ExerciseEntity): Long

    @Delete
    suspend fun delete(exerciseEntity: ExerciseEntity)

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
    fun getExerciseWithMuscleGroups(exerciseId: Long): Map<ExerciseEntity, List<ExerciseAndMuscleGroupEntity>>

    @Transaction
    @Query("SELECT * FROM Exercise")
    fun getSetWithExercise(): Flow<List<SetWithExercise>>

}