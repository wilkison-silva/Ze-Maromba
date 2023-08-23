package br.com.zemaromba.di

import br.com.zemaromba.data.sources.local.database.dao.ExerciseDao
import br.com.zemaromba.data.sources.local.database.dao.SetDao
import br.com.zemaromba.data.sources.local.database.dao.TrainingPlanDao
import br.com.zemaromba.data.repository.TrainingPlanRepositoryImpl
import br.com.zemaromba.domain.repository.TrainingPlanRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TrainingPlanModule {

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