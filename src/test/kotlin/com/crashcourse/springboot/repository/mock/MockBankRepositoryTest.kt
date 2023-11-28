package com.crashcourse.springboot.repository.mock

import com.crashcourse.springboot.model.Bank
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.test.annotation.DirtiesContext

internal class MockBankRepositoryTest {
    private val mockRepository = MockBankRepository()

    @Nested
    @DisplayName("MockBankRetrieveBanksTest")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class MockBankRetrieveBanksTest {
        @Test
        fun `should provide a collection of banks`() {
            // arrange

            // act
            val banks = mockRepository.retrieveBanks()

            // assert
            assertThat(banks.size).isGreaterThanOrEqualTo(3)
        }

        @Test
        fun `should provide some mock data`() {
            // arrange

            // act
            val banks = mockRepository.retrieveBanks()

            // assert
            assertThat(banks).allMatch { it.accountNumber.isNotBlank() }
            assertThat(banks).anyMatch { it.trust != 0.0 }
            assertThat(banks).anyMatch { it.transactionFee != 0 }
        }

    }

    @Nested
    @DisplayName("MockBankRetrieveBankTest")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class MockBankRetrieveBankTest {
        @Test
        fun `should provide a bank`() {
            // arrange
            val accountNumber = "1234"

            // act
            val bank = mockRepository.retrieveBank(accountNumber)


            // assert
            assertThat(bank?.accountNumber).isEqualTo(accountNumber)

        }

        @Test
        fun `should throw an exception when bank not found`() {
            // arrange
            val accountNumber = "does_not_exist"

            // act / assert
            val exception = assertThrows<NoSuchElementException> { mockRepository.retrieveBank(accountNumber) }
            assertThat(exception.message).contains("Could not find a bank with account number $accountNumber")
        }
    }

    @Nested
    @DisplayName("MockBankCreateBankTest")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class MockBankCreateBankTest {
        @Test
        fun `should create a bank`() {
            // arrange
            val bank = Bank("new_bank", 31.415, 17)

            // act
            val createdBank = mockRepository.createBank(bank)

            // assert
            assertThat(createdBank).isEqualTo(bank)
            // assert that the bank was added to the list of banks
            assertThat(mockRepository.retrieveBank(bank.accountNumber)).isEqualTo(bank)
        }

        @Test
        fun `should throw an exception when bank with given account number already exists`() {
            // arrange
            val bank = Bank("1234", 31.415, 17)

            // act / assert
            assertThrows<IllegalArgumentException> { mockRepository.createBank(bank) }
        }
    }

    @Nested
    @DisplayName("MockBankUpdateBankTest")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class MockBankUpdateBankTest {
        @Test
        fun `should update an existing bank`() {
            // arrange
            val updatedBank = Bank("1234", 1.0, 1)

            // act
            val bank = mockRepository.updateBank(updatedBank)

            // assert
            assertThat(bank).isEqualTo(updatedBank)

            // assert that the bank was updated
            assertThat(mockRepository.retrieveBank(updatedBank.accountNumber)).isEqualTo(updatedBank)
        }

        @Test
        fun `should throw an exception when bank with given account number does not exist`() {
            // arrange
            val updatedBank = Bank("does_not_exist", 1.0, 1)

            // act / assert
            assertThrows<NoSuchElementException> { mockRepository.updateBank(updatedBank) }
        }
    }

    @Nested
    @DisplayName("MockBankDeleteBankTest")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class MockBankDeleteBankTest {
        @Test
        fun `should delete an existing bank`() {
            // arrange
            val accountNumber = "1234"

            // act
            mockRepository.deleteBank(accountNumber)

            // assert
            assertThrows<NoSuchElementException> {
                mockRepository.retrieveBank(accountNumber)
            }
        }

        @Test
        fun `should throw an exception when bank with given account number does not exist`() {
            // arrange
            val accountNumber = "does_not_exist"

            // act / assert
            assertThrows<NoSuchElementException> { mockRepository.deleteBank(accountNumber) }
        }
    }


}