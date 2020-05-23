package com.github.hellsingdarge.phonebook

data class Department(val name: String, val phoneNumber: String?)
{
    override fun toString(): String
    {
        val number = if (phoneNumber != null) ": $phoneNumber" else ""
        return "$name$number"
    }
}