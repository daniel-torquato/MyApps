package xyz.torquato.myapps.data.impl.web

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class WebDataSource @Inject constructor() {

    open external fun search(query: String, start: Int, length: Int): Any

    init {
        System.loadLibrary("web")
    }
}