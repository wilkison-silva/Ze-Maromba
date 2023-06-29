package br.com.zemaromba.feature.home.di

import br.com.zemaromba.core_data.local.database.dao.TrainingPlanDao
import br.com.zemaromba.feature.home.data.repository.TrainingPlanRepositoryImpl
import br.com.zemaromba.feature.home.domain.repository.TrainingPlanRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideTrainingPlanRepository(
        trainingPlanDao: TrainingPlanDao
    ): TrainingPlanRepository {
        return TrainingPlanRepositoryImpl(trainingPlanDao)
    }
}