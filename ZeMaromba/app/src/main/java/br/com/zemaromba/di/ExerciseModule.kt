package br.com.zemaromba.di

import br.com.zemaromba.data.sources.local.database.dao.ExerciseDao
import br.com.zemaromba.data.repository.ExercisesRepositoryImpl
import br.com.zemaromba.domain.repository.ExercisesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExerciseModule {

    @Provides
    @Singleton
    fun provideExercisesRepository(
        exerciseDao: ExerciseDao,
    ): ExercisesRepository {
        return ExercisesRepositoryImpl(exerciseDao)
    }


}