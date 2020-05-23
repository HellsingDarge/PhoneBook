package com.github.hellsingdarge.phonebook

data class Employee(
        val name: String,
        val department: Department,
        val internalPhoneNumber: String?,
        val externalPhoneNumber: String?,
        val homePhoneNumber: String?
)
{
    fun fancyPrint(): String
    {
        val result = StringBuilder()
        result.append("* $name - ${department.fancyPrint()}\n")

        if (internalPhoneNumber != null)
        {
            result.append(" - Internal number: $internalPhoneNumber\n")
        }

        if (externalPhoneNumber != null)
        {
            result.append(" - External number: $externalPhoneNumber\n")
        }

        if (homePhoneNumber != null)
        {
            result.append(" - Home number: $homePhoneNumber\n")
        }

        return result.toString()
    }
}