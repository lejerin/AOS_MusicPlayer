package lej.happy.musicapp.ui.login

import android.content.Intent
import android.os.Bundle
import com.happy.commons.ui.base.BaseActivity
import lej.happy.musicapp.R
import lej.happy.musicapp.databinding.ActivityLoginBinding
import lej.happy.musicapp.ui.main.MainActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override val layoutResourceId: Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}