package br.com.zemaromba.core_data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.zemaromba.core_data.model.ExerciseData
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseData: ExerciseData): Long

    @Delete
    suspend fun delete(exerciseData: ExerciseData)

    @Query("SELECT * FROM exercises")
    fun getAll(): Flow<List<ExerciseData>>

}