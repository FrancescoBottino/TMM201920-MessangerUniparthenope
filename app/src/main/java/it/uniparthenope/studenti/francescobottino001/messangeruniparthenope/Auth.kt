package it.uniparthenope.studenti.francescobottino001.messangeruniparthenope

import android.content.Context
import it.uniparthenope.studenti.francescobottino001.messangeruniparthenope.util.SingletonHolder

class Auth( private var context: Context) {
    companion object : SingletonHolder<Auth, Context>(::Auth) {
        private const val TOKEN = "token"
        private const val USERNAME = "username"
    }

    private fun getValue(key: String): String? {
        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_auth_file), Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    private fun setValue(key: String, value: String?) {
        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_auth_file), Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    var username:String?
        get() = getValue(USERNAME)
        set(newUsername) = setValue(USERNAME, newUsername)

    private var token:String?
        get() = getValue(TOKEN)
        set(newToken) = setValue(TOKEN, newToken)

    fun login(token:String, username:String) {
        this.username = username
        this.token = token
    }

    fun logout() {
        this.username = null
        this.token = null
    }

    fun isUserLogged(): Boolean {
        return try {
            val token:String = token!!
            val username:String = username!!
            true
        } catch (e:Exception) {
            false
        }
    }
}