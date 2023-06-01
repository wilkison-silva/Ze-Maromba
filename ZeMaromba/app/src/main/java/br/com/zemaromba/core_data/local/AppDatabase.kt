package br.com.zemaromba.core_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.zemaromba.core_data.local.dao.ExerciseDao
import br.com.zemaromba.core_data.local.dao.MuscleGroupDao
import br.com.zemaromba.core_data.model.ExerciseData
import br.com.zemaromba.core_data.model.MuscleGroupData
import br.com.zemaromba.core_data.model.SetData
import br.com.zemaromba.core_data.model.TrainingData

@Database(
    entities = [
        ExerciseData::class,
        MuscleGroupData::class,
        SetData::class,
        TrainingData::class
    ],
    version = AppDatabase.databaseVersion,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val exerciseDao: ExerciseDao
    abstract val muscleGroupDao: MuscleGroupDao

    companion object {
        const val databaseVersion = 1
        const val databaseName = "ze_maromba_app_database"
    }

}