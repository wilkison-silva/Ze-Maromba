package br.com.zemaromba.core_data.di

import android.content.Context
import androidx.room.Room
import br.com.zemaromba.BuildConfig
import br.com.zemaromba.core_data.local.database.AppDatabase
import br.com.zemaromba.core_data.local.database.dao.ExerciseAndMuscleDao
import br.com.zemaromba.core_data.local.database.dao.ExerciseDao
import br.com.zemaromba.core_data.local.database.dao.SetDao
import br.com.zemaromba.core_data.local.database.dao.TrainingDao
import br.com.zemaromba.core_data.local.database.dao.TrainingPlanDao
import br.com.zemaromba.core_data.local.datastore.UserDataStoreImpl
import br.com.zemaromba.core_domain.datastore.UserDataStore
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
            BuildConfig.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideExerciseDao(appDatabase: AppDatabase): ExerciseDao {
        return appDatabase.exerciseDao
    }

    @Provides
    @Singleton
    fun provideExerciseAndMuscleDao(appDatabase: AppDatabase): ExerciseAndMuscleDao {
        return appDatabase.exerciseAndMuscleDao
    }

    @Provides
    @Singleton
    fun provideSetDao(appDatabase: AppDatabase): SetDao {
        return appDatabase.setDao
    }

    @Provides
    @Singleton
    fun provideTrainingDao(appDatabase: AppDatabase): TrainingDao {
        return appDatabase.trainingDao
    }

    @Provides
    @Singleton
    fun provideTrainingPlanDao(appDatabase: AppDatabase): TrainingPlanDao {
        return appDatabase.trainingPlanDao
    }

    @Provides
    @Singleton
    fun provideUserDataStore(@ApplicationContext context: Context): UserDataStore {
        return UserDataStoreImpl(context = context)
    }

}