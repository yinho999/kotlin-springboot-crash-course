package com.crashcourse.springboot.controller

import com.crashcourse.springboot.model.Bank
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post

// @SpringBootTest will initialize the entire Spring application context for the test, expensive
@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest(
    // MockMvc is a class that allows us to make requests to our application without actually starting up the server
    @Autowired
    private val mockMvc: MockMvc,
    @Autowired
    private val objectMapper: ObjectMapper
) {

    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should return all banks`() {
            // arrange

            // act/assert
            mockMvc.get(baseUrl).andDo { print() }.andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                // first element in the array has accountNumber 1234
                jsonPath("$[0].accountNumber") { value("1234") }
            }
        }
    }

    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should return the bank with the given account number`() {
            // arrange
            val accountNumber = 1234

            // act / assert
            mockMvc.get("$baseUrl/$accountNumber").andDo { print() }.andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                // first element in the array has accountNumber 1234
                jsonPath("$.trust") { value("3.14") }
                jsonPath("$.transactionFee") { value("17") }
            }

        }

        @Test
        fun `should return NOT FOUND if the account number does not exist`() {
            // arrange
            val accountNumber = "does_not_exist"

            // act / assert
            mockMvc.get("$baseUrl/$accountNumber").andDo { print() }.andExpect {
                status { isNotFound() }
            }

        }
    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {
        @Test
        fun `should add the new bank`() {
            // arrange
            val newBank = Bank("acc123", 3.14, 17)

            // act
            val response = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }
            // assert
            response.andDo { print() }.andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON)
                    // compare the response body with the newBank object
                    json(objectMapper.writeValueAsString(newBank))
                }
            }

            // assert that the bank was added to the repository
            mockMvc.get("$baseUrl/${newBank.accountNumber}").andExpect {
                content {
                    json(objectMapper.writeValueAsString(newBank))
                }
            }

        }

        @Test
        fun `should return BAD REQUEST if bank with given account number already exists`() {
            // arrange
            val invalidBank = Bank("1234", 1.0, 1)

            // act
            val response = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
            // assert
            response.andDo { print() }.andExpect {
                status { isBadRequest() }
            }

        }

    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBank {
        @Test
        fun `should update an existing bank`() {
            // arrange
            val updatedBank = Bank("1234", 1.0, 1)

            // act
            val response = mockMvc.patch("$baseUrl/${updatedBank.accountNumber}") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }
            // assert
            response.andDo { print() }.andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(updatedBank))
                }
            }

            // assert that the bank was updated
            mockMvc.get("$baseUrl/${updatedBank.accountNumber}").andExpect {
                content {
                    json(objectMapper.writeValueAsString(updatedBank))
                }
            }


        }

        @Test
        fun `should return BAD REQUEST if the account number in path does not match the account number in the bank`() {
            // arrange
            val invalidBank = Bank("1234", 1.0, 1)

            // act
            val response = mockMvc.patch("$baseUrl/does_not_exist") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
            // assert
            response.andDo { print() }.andExpect {
                status { isBadRequest() }
            }

        }

        @Test
        fun `should return NOT FOUND if the account number does not exist`() {
            // arrange
            val invalidBank = Bank("does_not_exist", 1.0, 1)

            // act
            val response = mockMvc.patch("$baseUrl/${invalidBank.accountNumber}") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
            // assert
            response.andDo { print() }.andExpect {
                status { isNotFound() }
            }

        }

    }


}