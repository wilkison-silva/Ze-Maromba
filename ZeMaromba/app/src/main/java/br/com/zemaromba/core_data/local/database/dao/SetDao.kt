package br.com.zemaromba.core_data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import br.com.zemaromba.core_data.model.SetEntity
import br.com.zemaromba.core_data.model.relations.SetWithExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface SetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(setEntity: SetEntity): Long

    @Delete
    suspend fun delete(setEntity: SetEntity)

    @Update
    suspend fun update(setEntity: SetEntity)

    @Transaction
    @Query("SELECT * FROM Exercise " +
            "JOIN `Set` ON `Set`.exercise_id = Exercise.exercise_id " +
            "WHERE `Set`.training_id = :trainingId")
    fun getSetsWithExerciseByTrainingId(trainingId: Long): Flow<List<SetWithExercise>>

}