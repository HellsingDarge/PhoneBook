package com.github.hellsingdarge.phonebook

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import java.lang.IllegalStateException

class ArgHandler(val args: Array<String>)
{
    private val parser = ArgParser("PhoneBook.jar", true)

    val listAllDepartments: Boolean? by parser.option(
            ArgType.Boolean,
            fullName = "listAllDepartments",
            description = "List all registered departments' names (if present)"
    )

    val listEmployeesInfo: Boolean? by parser.option(
            ArgType.Boolean,
            fullName = "listEmployeesInfo",
            description = "List name and departments of the employee. Optionally also lists internal, extranal and home phone numbers"
    )

    init
    {
        parser.parse(args)
    }
}