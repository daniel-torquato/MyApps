package xyz.torquato.myapps.ui.producer.cypher

object InputProcessing {
    @OptIn(ExperimentalStdlibApi::class)
    fun convertToHex(input: String): String = cleanUp(input).also {
        println("MyTag: ALL $it")
    }.chunked(2).joinToString(separator = ":") { chunk ->
        chunk.map {
            when (it) {
                in '0'..'9' -> it
                in 'a'..'f' -> it
                else -> '0'
            }
        }.joinToString("")
    }

    fun cleanUp(input: String) = input.lowercase().replace(":", "")
}