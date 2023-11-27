package com.crashcourse.springboot.repository.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockBankRepositoryTest{
    private val mockRepository = MockBankRepository()
    @Test
    fun `should provide a collection of banks`(){
        // arrange

        
        // act
        val banks = mockRepository.retrieveBanks()
        
        // assert
        assertThat(banks.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data`(){
        // arrange


        // act
        val banks = mockRepository.retrieveBanks()

        // assert
        assertThat(banks).allMatch { it.accountNumber.isNotBlank() }
        assertThat(banks).anyMatch { it.trust != 0.0 }
        assertThat(banks).anyMatch { it.transactionFee != 0 }
    }


    @Test
    fun `should provide a bank`(){
        // arrange
        val accountNumber = "1234"

        // act
        val bank = mockRepository.retrieveBank(accountNumber)


        // assert
        assertThat(bank?.accountNumber).isEqualTo(accountNumber)

    }
}