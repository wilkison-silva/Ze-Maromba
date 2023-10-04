package br.com.zemaromba.data.sources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.zemaromba.data.sources.local.database.dao.ExerciseDao
import br.com.zemaromba.data.sources.local.database.dao.SetDao
import br.com.zemaromba.data.sources.local.database.dao.TrainingDao
import br.com.zemaromba.data.sources.local.database.dao.TrainingPlanDao
import br.com.zemaromba.data.model.ExerciseEntity
import br.com.zemaromba.data.model.SetEntity
import br.com.zemaromba.data.model.TrainingEntity
import br.com.zemaromba.data.model.TrainingPlanEntity
import br.com.zemaromba.data.model.ExerciseAndMuscleGroupEntity

@Database(
    entities = [
        ExerciseEntity::class,
        SetEntity::class,
        TrainingEntity::class,
        ExerciseAndMuscleGroupEntity::class,
        TrainingPlanEntity::class
    ],
    version = AppDatabase.databaseVersion,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val exerciseDao: ExerciseDao
    abstract val setDao: SetDao
    abstract val trainingDao: TrainingDao
    abstract val trainingPlanDao: TrainingPlanDao

    companion object {
        const val databaseVersion = 1
    }

}