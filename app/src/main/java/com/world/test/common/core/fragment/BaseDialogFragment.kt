package com.world.test.common.core.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.world.test.R

abstract class BaseDialogFragment : DialogFragment() {

    protected fun bindView(
        inflater: LayoutInflater,
        @LayoutRes layoutResId: Int
    ): View {
        val rootView = inflater.inflate(R.layout.base_dialog_fragment, null)
        val contentView = rootView.findViewById<ViewGroup>(R.id.content_view_base_dialog)
        inflater.inflate(layoutResId, contentView)

        return rootView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireContext(), R.style.Dialog) {}
    }
}