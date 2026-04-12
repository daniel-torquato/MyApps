package xyz.torquato.myapps.test.microbench.fakes

import org.json.JSONObject
import xyz.torquato.myapps.data.impl.web.WebDataSource

class FakeWebDataSource: WebDataSource() {

    override fun search(query: String, start: Int, length: Int): Any {
        return JSONObject()
    }
}