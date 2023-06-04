package br.com.zemaromba.core_data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import br.com.zemaromba.core_data.model.MuscleGroupEntity
import br.com.zemaromba.core_data.model.relations.MusclesWithExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface MuscleGroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(muscleGroupEntity: MuscleGroupEntity): Long

    @Delete
    suspend fun delete(muscleGroupEntity: MuscleGroupEntity)

    @Update
    suspend fun update(muscleGroupEntity: MuscleGroupEntity)

    @Query("SELECT * FROM MuscleGroup")
    fun getAll(): Flow<List<MuscleGroupEntity>>

    @Transaction
    @Query("SELECT * FROM MuscleGroup")
    fun getMuscleGroupsWithExercises(): Flow<List<MusclesWithExercises>>

}