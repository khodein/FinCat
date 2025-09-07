package com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract

import com.sergei.pokhodai.expensemanagement.core.router.contract.RouterContract
import kotlinx.serialization.Serializable

@Serializable
internal data class UserAvatarContract(
    val avatar: String?
) : RouterContract