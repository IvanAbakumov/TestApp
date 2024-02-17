package com.world.test.presentation.screen_2

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.world.data.ENTER_NUMBER
import com.world.data.LOCAL_ITEM
import com.world.domain.models.HistoryItemModel
import com.world.test.R
import com.world.test.common.core.fragment.BaseFragment
import com.world.test.databinding.FragmentScreen2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Screen2Fragment : BaseFragment<FragmentScreen2Binding>() {

    override val layoutRes = R.layout.fragment_screen2
    override val binding: FragmentScreen2Binding by viewBinding()
    private val viewModel: Screen2ViewModel by viewModels()
    private val enterNumber by lazy { arguments?.getString(ENTER_NUMBER) }
    private val localItem by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(LOCAL_ITEM, HistoryItemModel::class.java)
        } else {
            arguments?.getParcelable(LOCAL_ITEM)
        }
    }

    override fun initUI(savedInstanceState: Bundle?) {
        if(localItem != null) viewModel.setFactNumberModel(localItem)
        else viewModel.getFact(enterNumber)
    }

    override fun layoutBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.view = this
        binding.viewModel = viewModel
    }

    fun onClickBack() {
        findNavController().popBackStack()
    }
}