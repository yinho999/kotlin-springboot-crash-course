package com.crashcourse.springboot.repository.network

import com.crashcourse.springboot.model.Bank
import com.crashcourse.springboot.repository.BankRepository
import com.crashcourse.springboot.repository.network.dto.BankList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

@Repository("network")
class NetworkRepository(
    @Autowired private val restTemplate: RestTemplate
) : BankRepository {
    override fun retrieveBanks(): Collection<Bank> {
        val response: ResponseEntity<BankList> = restTemplate.getForEntity("http://54.193.31.159/banks")
        return response.body?.results ?: throw NoSuchElementException("Could not find a bank with account number ")
    }

    override fun retrieveBank(accountNumber: String): Bank {
        TODO("Not yet implemented")
    }

    override fun createBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun updateBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(accountNumber: String) {
        TODO("Not yet implemented")
    }
}