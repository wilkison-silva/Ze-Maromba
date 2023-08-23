package br.com.zemaromba.data.sources.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.zemaromba.data.model.TrainingEntity

@Dao
interface TrainingDao {

    @Insert
    suspend fun insert(trainingEntity: TrainingEntity): Long

    @Update
    suspend fun update(trainingEntity: TrainingEntity)

    @Query("DELETE FROM Training WHERE Training.training_id = :trainingId")
    suspend fun deleteById(trainingId: Long): Int

    @Query("SELECT * FROM Training WHERE Training.training_id = :trainingId")
    suspend fun getTrainingById(trainingId: Long): TrainingEntity

}