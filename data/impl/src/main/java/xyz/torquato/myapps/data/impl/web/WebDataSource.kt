package xyz.torquato.myapps.data.impl.web

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebDataSource @Inject constructor() {

    external fun search(query: String, start: Int, length: Int): Any

    init {
        System.loadLibrary("web")
    }
}