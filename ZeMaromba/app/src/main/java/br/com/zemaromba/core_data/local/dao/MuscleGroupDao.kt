package br.com.zemaromba.core_data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import br.com.zemaromba.core_data.model.MuscleGroupEntity
import br.com.zemaromba.core_data.model.relations.MusclesWithExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface MuscleGroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(muscleGroupEntity: MuscleGroupEntity): Long

    @Query("SELECT * FROM MuscleGroup")
    fun getAll(): Flow<List<MuscleGroupEntity>>

    @Transaction
    @Query("SELECT * FROM MuscleGroup")
    fun getMuscleGroupsWithExercises(): Flow<List<MusclesWithExercises>>

}