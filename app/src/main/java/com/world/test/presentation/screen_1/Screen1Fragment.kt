package com.world.test.presentation.screen_1

import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.world.data.ENTER_NUMBER
import com.world.data.LOCAL_ITEM
import com.world.domain.models.HistoryItemModel
import com.world.test.R
import com.world.test.common.core.fragment.BaseFragment
import com.world.test.databinding.FragmentScreen1Binding
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Screen1Fragment : BaseFragment<FragmentScreen1Binding>() {

    override val layoutRes = R.layout.fragment_screen1
    override val binding: FragmentScreen1Binding by viewBinding()
    private val viewModel: Screen1ViewModel by viewModels()

    private val adapter by lazy {
        HistoryAdapter(
            object : HistoryAdapter.HistoryItemListener {
                override fun setSelectedItem(item: HistoryItemModel) {
                    findNavController().navigate(
                        R.id.action_screen1Fragment_to_screen2Fragment,
                        bundleOf(
                            LOCAL_ITEM to item
                        )
                    )
                }
            }
        )
    }

    override fun initUI(savedInstanceState: Bundle?) {
        viewModel.getItemsFromDatabase()
        observeHistoryList()
    }

    override fun layoutBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.view = this
        binding.viewModel = viewModel
        binding.adapter = adapter
    }

    fun onClickGetFact() {
        if (viewModel.getEnterNumber().isNotEmpty())
            if (viewModel.checkInternetConnection(requireContext()))
                findNavController().navigate(
                    R.id.action_screen1Fragment_to_screen2Fragment,
                    bundleOf(ENTER_NUMBER to viewModel.getEnterNumber())
                )
            else Toasty.error(
                requireContext(),
                getString(R.string.internet_is_not_available),
                Toast.LENGTH_SHORT,
                true
            ).show()
        else {
            Toasty.error(
                requireContext(),
                getString(R.string.text_empty_field),
                Toast.LENGTH_SHORT,
                true
            ).show()
        }
    }

    fun onClickGetFactRandom() {
        if (viewModel.checkInternetConnection(requireContext()))
            findNavController().navigate(R.id.action_screen1Fragment_to_screen2Fragment)
        else Toasty.error(
            requireContext(),
            getString(R.string.internet_is_not_available),
            Toast.LENGTH_SHORT,
            true
        ).show()
    }

    private fun observeHistoryList() = viewLifecycleOwner.lifecycleScope.launch {
        viewModel.listItems.collect {
            it?.let {
                adapter.setData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearEnterNumber()
    }
}