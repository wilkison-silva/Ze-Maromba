package br.com.zemaromba

import android.app.Application
import br.com.zemaromba.core_data.local.database.dao.ExerciseAndMuscleDao
import br.com.zemaromba.core_data.local.database.dao.ExerciseDao
import br.com.zemaromba.core_data.local.database.dao.MuscleGroupDao
import br.com.zemaromba.core_data.local.database.dao.SetDao
import br.com.zemaromba.core_data.local.database.dao.TrainingDao
import br.com.zemaromba.core_data.local.database.dao.TrainingPlanDao
import br.com.zemaromba.core_data.model.ExerciseEntity
import br.com.zemaromba.core_data.model.MuscleGroupEntity
import br.com.zemaromba.core_data.model.SetEntity
import br.com.zemaromba.core_data.model.TrainingEntity
import br.com.zemaromba.core_data.model.TrainingPlanEntity
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

    @Inject
    lateinit var setDao: SetDao

    @Inject
    lateinit var trainingDao: TrainingDao

    @Inject
    lateinit var trainingPlanDao: TrainingPlanDao

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

                trainingPlanDao.insert(TrainingPlanEntity(name = "Monstrão em 60 dias"))
                trainingPlanDao.insert(TrainingPlanEntity(name = "Monstrona em 60 dias"))

                trainingDao.insert(
                    TrainingEntity(
                        trainingPlanId = 1,
                        name = "Peitoral e Bíceps Monstros - Semana 1"
                    )
                )

                trainingDao.insert(
                    TrainingEntity(
                        trainingPlanId = 2,
                        name = "Coxas Duronas - Semana 1"
                    )
                )

                setDao.insert(
                    SetEntity(
                        exerciseId = 1,
                        training_id = 1,
                        quantity = 12,
                        repetitions = 4,
                        weight = 12.0,
                        observation = "Sem forçar a articulação",
                        completed = false,
                        restingTime = 60.0
                    )
                )

                setDao.insert(
                    SetEntity(
                        exerciseId = 2,
                        training_id = 1,
                        quantity = 12,
                        repetitions = 4,
                        weight = 12.0,
                        observation = "Segurar o triângulo corretamente",
                        completed = false,
                        restingTime = 60.0
                    )
                )

                setDao.insert(
                    SetEntity(
                        exerciseId = 5,
                        training_id = 1,
                        quantity = 12,
                        repetitions = 4,
                        weight = 10.0,
                        observation = "Não fazer rosca direta",
                        completed = false,
                        restingTime = 60.0
                    )
                )
            }
        }
    }
}