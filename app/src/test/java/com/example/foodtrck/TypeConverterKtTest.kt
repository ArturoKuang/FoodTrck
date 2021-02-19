package com.example.foodtrck

import org.junit.Test
import org.junit.Assert
import java.util.Date

class TypeConverterKtTest {

    @Test
    fun convertTrimmedDate() {
        val expected = Date(1613750400000)
        Assert.assertEquals(expected, com.example.foodtrck.utils.convertTrimmedDate(1613750400))
    }
}