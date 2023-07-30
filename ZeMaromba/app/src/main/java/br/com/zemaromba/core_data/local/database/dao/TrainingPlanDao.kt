package br.com.zemaromba.core_data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import br.com.zemaromba.core_data.model.TrainingPlanEntity
import br.com.zemaromba.core_data.model.relations.TrainingPlanWithTrainings
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingPlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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