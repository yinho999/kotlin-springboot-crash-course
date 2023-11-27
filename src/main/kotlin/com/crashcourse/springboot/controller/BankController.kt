package com.crashcourse.springboot.controller

import com.crashcourse.springboot.model.Bank
import com.crashcourse.springboot.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/banks")
class BankController(private val bankService: BankService) {
    // @ExceptionHandler is a way to handle exceptions in a centralized way
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping("")
    fun getBanks(): Collection<Bank> = bankService.getBanks()

    @GetMapping("/{accountNumber}")
    fun getBank(@PathVariable accountNumber: String): Bank {
        return bankService.getBank(accountNumber)
    }

    @PostMapping("")
    // return Created status code if successful
    @ResponseStatus(HttpStatus.CREATED)
    fun addBank(@RequestBody bank: Bank): Bank {
        return bankService.addBank(bank)
    }

    @PatchMapping("/{accountNumber}")
    fun updateBank(@PathVariable accountNumber: String, @RequestBody bank: Bank): Bank {
        if (accountNumber != bank.accountNumber) {
            throw IllegalArgumentException("Account number in path and request body must match")
        }
        return bankService.updateBank(bank)
    }
}