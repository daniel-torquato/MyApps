package xyz.torquato.myapps.test.microbench.fakes

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import xyz.torquato.myapps.data.impl.web.WebDataSource

class FakeWebDataSource: WebDataSource() {

    override fun search(query: String, start: Int, length: Int): Any {
        Log.d("MyTag", "SEARCH $query")
        return if (query.isNotEmpty())
            produceObject(query)
        else
            JSONObject()
    }

    companion object {
        fun produceObject(tag: String) = JSONObject().apply {

            val items = JSONArray()

            repeat(5) { index ->
                val element = JSONObject()

                element.put("id", "$tag[$index]")

                val volumeInfo = JSONObject()

                volumeInfo.put("title", "example$index")
                volumeInfo.put("authors", "autor$index")
                volumeInfo.put("description", "description$index")

                val imageLinks = JSONObject()

                imageLinks.put("smallThumbnail", "http://example.com/image.png")
                imageLinks.put("thumbnail", "http://example.com/image.png")

                volumeInfo.put("imageLinks", imageLinks)

                element.put("volumeInfo", volumeInfo)

                val saleInfo = JSONObject()

                saleInfo.put("saleability", "FOR_SALE")

                saleInfo.put("buyLink", "https://example.com")

                element.put("saleInfo", saleInfo)

                items.put(index, element)
            }


            put("items", items)
        }
    }

}