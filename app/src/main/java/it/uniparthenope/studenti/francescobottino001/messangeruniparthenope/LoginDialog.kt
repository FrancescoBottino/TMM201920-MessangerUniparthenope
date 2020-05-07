package it.uniparthenope.studenti.francescobottino001.messangeruniparthenope

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import it.uniparthenope.studenti.francescobottino001.messangeruniparthenope.api.ApiClient
import it.uniparthenope.studenti.francescobottino001.messangeruniparthenope.util.animateView
import kotlinx.android.synthetic.main.dialog_login.*
import kotlinx.android.synthetic.main.progress_overlay.*

class LoginDialog(context: Context, private val onLogin: (()->Unit)?) : AlertDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = View.inflate(this.context, R.layout.dialog_login, null)
        setContentView(view)

        login_button.setOnClickListener { onButtonClick() }
    }

    private fun onButtonClick() {
        startLoadingState()

        val username = username_field.text.toString()
        val password = password_field.text.toString()

        ApiClient(this.context).login(
            username,
            password
        ) { token: String?, error: String? ->
            if ( error == null ) {
                token!!

                result_textview.visibility = View.GONE
                endLoadingState()
                dismiss()
                onLogin?.invoke()
            } else {
                result_textview.text = error
                result_textview.visibility = View.VISIBLE
                endLoadingState()
            }
        }
    }

    private fun startLoadingState() {
        setCancelable(false)
        animateView(progress_overlay, View.VISIBLE, 1f, 200)
        login_button.isEnabled = false
        login_button.isClickable = false
        login_button.text = ""
    }

    private fun endLoadingState() {
        login_button.text = this.context.resources.getString(R.string.login_button_text)
        login_button.isClickable = true
        login_button.isEnabled = true
        animateView(progress_overlay, View.GONE, 0f, 200)
        setCancelable(false)
    }
}