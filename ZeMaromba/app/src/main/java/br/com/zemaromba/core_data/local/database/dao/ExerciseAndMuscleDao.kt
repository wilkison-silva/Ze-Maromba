package br.com.zemaromba.core_data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.zemaromba.core_data.model.cross_references.ExerciseAndMuscleGroupCrossRefEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseAndMuscleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseAndMuscleRef: ExerciseAndMuscleGroupCrossRefEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exerciseAndMuscleRefList: List<ExerciseAndMuscleGroupCrossRefEntity>)

    @Query("SELECT * FROM ExerciseAndMuscleGroup WHERE exercise_id = :exerciseId")
    fun getMusclesByExerciseId(exerciseId: Long): Flow<List<ExerciseAndMuscleGroupCrossRefEntity>>

}