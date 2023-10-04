package br.com.zemaromba.data.di

import android.content.Context
import androidx.room.Room
import br.com.zemaromba.BuildConfig
import br.com.zemaromba.data.repository.ExercisesRepositoryImpl
import br.com.zemaromba.data.repository.SetRepositoryImpl
import br.com.zemaromba.data.repository.TrainingPlanRepositoryImpl
import br.com.zemaromba.data.repository.TrainingRepositoryImpl
import br.com.zemaromba.data.repository.UserRepositoryImpl
import br.com.zemaromba.data.sources.local.database.AppDatabase
import br.com.zemaromba.data.sources.local.database.dao.ExerciseDao
import br.com.zemaromba.data.sources.local.database.dao.SetDao
import br.com.zemaromba.data.sources.local.database.dao.TrainingDao
import br.com.zemaromba.data.sources.local.database.dao.TrainingPlanDao
import br.com.zemaromba.domain.repository.ExercisesRepository
import br.com.zemaromba.domain.repository.SetRepository
import br.com.zemaromba.domain.repository.TrainingPlanRepository
import br.com.zemaromba.domain.repository.TrainingRepository
import br.com.zemaromba.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            BuildConfig.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideExerciseDao(appDatabase: AppDatabase): ExerciseDao {
        return appDatabase.exerciseDao
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
    fun provideUserDataStore(@ApplicationContext context: Context): UserRepository {
        return UserRepositoryImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideTrainingRepository(
        trainingDao: TrainingDao,
        setDao: SetDao,
        exerciseDao: ExerciseDao
    ): TrainingRepository {
        return TrainingRepositoryImpl(trainingDao, setDao, exerciseDao)
    }

    @Provides
    @Singleton
    fun provideSetRepository(
        setDao: SetDao,
        exerciseDao: ExerciseDao
    ): SetRepository {
        return SetRepositoryImpl(setDao, exerciseDao)
    }

    @Provides
    @Singleton
    fun provideExercisesRepository(
        exerciseDao: ExerciseDao,
    ): ExercisesRepository {
        return ExercisesRepositoryImpl(exerciseDao)
    }

    @Provides
    @Singleton
    fun provideTrainingPlanRepository(
        trainingPlanDao: TrainingPlanDao,
        setDao: SetDao,
        exerciseDao: ExerciseDao
    ): TrainingPlanRepository {
        return TrainingPlanRepositoryImpl(trainingPlanDao, setDao, exerciseDao)
    }

}