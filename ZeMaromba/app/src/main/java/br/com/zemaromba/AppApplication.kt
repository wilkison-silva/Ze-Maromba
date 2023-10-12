package br.com.zemaromba

import android.app.Application
import android.content.res.Configuration
import br.com.zemaromba.common.extensions.convertJsonFileToString
import br.com.zemaromba.common.extensions.isDatabaseCreated
import br.com.zemaromba.common.extensions.parseJsonStringToClassObject
import br.com.zemaromba.data.model.ExerciseAndMuscleGroupEntity
import br.com.zemaromba.data.model.ExerciseDTO
import br.com.zemaromba.data.sources.local.database.dao.ExerciseDao
import br.com.zemaromba.domain.repository.UserRepository
import br.com.zemaromba.presentation.features.user_configurations.model.Theme
import com.google.android.gms.ads.MobileAds
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
    lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()

        createExercisesIfNecessary()
        googleAdmobInit()
    }

    private fun googleAdmobInit() {
        MobileAds.initialize(this) {}
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
            }
        }
    }
}