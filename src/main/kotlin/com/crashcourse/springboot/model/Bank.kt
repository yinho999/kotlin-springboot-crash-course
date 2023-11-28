package com.crashcourse.springboot.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Bank(
    // the name of the property in the JSON response
    @JsonProperty("account_number")
    val accountNumber: String,
    @JsonProperty("trust")
    val trust: Double,
    @JsonProperty("default_transaction_fee")
    val transactionFee: Int
)