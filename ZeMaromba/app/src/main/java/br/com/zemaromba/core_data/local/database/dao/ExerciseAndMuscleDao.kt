package br.com.zemaromba.core_data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.zemaromba.core_data.model.ExerciseAndMuscleGroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseAndMuscleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseAndMuscleRef: ExerciseAndMuscleGroupEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exerciseAndMuscleRefList: List<ExerciseAndMuscleGroupEntity>)

    @Query("SELECT * FROM ExerciseAndMuscleGroup WHERE exercise_id = :exerciseId")
    fun getMusclesByExerciseId(exerciseId: Long): Flow<List<ExerciseAndMuscleGroupEntity>>

}