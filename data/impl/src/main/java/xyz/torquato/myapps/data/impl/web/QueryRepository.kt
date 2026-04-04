package xyz.torquato.myapps.data.impl.web

import kotlinx.coroutines.flow.Flow
import xyz.torquato.myaps.domain.api.web.IQueryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QueryRepository @Inject constructor(
    private val dataSource: QueryDataSource
): IQueryRepository {

    override val queryRequest: Flow<String> = dataSource.query.also {
        println("MyTag: [R] UPDATE ${hashCode()}")
    }

    override fun getLast(): String = dataSource.query.value.also {
        println("MyTag: [R] CURRENT ${hashCode()}")
    }

    override fun setQuery(request: String) {
        dataSource.setAll(request)
    }
}