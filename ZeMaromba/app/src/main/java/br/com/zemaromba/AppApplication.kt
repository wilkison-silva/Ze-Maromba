package br.com.zemaromba

import android.app.Application
import br.com.zemaromba.core_data.local.database.dao.ExerciseAndMuscleDao
import br.com.zemaromba.core_data.local.database.dao.ExerciseDao
import br.com.zemaromba.core_data.local.database.dao.MuscleGroupDao
import br.com.zemaromba.core_data.model.ExerciseEntity
import br.com.zemaromba.core_data.model.MuscleGroupEntity
import br.com.zemaromba.core_data.model.cross_references.ExerciseAndMuscleGroupCrossRefEntity
import br.com.zemaromba.feature.training_origination.domain.model.MuscleGroup
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class AppApplication : Application() {

    @Inject
    lateinit var muscleGroupDao: MuscleGroupDao

    @Inject
    lateinit var exerciseDao: ExerciseDao

    @Inject
    lateinit var exerciseAndMuscleDao: ExerciseAndMuscleDao

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            deleteDatabase(BuildConfig.DATABASE_NAME)
            CoroutineScope(Dispatchers.IO).launch {
                MuscleGroup.values().forEach {
                    muscleGroupDao.insert(MuscleGroupEntity(name = it.name))
                }
                exerciseDao.insertAll(
                    exerciseEntityList = listOf(
                        ExerciseEntity(name = "Pulley frente"),
                        ExerciseEntity(name = "Pulley triângulo"),
                        ExerciseEntity(name = "Remada pronada"),
                        ExerciseEntity(name = "Crucifixo inverso"),
                        ExerciseEntity(name = "Rosca Scott"),
                        ExerciseEntity(name = "Rosca simultânea"),
                        ExerciseEntity(name = "Extensão lombar"),
                        ExerciseEntity(name = "Supino reto"),
                    )
                )
                exerciseAndMuscleDao.insertAll(
                    exerciseAndMuscleRefList = listOf(
                        ExerciseAndMuscleGroupCrossRefEntity(
                            exerciseId = 1,
                            muscleId = 2
                        ),
                        ExerciseAndMuscleGroupCrossRefEntity(
                            exerciseId = 2,
                            muscleId = 2
                        ),
                        ExerciseAndMuscleGroupCrossRefEntity(
                            exerciseId = 3,
                            muscleId = 2
                        ),
                        ExerciseAndMuscleGroupCrossRefEntity(
                            exerciseId = 4,
                            muscleId = 2
                        ),
                        ExerciseAndMuscleGroupCrossRefEntity(
                            exerciseId = 5,
                            muscleId = 5
                        ),
                        ExerciseAndMuscleGroupCrossRefEntity(
                            exerciseId = 6,
                            muscleId = 5
                        ),
                        ExerciseAndMuscleGroupCrossRefEntity(
                            exerciseId = 7,
                            muscleId = 14
                        ),
                        ExerciseAndMuscleGroupCrossRefEntity(
                            exerciseId = 8,
                            muscleId = 1
                        ),
                        ExerciseAndMuscleGroupCrossRefEntity(
                            exerciseId = 8,
                            muscleId = 4
                        ),
                    )
                )

            }
        }
    }
}