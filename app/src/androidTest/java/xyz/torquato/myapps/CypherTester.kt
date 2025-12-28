package xyz.torquato.myapps

object CypherTester {

    external fun entry(token: String, message: String): String


    init {
        System.loadLibrary("cypher_test")
    }
}