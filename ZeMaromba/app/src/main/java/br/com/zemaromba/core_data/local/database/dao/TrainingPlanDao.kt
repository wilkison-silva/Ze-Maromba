package br.com.zemaromba.core_data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import br.com.zemaromba.core_data.model.TrainingPlanEntity
import br.com.zemaromba.core_data.model.relations.TrainingPlanWithTrainings
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingPlanDao {

    @Upsert
    suspend fun insert(trainingPlanEntity: TrainingPlanEntity): Long

    @Query("DELETE FROM TrainingPlan WHERE TrainingPlan.training_plan_id = :trainingPlanId")
    suspend fun deleteById(trainingPlanId: Long): Int

    @Transaction
    @Query("SELECT * FROM TrainingPlan WHERE TrainingPlan.training_plan_id = :trainingPlanId")
    fun getTrainingPlanWithTrainings(trainingPlanId: Long): Flow<TrainingPlanWithTrainings>

    @Query("SELECT * FROM TrainingPlan")
    fun getAllTrainingPlans(): Flow<List<TrainingPlanEntity>>

    @Query("SELECT * FROM TrainingPlan WHERE TrainingPlan.training_plan_id = :trainingPlanId")
    fun getTrainingPlanById(trainingPlanId: Long): Flow<TrainingPlanEntity>

}