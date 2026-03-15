package xyz.torquato.myapps.domain.web

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import xyz.torquato.myapps.api.web.IQueryRepository
import xyz.torquato.myapps.api.web.IWebRepository
import xyz.torquato.myapps.api.web.model.QueryResult
import xyz.torquato.myapps.data.web.QueryRepository


class GetBooksUseCase(
    private val queryRepository: IQueryRepository,
    private val webRepository: IWebRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<QueryResult> =
        queryRepository.queryRequest.onEach{
            println("Mytag: ${this::class.simpleName} Content Request $it")
        }.flatMapLatest { queryRequest ->
            flowOf(webRepository.request(queryRequest))
        }
}