package com.tynkovski.notes.presentation.utils

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp

@Immutable
class Paddings(
    val extraSmall: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp,
) {
    fun copy(
        extraSmall: Dp = this.extraSmall,
        small: Dp = this.small,
        medium: Dp = this.medium,
        large: Dp = this.large,
        extraLarge: Dp = this.extraLarge,
    ): Paddings =
        Paddings(
            extraSmall = extraSmall,
            small = small,
            medium = medium,
            large = large,
            extraLarge = extraLarge,
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Paddings) return false
        if (extraSmall != other.extraSmall) return false
        if (small != other.small) return false
        if (medium != other.medium) return false
        if (large != other.large) return false
        if (extraLarge != other.extraLarge) return false
        return true
    }

    override fun hashCode(): Int {
        var result = extraSmall.hashCode()
        result = 31 * result + small.hashCode()
        result = 31 * result + medium.hashCode()
        result = 31 * result + large.hashCode()
        result = 31 * result + extraLarge.hashCode()
        return result
    }

    override fun toString(): String {
        return "Paddings(" +
                "extraSmall=$extraSmall, " +
                "small=$small, " +
                "medium=$medium, " +
                "large=$large, " +
                "extraLarge=$extraLarge)"
    }
}
