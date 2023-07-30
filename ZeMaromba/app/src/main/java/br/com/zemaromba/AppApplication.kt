package br.com.zemaromba

import android.app.Application
import br.com.zemaromba.common.extensions.convertJsonFileToString
import br.com.zemaromba.common.extensions.isDatabaseCreated
import br.com.zemaromba.common.extensions.parseJsonStringToClassObject
import br.com.zemaromba.common.extensions.toExerciseEntity
import br.com.zemaromba.core_data.local.database.dao.ExerciseAndMuscleDao
import br.com.zemaromba.core_data.local.database.dao.ExerciseDao
import br.com.zemaromba.core_data.local.database.dao.SetDao
import br.com.zemaromba.core_data.local.database.dao.TrainingDao
import br.com.zemaromba.core_data.local.database.dao.TrainingPlanDao
import br.com.zemaromba.core_data.model.ExerciseAndMuscleGroupEntity
import br.com.zemaromba.core_data.model.ExerciseDTO
import br.com.zemaromba.core_data.model.SetEntity
import br.com.zemaromba.core_data.model.TrainingEntity
import br.com.zemaromba.core_data.model.TrainingPlanEntity
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class AppApplication : Application() {

    @Inject
    lateinit var setDao: SetDao

    @Inject
    lateinit var trainingDao: TrainingDao

    @Inject
    lateinit var trainingPlanDao: TrainingPlanDao

    @Inject
    lateinit var exerciseDao: ExerciseDao

    @Inject
    lateinit var exerciseAndMuscleDao: ExerciseAndMuscleDao

    override fun onCreate() {
        super.onCreate()

        createExercisesIfNecessary()
    }

    private fun createExercisesIfNecessary() {
        if (!isDatabaseCreated(BuildConfig.DATABASE_NAME)) {
            CoroutineScope(Dispatchers.IO).launch {
                val fileName = "exercises_with_muscle_groups.json"
                val context = this@AppApplication
                context.convertJsonFileToString(fileName = fileName)?.let { jsonFileString ->
                    parseJsonStringToClassObject<List<ExerciseDTO>>(jsonFileString)
                        .forEach { exerciseDto ->
                            val id =
                                exerciseDao.insert(exerciseEntity = exerciseDto.toExerciseEntity())
                            exerciseDto.muscleGroups.forEach { muscleGroup ->
                                exerciseAndMuscleDao.insert(
                                    exerciseAndMuscleRef = ExerciseAndMuscleGroupEntity(
                                        exerciseId = id,
                                        muscleName = muscleGroup.name
                                    )
                                )
                            }
                        }
                }



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
                        training_id = 2,
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