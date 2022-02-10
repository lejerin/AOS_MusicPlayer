package lej.happy.musicapp.ui.login

import android.content.Intent
import android.os.Bundle
import lej.happy.musicapp.R
import lej.happy.musicapp.databinding.ActivityLoginBinding
import lej.happy.musicapp.ui.base.BaseActivity
import lej.happy.musicapp.ui.main.MainActivity

class LoginActivity : BaseActivity() {

    private val binding: ActivityLoginBinding by binding(R.layout.activity_login)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this@LoginActivity

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}