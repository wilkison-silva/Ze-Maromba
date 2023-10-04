package br.com.zemaromba

import android.app.Application
import android.content.res.Configuration
import br.com.zemaromba.common.extensions.convertJsonFileToString
import br.com.zemaromba.common.extensions.isDatabaseCreated
import br.com.zemaromba.common.extensions.parseJsonStringToClassObject
import br.com.zemaromba.data.model.ExerciseAndMuscleGroupEntity
import br.com.zemaromba.data.model.ExerciseDTO
import br.com.zemaromba.data.model.SetEntity
import br.com.zemaromba.data.model.TrainingEntity
import br.com.zemaromba.data.model.TrainingPlanEntity
import br.com.zemaromba.data.sources.local.database.dao.ExerciseDao
import br.com.zemaromba.data.sources.local.database.dao.SetDao
import br.com.zemaromba.data.sources.local.database.dao.TrainingDao
import br.com.zemaromba.data.sources.local.database.dao.TrainingPlanDao
import br.com.zemaromba.domain.repository.UserRepository
import br.com.zemaromba.presentation.features.user_configurations.model.Theme
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
    lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()

        createExercisesIfNecessary()
    }

    private fun createExercisesIfNecessary() {
        if (!isDatabaseCreated(BuildConfig.DATABASE_NAME)) {
            CoroutineScope(Dispatchers.IO).launch {
                val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                when (currentNightMode) {
                    Configuration.UI_MODE_NIGHT_NO -> {
                        userRepository.saveTheme(themeName = Theme.LIGHT.name)
                    }
                    Configuration.UI_MODE_NIGHT_YES -> {
                        userRepository.saveTheme(themeName = Theme.DARK.name)
                    }
                }

                val fileName = "exercises_with_muscle_groups.json"
                val context = this@AppApplication
                context.convertJsonFileToString(fileName = fileName)?.let { jsonFileString ->
                    parseJsonStringToClassObject<List<ExerciseDTO>>(jsonFileString)
                        .forEach { exerciseDto ->
                            exerciseDao.insertExerciseWithMuscleGroupRef(
                                exerciseEntity = exerciseDto.toExerciseEntity(),
                                onExerciseInserted = { exerciseId ->
                                    exerciseDto.muscleGroups.map {
                                        ExerciseAndMuscleGroupEntity(
                                            exerciseId = exerciseId,
                                            muscleName = it.name
                                        )
                                    }
                                }
                            )
                        }
                }

                //CRIA PLANO DE TREINO COM ID = 1
                trainingPlanDao.insert(TrainingPlanEntity(name = "Monstrão em 60 dias"))

                //CRIA TREINO COM ID = 1 DENTRO DO PLANO DE TREINO DE ID = 1
                trainingDao.insert(
                    TrainingEntity(
                        trainingPlanId = 1,
                        name = "Peitoral e Bíceps - Semana 1"
                    )
                )
                //ADICIONA SETS NO TREINO DE ID = 1
                setDao.insert(
                    SetEntity(
                        exerciseId = 1,
                        trainingId = 1,
                        quantity = 12,
                        repetitions = 4,
                        weight = 12,
                        observation = "Sem forçar a articulação",
                        completed = false,
                        restingTime = 60
                    )
                )
                setDao.insert(
                    SetEntity(
                        exerciseId = 2,
                        trainingId = 1,
                        quantity = 12,
                        repetitions = 4,
                        weight = 12,
                        observation = "Segurar o triângulo corretamente",
                        completed = false,
                        restingTime = 60
                    )
                )


                //CRIA TREINO COM ID = 2 DENTRO DO PLANO DE TREINO DE ID = 1
                trainingDao.insert(
                    TrainingEntity(
                        trainingPlanId = 1,
                        name = "Coxas - Semana 1"
                    )
                )
                //ADICIONA SETS NO TREINO DE ID = 2
                setDao.insert(
                    SetEntity(
                        exerciseId = 3,
                        trainingId = 2,
                        quantity = 12,
                        repetitions = 4,
                        weight = 10,
                        observation = "Não fazer rosca direta",
                        completed = false,
                        restingTime = 60
                    )
                )

            }
        }
    }
}