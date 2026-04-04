package xyz.torquato.myapps.data.impl.web

import kotlinx.coroutines.flow.Flow
import xyz.torquato.myaps.domain.api.web.ILocalWebRepository
import javax.inject.Inject

class LocalWebRepository @Inject constructor(
    private val dataSource: LocalWebDataSource,
): ILocalWebRepository {

    override val selectedBookId: Flow<String> = dataSource.selectedBookId

    override fun setSelectedBookId(id: String) = dataSource.setSelectedBookId(id)

}