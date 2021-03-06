package it.uniparthenope.studenti.francescobottino001.messangeruniparthenope.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import it.uniparthenope.studenti.francescobottino001.messangeruniparthenope.App
import it.uniparthenope.studenti.francescobottino001.messangeruniparthenope.Auth
import it.uniparthenope.studenti.francescobottino001.messangeruniparthenope.LoginDialog
import it.uniparthenope.studenti.francescobottino001.messangeruniparthenope.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onResume() {
        super.onResume()

        val ctx = requireContext()
        val auth = Auth.getInstance(ctx)

        if(auth.isUserLogged()) {
            user_message_textview.text = "Benvenuto ${auth.username}"
            user_action_button.text = getString(R.string.logout_button_text)
            user_action_button.setOnClickListener {
                auth.logout()
                this.onResume()
            }
        } else {
            user_message_textview.text = getString(R.string.user_not_logged)
            user_action_button.text = getString(R.string.login_button_text)
            user_action_button.setOnClickListener {
                LoginDialog(requireContext()) {
                    this.onResume()
                }.show()
            }
        }
    }
}
