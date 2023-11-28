package com.crashcourse.springboot.service

import com.crashcourse.springboot.model.Bank
import com.crashcourse.springboot.repository.BankRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class BankService (
    // use @Qualifier to specify which bean to use
    @Qualifier("mock")
    private val bankRepository: BankRepository){
    fun getBanks(): Collection<Bank> = bankRepository.retrieveBanks()
    fun getBank(accountNumber: String): Bank = bankRepository.retrieveBank(accountNumber)
    fun addBank(bank: Bank): Bank = bankRepository.createBank(bank)
    fun updateBank(bank: Bank): Bank  = bankRepository.updateBank( bank)
    fun deleteBank(accountNumber: String) = bankRepository.deleteBank(accountNumber)
}