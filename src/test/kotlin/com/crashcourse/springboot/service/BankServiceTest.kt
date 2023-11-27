package com.crashcourse.springboot.service

import com.crashcourse.springboot.repository.BankRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class BankServiceTest{
    // relax means that we don't care about the order of the calls to the mock object and return default values for any calls that we haven't explicitly set up.
    private val bankRepository:BankRepository = mockk(relaxed = true)
    private val bankService = BankService(bankRepository)
    @Test
    fun `should call it repository to retrieve banks`(){
        // arrange
        // setup getBanks() method to return empty list - dont need it because we have relaxed = true in mockk
        // every { bankRepository.getBanks() } returns emptyList()

        // act
        val banks = bankService.getBanks()

        // assert

        // This verifies that the getBanks() method was called on the bankRepository object exactly once.
        verify (exactly = 1) { bankRepository.retrieveBanks() }

    }


    @Test
    fun `should call it repository to retrieve bank by account number`(){
        // arrange
        val accountNumber = "1234"

        // act
        val bank = bankService.getBank(accountNumber)

        // assert
        verify (exactly = 1) { bankRepository.retrieveBank(accountNumber) }
    }
}