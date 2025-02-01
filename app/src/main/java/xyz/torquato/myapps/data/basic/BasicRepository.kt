package xyz.torquato.myapps.data.basic

class BasicRepository(
    libraryName: String = "basic"
) {

    private external fun entry(): String

    init {
        System.loadLibrary(libraryName)
    }
}