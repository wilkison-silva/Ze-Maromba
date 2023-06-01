package br.com.zemaromba.core_data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import br.com.zemaromba.core_data.model.MuscleGroupData

@Dao
interface MuscleGroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(muscleGroupData: MuscleGroupData): Long

}