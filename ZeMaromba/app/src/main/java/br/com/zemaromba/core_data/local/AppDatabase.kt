package br.com.zemaromba.core_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.zemaromba.core_data.local.dao.ExerciseDao
import br.com.zemaromba.core_data.local.dao.MuscleGroupDao
import br.com.zemaromba.core_data.model.ExerciseData
import br.com.zemaromba.core_data.model.MuscleGroupData

@Database(
    entities = [
        ExerciseData::class,
        MuscleGroupData::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract val exerciseDao: ExerciseDao
    abstract val muscleGroupDao: MuscleGroupDao

}