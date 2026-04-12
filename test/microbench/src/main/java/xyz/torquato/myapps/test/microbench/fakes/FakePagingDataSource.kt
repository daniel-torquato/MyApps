package xyz.torquato.myapps.test.microbench.fakes

import xyz.torquato.myapps.data.impl.external.PagingDataSource

class FakePagingDataSource: PagingDataSource() {

    override fun addPager(id: String) {
        println("MyTag: [FAKE] ${id}")
    }

}