package br.com.zemaromba.core_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.zemaromba.core_data.local.dao.ExerciseDao
import br.com.zemaromba.core_data.local.dao.MuscleGroupDao
import br.com.zemaromba.core_data.model.ExerciseEntity
import br.com.zemaromba.core_data.model.MuscleGroupEntity
import br.com.zemaromba.core_data.model.SetEntity
import br.com.zemaromba.core_data.model.TrainingEntity
import br.com.zemaromba.core_data.model.cross_references.ExerciseAndMuscleGroupCrossRefEntity

@Database(
    entities = [
        ExerciseEntity::class,
        MuscleGroupEntity::class,
        SetEntity::class,
        TrainingEntity::class,
        ExerciseAndMuscleGroupCrossRefEntity::class
    ],
    version = AppDatabase.databaseVersion,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val exerciseDao: ExerciseDao
    abstract val muscleGroupDao: MuscleGroupDao

    companion object {
        const val databaseVersion = 1
        const val databaseName = "ze_maromba_app_database"
    }

}