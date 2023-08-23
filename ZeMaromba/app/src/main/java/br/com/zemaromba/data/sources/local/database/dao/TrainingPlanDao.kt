package br.com.zemaromba.data.sources.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import br.com.zemaromba.data.model.TrainingPlanEntity
import br.com.zemaromba.data.model.relations.TrainingPlanWithTrainings
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingPlanDao {

    @Insert
    suspend fun insert(trainingPlanEntity: TrainingPlanEntity): Long

    @Update
    suspend fun update(trainingPlanEntity: TrainingPlanEntity)

    @Query("DELETE FROM TrainingPlan WHERE TrainingPlan.training_plan_id = :trainingPlanId")
    suspend fun deleteById(trainingPlanId: Long): Int

    @Transaction
    @Query("SELECT * FROM TrainingPlan WHERE TrainingPlan.training_plan_id = :trainingPlanId")
    fun getTrainingPlanWithTrainings(trainingPlanId: Long): Flow<TrainingPlanWithTrainings>

    @Query("SELECT * FROM TrainingPlan")
    fun getAllTrainingPlans(): Flow<List<TrainingPlanEntity>>

    @Query("SELECT * FROM TrainingPlan WHERE TrainingPlan.training_plan_id = :trainingPlanId")
    suspend fun getTrainingPlanById(trainingPlanId: Long): TrainingPlanEntity

}