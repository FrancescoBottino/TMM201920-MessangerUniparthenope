package it.uniparthenope.studenti.francescobottino001.messangeruniparthenope.api

import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

class ApiResponse(response: String) {

    var success: Boolean = false
    var error: String = ""
    var json: String = ""

    init {
        json = response
        try {
            if( response.isEmpty() )
                throw Exception("Empty response")

            val jsonToken = JSONTokener(response).nextValue()
            if (jsonToken !is JSONObject && jsonToken !is JSONArray)
                throw Exception("Could not parse result as JSON")

            success = true
            error = ""
        } catch (e: Exception) {
            e.printStackTrace()
            error = "Parsing Error${if( e.message!=null ) ", (${e.message})" else ""}"
            success = false
        }
    }
}