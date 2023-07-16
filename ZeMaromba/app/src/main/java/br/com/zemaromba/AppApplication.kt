package br.com.zemaromba

import android.app.Application
import br.com.zemaromba.common.extensions.convertJsonFileToString
import br.com.zemaromba.common.extensions.isDatabaseCreated
import br.com.zemaromba.common.extensions.parseJsonStringToClassObject
import br.com.zemaromba.common.extensions.toExerciseEntity
import br.com.zemaromba.core_data.local.database.dao.ExerciseAndMuscleDao
import br.com.zemaromba.core_data.local.database.dao.ExerciseDao
import br.com.zemaromba.core_data.model.ExerciseAndMuscleGroupEntity
import br.com.zemaromba.core_data.model.ExerciseDTO
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
            }
        }
    }
}