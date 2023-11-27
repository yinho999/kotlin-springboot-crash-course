package com.crashcourse.springboot.repository

import com.crashcourse.springboot.model.Bank

interface BankRepository {
    fun retrieveBanks(): Collection<Bank>
    fun retrieveBank(accountNumber: String): Bank
    fun createBank(bank: Bank): Bank
    fun updateBank( bank: Bank): Bank
}