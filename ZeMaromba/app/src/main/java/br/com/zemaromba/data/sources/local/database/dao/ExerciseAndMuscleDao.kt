package br.com.zemaromba.data.sources.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import br.com.zemaromba.data.model.ExerciseAndMuscleGroupEntity

@Dao
interface ExerciseAndMuscleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseAndMuscleRef: ExerciseAndMuscleGroupEntity)

}