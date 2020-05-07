package it.uniparthenope.studenti.francescobottino001.messangeruniparthenope.api

import android.content.Context
import it.uniparthenope.studenti.francescobottino001.messangeruniparthenope.Auth
import org.json.JSONObject

class ApiResponseManager(private val ctx: Context) {
    fun loginResponseManager(
        response: String,
        token: String,
        username: String,
        completion: (token: String?, error: String?) -> Unit
    ) {
        val status:String = JSONObject(response).getString("status")
        if( status == "success" ) {
            Auth.getInstance(ctx).login( token, username )
            completion.invoke(token, null)
        } else {
            val errorMsg:String = JSONObject(response).getString("errMsg")
            completion.invoke(null, errorMsg)
        }
    }
}