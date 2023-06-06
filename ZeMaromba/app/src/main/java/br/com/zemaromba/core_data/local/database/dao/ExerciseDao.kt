package br.com.zemaromba.core_data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import br.com.zemaromba.core_data.model.ExerciseEntity
import br.com.zemaromba.core_data.model.relations.ExercisesWithMuscles
import br.com.zemaromba.core_data.model.relations.SetWithExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseEntity: ExerciseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exerciseEntityList: List<ExerciseEntity>): List<Long>

    @Delete
    suspend fun delete(exerciseEntity: ExerciseEntity)

    @Update
    suspend fun update(exerciseEntity: ExerciseEntity)

    @Query("SELECT * FROM Exercise")
    fun getAll(): Flow<List<ExerciseEntity>>

    @Transaction
    @Query("SELECT * FROM Exercise")
    fun getExercisesWithMuscleGroups(): Flow<List<ExercisesWithMuscles>>

    @Transaction
    @Query("SELECT * FROM Exercise")
    fun getSetWithExercise(): Flow<List<SetWithExercise>>

}