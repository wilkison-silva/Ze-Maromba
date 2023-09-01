package br.com.zemaromba.data.sources.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import br.com.zemaromba.data.model.SetEntity
import br.com.zemaromba.data.model.relations.SetWithExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface SetDao {

    @Insert
    suspend fun insert(setEntity: SetEntity): Long

    @Update
    suspend fun update(setEntity: SetEntity)

    @Query("UPDATE `Set` SET completed = :completed WHERE `Set`.id = :setId")
    suspend fun completeSet(setId: Long, completed: Boolean)

    @Query("DELETE FROM `Set` WHERE `Set`.id = :setId")
    suspend fun deleteById(setId: Long): Int

    @Transaction
    @Query("SELECT * FROM Exercise " +
            "JOIN `Set` ON `Set`.exercise_id = Exercise.exercise_id " +
            "WHERE `Set`.training_id = :trainingId")
    fun getSetsWithExerciseByTrainingId(trainingId: Long): Flow<List<SetWithExercise>>

}