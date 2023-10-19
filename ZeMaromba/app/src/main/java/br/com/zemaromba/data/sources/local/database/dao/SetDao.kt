package br.com.zemaromba.data.sources.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import br.com.zemaromba.data.model.SetEntity
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

    @Query("SELECT * FROM `Set` WHERE `Set`.training_id = :trainingId")
    fun getSetByTrainingId(trainingId: Long): Flow<List<SetEntity>>

    @Query("SELECT * FROM `Set` WHERE `Set`.id = :setId")
    suspend fun getSet(setId: Long): List<SetEntity>

}