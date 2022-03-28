package lej.happy.musicapp.ui.main.home

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lej.happy.musicapp.R
import lej.happy.musicapp.databinding.FragmentHomeBinding
import lej.happy.musicapp.ui.base.BaseFragment

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>() {
    override val layoutResourceId = R.layout.fragment_home

    override fun initBinding() {

    }
}
