package xyz.torquato.myapps.test.microbench

import android.util.Log
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import xyz.torquato.myapps.domain.impl.web.SetQueryUseCase
import xyz.torquato.myapps.domain.impl.web.content.GetBooksUseCase
import xyz.torquato.myapps.domain.impl.web.content.SetSelectedBookUseCase
import xyz.torquato.myapps.domain.impl.web.paging.GetNextPageUseCase
import xyz.torquato.myapps.presentation.viewmodel.web.WebViewModel
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

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var webViewModel: WebViewModel

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun log() {
        benchmarkRule.measureRepeated {
            runBlocking {
                webViewModel.setQuery("example")
                println("MyTag: ${webViewModel.uiState.value}")
            }
            Log.d("LogBenchmark", "the cost of writing this log method will be measured")
        }
    }

}