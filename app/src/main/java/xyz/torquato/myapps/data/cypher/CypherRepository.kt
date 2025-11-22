package xyz.torquato.myapps.data.cypher

import xyz.torquato.myapps.api.sound.ICypherRepository
import javax.inject.Inject

class CypherRepository @Inject constructor() : ICypherRepository {

   //private external fun entry(): String

   override fun message(): String = "dalksdjasldk"

   //init {
   //    System.loadLibrary("cypher")
   //}
}