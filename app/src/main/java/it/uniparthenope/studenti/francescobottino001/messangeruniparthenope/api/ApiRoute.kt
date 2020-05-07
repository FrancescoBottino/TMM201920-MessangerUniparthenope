package it.uniparthenope.studenti.francescobottino001.messangeruniparthenope.api

import android.util.Base64
import com.android.volley.Request

sealed class ApiRoute {

    val timeOut: Int
        get() {
            return 3000
        }

    val baseUrl: String
        get() {
            return "https://api.uniparthenope.it"
        }

    data class Login(var username:String, var password:String) : ApiRoute() {
        val token: String = Base64.encodeToString("$username:$password".toByteArray(), Base64.NO_WRAP)
    }

    val httpMethod: Int
        get() {
            return when (this) {
                is Login -> Request.Method.GET
            }
        }

    val params: HashMap<String, String>
        get() {
            val params: HashMap<String, String> = hashMapOf()
            return when (this) {
                is Login -> params
            }
        }

    val headers: HashMap<String, String>
        get() {
            val map: HashMap<String, String> = hashMapOf()
            map["Accept"] = "application/json"
            return when (this) {
                is Login -> {
                    map["authorization"] = "Basic ${this.token}"
                    map
                }
            }
        }

    val url: String
        get() {
            return "$baseUrl/${when (this@ApiRoute) {
                is Login -> "auth/v1/login"
            }}"
        }
}