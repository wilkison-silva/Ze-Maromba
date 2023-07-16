package br.com.zemaromba

import android.app.Application
import br.com.zemaromba.core_data.local.database.dao.ExerciseAndMuscleDao
import br.com.zemaromba.core_data.local.database.dao.ExerciseDao
import br.com.zemaromba.core_data.local.database.dao.SetDao
import br.com.zemaromba.core_data.local.database.dao.TrainingDao
import br.com.zemaromba.core_data.local.database.dao.TrainingPlanDao
import br.com.zemaromba.core_data.model.ExerciseAndMuscleGroupEntity
import br.com.zemaromba.core_data.model.ExerciseEntity
import br.com.zemaromba.core_data.model.SetEntity
import br.com.zemaromba.core_data.model.TrainingEntity
import br.com.zemaromba.core_data.model.TrainingPlanEntity
import br.com.zemaromba.core_domain.model.MuscleGroup
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class AppApplication : Application() {

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
                exerciseDao.insertAll(
                    exerciseEntityList = listOf(
                        ExerciseEntity(name = "Pulley frente", favorite = true, urlLink = null),
                        ExerciseEntity(name = "Pulley triângulo", favorite = true, urlLink = null),
                        ExerciseEntity(name = "Remada pronada", favorite = false, urlLink = null),
                        ExerciseEntity(name = "Crucifixo inverso", favorite = false, urlLink = null),
                        ExerciseEntity(name = "Rosca Scott", favorite = false, urlLink = null),
                        ExerciseEntity(name = "Rosca simultânea", favorite = false, urlLink = null),
                        ExerciseEntity(name = "Extensão lombar", favorite = false, urlLink = null),
                        ExerciseEntity(name = "Supino reto", favorite = false, urlLink = null),
                    )
                )
                exerciseAndMuscleDao.insertAll(
                    exerciseAndMuscleRefList = listOf(
                        ExerciseAndMuscleGroupEntity(
                            exerciseId = 1,
                            muscleName = MuscleGroup.DORSAL.name
                        ),
                        ExerciseAndMuscleGroupEntity(
                            exerciseId = 2,
                            muscleName = MuscleGroup.DORSAL.name
                        ),
                        ExerciseAndMuscleGroupEntity(
                            exerciseId = 3,
                            muscleName = MuscleGroup.DORSAL.name
                        ),
                        ExerciseAndMuscleGroupEntity(
                            exerciseId = 4,
                            muscleName = MuscleGroup.DORSAL.name
                        ),
                        ExerciseAndMuscleGroupEntity(
                            exerciseId = 5,
                            muscleName = MuscleGroup.BICEPS.name
                        ),
                        ExerciseAndMuscleGroupEntity(
                            exerciseId = 6,
                            muscleName = MuscleGroup.BICEPS.name
                        ),
                        ExerciseAndMuscleGroupEntity(
                            exerciseId = 7,
                            muscleName = MuscleGroup.LUMBAR.name
                        ),
                        ExerciseAndMuscleGroupEntity(
                            exerciseId = 8,
                            muscleName = MuscleGroup.CHEST.name
                        ),
                        ExerciseAndMuscleGroupEntity(
                            exerciseId = 8,
                            muscleName = MuscleGroup.TRICEPS.name
                        ),
                    )
                )

                trainingPlanDao.insert(TrainingPlanEntity(name = "Monstrão em 60 dias"))
                trainingPlanDao.insert(TrainingPlanEntity(name = "Monstrona em 60 dias"))

                trainingDao.insert(
                    TrainingEntity(
                        trainingPlanId = 1,
                        name = "Peitoral e Bíceps - Semana 1"
                    )
                )

                trainingDao.insert(
                    TrainingEntity(
                        trainingPlanId = 2,
                        name = "Coxas - Semana 1"
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