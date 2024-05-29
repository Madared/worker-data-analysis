package com.gentechsolutions.workerDataAnalysis

import com.gentechsolutions.workerDataAnalysis.extensions.normalize
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class NormalizationTests {
    @Test
    fun padding_is_factored_out() {
        val withPadding = " hello "
        val withoutPadding = "hello"
        assertTrue(withoutPadding.normalize() == withPadding.normalize())
    }

    @Test
    fun case_is_factored_out() {
        val upperCase = "HELLO"
        val lowerCase = "hello"
        assertTrue(upperCase.normalize() == lowerCase.normalize())
    }
}