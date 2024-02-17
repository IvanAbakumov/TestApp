package com.world.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryItemModel (
    val text: String?,
    val number: String?
) : Parcelable