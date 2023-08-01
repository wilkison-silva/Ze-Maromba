package br.com.zemaromba.feature.training_plan.di

import br.com.zemaromba.core_data.local.database.dao.ExerciseDao
import br.com.zemaromba.core_data.local.database.dao.SetDao
import br.com.zemaromba.core_data.local.database.dao.TrainingDao
import br.com.zemaromba.feature.training_plan.data.repository.TrainingRepositoryImpl
import br.com.zemaromba.feature.training_plan.domain.repository.TrainingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrainingModule {

    @Provides
    @Singleton
    fun provideTrainingRepository(
        trainingDao: TrainingDao,
        setDao: SetDao,
        exerciseDao: ExerciseDao
    ): TrainingRepository {
        return TrainingRepositoryImpl(trainingDao, setDao, exerciseDao)
    }
}