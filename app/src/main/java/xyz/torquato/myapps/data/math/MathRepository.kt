package xyz.torquato.myapps.data.math

import xyz.torquato.myapps.api.math.IMathRepository
import javax.inject.Inject

class MathRepository @Inject constructor() : IMathRepository {

    private external fun sum(token: String, message: String): String


    init {
        System.loadLibrary("calculator")
    }
}