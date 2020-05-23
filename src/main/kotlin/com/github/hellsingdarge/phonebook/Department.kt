package com.github.hellsingdarge.phonebook

data class Department(val name: String, val phoneNumber: String)
{
    override fun toString(): String
    {
        return "$name: $phoneNumber"
    }
}