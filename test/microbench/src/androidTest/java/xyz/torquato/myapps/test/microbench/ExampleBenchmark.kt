package xyz.torquato.myapps.test.microbench

import androidx.benchmark.ExperimentalBenchmarkConfigApi
import androidx.benchmark.MicrobenchmarkConfig
import androidx.benchmark.TimeCapture
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import xyz.torquato.myapps.domain.impl.web.SetQueryUseCase
import xyz.torquato.myapps.domain.impl.web.content.GetBooksUseCase
import xyz.torquato.myapps.presentation.viewmodel.web.WebViewModel
import xyz.torquato.myapps.presentation.viewmodel.web.model.BookMenuMapper.toUiState
import xyz.torquato.myapps.presentation.viewmodel.web.model.BookMenuUiState
import javax.inject.Inject

/**
 * Benchmark, which will execute on an Android device.
 *
 * The body of [BenchmarkRule.measureRepeated] is measured in a loop, and Studio will
 * output the result. Modify your code to see how it affects performance.
 */
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ExampleBenchmark {

    @OptIn(ExperimentalBenchmarkConfigApi::class)
    @get:Rule
    val benchmarkRule = BenchmarkRule(MicrobenchmarkConfig(
        metrics = listOf(TimeCapture()),
        warmupCount = 50,
        measurementCount = 50,
    ))

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var webViewModel: WebViewModel


    @Inject
    lateinit var getBooksUseCase: GetBooksUseCase

    @Inject
    lateinit var setQueryUseCase: SetQueryUseCase

    @OptIn(ExperimentalBenchmarkConfigApi::class)
    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun log() {
        benchmarkRule.measureRepeated {
            runBlocking {
                setQueryUseCase("example")

                delay(500L)

                getUiState().first()
            }
        }
    }

    @Test
    fun log_2() {
        runBlocking {
            benchmarkRule.measureRepeated {
                setQueryUseCase("example")
            }
        }
    }

    private fun getUiState() = getBooksUseCase.invoke().map { items ->
        println("MyTag: New Content ${items}")
        BookMenuUiState(items.toUiState())
    }

}