package com.github.hellsingdarge.phonebook

data class Department(val name: String, val phoneNumber: String?)
{
    fun fancyPrint(): String
    {
        val number = if (phoneNumber != null) ": $phoneNumber" else ""
        return "$name$number"
    }
}