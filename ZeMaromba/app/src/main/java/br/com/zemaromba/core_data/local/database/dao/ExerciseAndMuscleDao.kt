package br.com.zemaromba.core_data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import br.com.zemaromba.core_data.model.ExerciseAndMuscleGroupEntity

@Dao
interface ExerciseAndMuscleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseAndMuscleRef: ExerciseAndMuscleGroupEntity)

}