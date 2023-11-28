package com.crashcourse.springboot.repository.network.dto

import com.crashcourse.springboot.model.Bank

data class BankList(
    val results: Collection<Bank>
)