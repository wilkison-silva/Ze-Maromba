package br.com.zemaromba

import android.app.Application
import android.util.Log
import br.com.zemaromba.common.extensions.convertJsonFileToString
import br.com.zemaromba.common.extensions.parseJsonStringToClassObject
import br.com.zemaromba.common.extensions.toExerciseEntity
import br.com.zemaromba.core_data.local.database.dao.ExerciseAndMuscleDao
import br.com.zemaromba.core_data.local.database.dao.ExerciseDao
import br.com.zemaromba.core_data.local.database.dao.SetDao
import br.com.zemaromba.core_data.local.database.dao.TrainingDao
import br.com.zemaromba.core_data.local.database.dao.TrainingPlanDao
import br.com.zemaromba.core_data.model.ExerciseAndMuscleGroupEntity
import br.com.zemaromba.core_data.model.ExerciseDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
                val jsonFileString = this@AppApplication
                    .convertJsonFileToString("exercises_with_muscle_groups.json")

                val exerciseDtoList =
                    parseJsonStringToClassObject<List<ExerciseDTO>>(jsonFileString!!)
                exerciseDtoList.forEach { exerciseDto ->
                    val id = exerciseDao.insert(exerciseDto.toExerciseEntity())
                    exerciseDto.muscleGroups.forEach {
                        exerciseAndMuscleDao.insert(
                            exerciseAndMuscleRef = ExerciseAndMuscleGroupEntity(
                                exerciseId = id,
                                muscleName = it.name
                            )
                        )
                    }
                }
            }
        }
    }
}