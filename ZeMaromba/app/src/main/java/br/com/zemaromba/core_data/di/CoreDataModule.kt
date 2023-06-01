package br.com.zemaromba.core_data.di

import android.content.Context
import androidx.room.Room
import br.com.zemaromba.core_data.local.AppDatabase
import br.com.zemaromba.core_data.local.dao.ExerciseDao
import br.com.zemaromba.core_data.local.dao.MuscleGroupDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreDataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.databaseName
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideExerciseDao(appDatabase: AppDatabase) : ExerciseDao {
        return appDatabase.exerciseDao
    }

    @Provides
    @Singleton
    fun provideMuscleGroupDao(appDatabase: AppDatabase) : MuscleGroupDao {
        return appDatabase.muscleGroupDao
    }

}