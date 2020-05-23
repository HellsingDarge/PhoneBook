package com.github.hellsingdarge.phonebook

data class Employee(
        val name: String,
        val department: Department,
        val internalPhoneNumber: String?,
        val externalPhoneNumber: String?,
        val homePhoneNumber: String?
)