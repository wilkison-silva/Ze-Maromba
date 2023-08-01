package br.com.zemaromba.core_data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import br.com.zemaromba.core_data.model.TrainingEntity

@Dao
interface TrainingDao {

    @Upsert
    suspend fun insert(trainingEntity: TrainingEntity): Long

    @Query("DELETE FROM Training WHERE Training.training_id = :trainingId")
    suspend fun deleteById(trainingId: Long): Int

    @Query("SELECT * FROM Training WHERE Training.training_id = :trainingId")
    suspend fun getTrainingById(trainingId: Long): TrainingEntity

}