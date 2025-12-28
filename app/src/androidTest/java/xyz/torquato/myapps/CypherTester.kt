package xyz.torquato.myapps

object CypherTester {


    external fun sum(token: String, message: String): String

    external fun times(token: String, message: String): String

    external fun converter(token: String): String

    init {
        System.loadLibrary("cypher_test")
    }
}