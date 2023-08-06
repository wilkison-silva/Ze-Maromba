package br.com.zemaromba.feature.training_plan.di

import br.com.zemaromba.core_data.local.database.dao.ExerciseDao
import br.com.zemaromba.core_data.local.database.dao.SetDao
import br.com.zemaromba.core_data.local.database.dao.TrainingPlanDao
import br.com.zemaromba.feature.training_plan.data.repository.TrainingPlanRepositoryImpl
import br.com.zemaromba.feature.training_plan.domain.repository.TrainingPlanRepository
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