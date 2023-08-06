package br.com.zemaromba.core_data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import br.com.zemaromba.core_data.model.ExerciseAndMuscleGroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseAndMuscleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseAndMuscleRef: ExerciseAndMuscleGroupEntity)

}