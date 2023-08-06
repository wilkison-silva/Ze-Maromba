package br.com.zemaromba.feature.home.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.zemaromba.core_domain.datastore.UserDataStore
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val userDataStore = mockk<UserDataStore>()
    private lateinit var viewModel: HomeScreenViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When viewmodel is initialized and userName was saved, userName must be retrieved from databases`() =
        runTest {
            val fakeName = "fakeName"
            coEvery {
                userDataStore.getName()
            }.returns(flowOf(fakeName))


            viewModel = HomeScreenViewModel(userDataStore)

            coVerify { userDataStore.getName() }
            assertEquals(fakeName, viewModel.state.value.userName)
        }
}