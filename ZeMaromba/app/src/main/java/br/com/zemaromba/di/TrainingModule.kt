package br.com.zemaromba.di

import br.com.zemaromba.data.repository.SetRepositoryImpl
import br.com.zemaromba.data.sources.local.database.dao.ExerciseDao
import br.com.zemaromba.data.sources.local.database.dao.SetDao
import br.com.zemaromba.data.sources.local.database.dao.TrainingDao
import br.com.zemaromba.data.repository.TrainingRepositoryImpl
import br.com.zemaromba.domain.repository.SetRepository
import br.com.zemaromba.domain.repository.TrainingRepository
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

    @Provides
    @Singleton
    fun provideSetRepository(
        setDao: SetDao,
        exerciseDao: ExerciseDao
    ): SetRepository {
        return SetRepositoryImpl(setDao, exerciseDao)
    }
}