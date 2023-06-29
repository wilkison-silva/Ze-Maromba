package br.com.zemaromba.core_data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import br.com.zemaromba.core_data.model.TrainingPlanEntity
import br.com.zemaromba.core_data.model.relations.TrainingPlanWithTrainings
import br.com.zemaromba.core_domain.model.TrainingPlan
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingPlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainingPlanEntity: TrainingPlanEntity): Long

    @Delete
    suspend fun delete(trainingPlanEntity: TrainingPlanEntity)

    @Update
    suspend fun update(trainingPlanEntity: TrainingPlanEntity)

    @Transaction
    @Query("SELECT * FROM TrainingPlan")
    fun getTrainingPlanWithTrainings(): Flow<List<TrainingPlanWithTrainings>>

    @Query("SELECT * FROM TrainingPlan")
    fun getTrainingPlan(): Flow<List<TrainingPlanEntity>>

}