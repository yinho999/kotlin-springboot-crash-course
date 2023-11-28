package com.crashcourse.springboot.service

import com.crashcourse.springboot.model.Bank
import com.crashcourse.springboot.repository.BankRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

internal class BankServiceTest {
    // relax means that we don't care about the order of the calls to the mock object and return default values for any calls that we haven't explicitly set up.
    private val bankRepository: BankRepository = mockk(relaxed = true)
    private val bankService = BankService(bankRepository)

    @Nested
    @DisplayName("Get Banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should call it repository to retrieve banks`() {
            // arrange
            // setup getBanks() method to return empty list - dont need it because we have relaxed = true in mockk
            // every { bankRepository.getBanks() } returns emptyList()

            // act
            val banks = bankService.getBanks()

            // assert

            // This verifies that the getBanks() method was called on the bankRepository object exactly once.
            verify(exactly = 1) { bankRepository.retrieveBanks() }

        }


        @Test
        fun `should call it repository to retrieve bank by account number`() {
            // arrange
            val accountNumber = "1234"

            // act
            val bank = bankService.getBank(accountNumber)

            // assert
            verify(exactly = 1) { bankRepository.retrieveBank(accountNumber) }
        }
    }

    @Nested
    @DisplayName("Get Bank")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should call it repository to retrieve bank by account number`() {
            // arrange
            val accountNumber = "1234"

            // act
            val bank = bankService.getBank(accountNumber)

            // assert
            verify(exactly = 1) { bankRepository.retrieveBank(accountNumber) }
        }

    }

    @Nested
    @DisplayName("Add Bank")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AddBank {
        @Test
        fun `should call it repository to add bank`() {
            // arrange
            val bank = Bank("1234", 1.0, 1)

            // act
            val result = bankService.addBank(bank)

            // assert
            verify(exactly = 1) { bankRepository.createBank(bank) }
        }

    }

    @Nested
    @DisplayName("Update Bank")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UpdateBank {
        @Test
        fun `should call it repository to update bank`() {
            // arrange
            val bank = Bank("1234", 1.0, 1)

            // act
            val result = bankService.updateBank(bank)

            // assert
            verify(exactly = 1) { bankRepository.updateBank(bank) }
        }

    }

    @Nested
    @DisplayName("Delete Bank")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteBank {
        @Test
        fun `should call it repository to delete bank`() {
            // arrange
            val accountNumber = "1234"

            // act
            val result = bankService.deleteBank(accountNumber)

            // assert
            verify(exactly = 1) { bankRepository.deleteBank(accountNumber) }
        }

    }

}