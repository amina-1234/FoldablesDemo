package com.example.resizescreenapp.ui.util

import androidx.window.layout.FoldingFeature
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun FoldingFeature?.isTableTopPosture(): Boolean {
    contract { returns(true) implies (this@isTableTopPosture != null) }
    return this?.state == FoldingFeature.State.HALF_OPENED &&
            this.orientation == FoldingFeature.Orientation.HORIZONTAL
}

@OptIn(ExperimentalContracts::class)
fun FoldingFeature?.isBookPosture(): Boolean {
    contract { returns(true) implies (this@isBookPosture != null) }
    return this?.state == FoldingFeature.State.HALF_OPENED &&
            this.orientation == FoldingFeature.Orientation.VERTICAL
}

@OptIn(ExperimentalContracts::class)
fun FoldingFeature?.isSeparatingPosture(): Boolean {
    contract { returns(true) implies (this@isSeparatingPosture != null) }
    return this?.state == FoldingFeature.State.FLAT && this.isSeparating
}
