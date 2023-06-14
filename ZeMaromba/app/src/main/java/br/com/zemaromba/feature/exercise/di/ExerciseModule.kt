package br.com.zemaromba.feature.exercise.di

import br.com.zemaromba.core_data.local.database.dao.ExerciseDao
import br.com.zemaromba.feature.exercise.data.repository.ExercisesRepositoryImpl
import br.com.zemaromba.feature.exercise.domain.repository.ExercisesRepository
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
        exerciseDao: ExerciseDao
    ): ExercisesRepository {
        return ExercisesRepositoryImpl(exerciseDao)
    }


}