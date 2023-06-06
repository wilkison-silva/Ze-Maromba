package br.com.zemaromba.core_data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import br.com.zemaromba.core_data.model.ExerciseEntity
import br.com.zemaromba.core_data.model.cross_references.ExerciseAndMuscleGroupCrossRefEntity
import br.com.zemaromba.core_data.model.relations.ExercisesWithMuscles
import br.com.zemaromba.core_data.model.relations.SetWithExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseAndMuscleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseAndMuscleRef: ExerciseAndMuscleGroupCrossRefEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exerciseAndMuscleRefList: List<ExerciseAndMuscleGroupCrossRefEntity>)

}