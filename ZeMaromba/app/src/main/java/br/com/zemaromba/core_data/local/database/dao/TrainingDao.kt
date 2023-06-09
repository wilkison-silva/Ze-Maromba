package br.com.zemaromba.core_data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.zemaromba.core_data.model.TrainingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainingEntity: TrainingEntity): Long

    @Delete
    suspend fun delete(trainingEntity: TrainingEntity)

    @Update
    suspend fun update(trainingEntity: TrainingEntity)

    @Query("SELECT * FROM Training")
    fun getAll(): Flow<List<TrainingEntity>>

}