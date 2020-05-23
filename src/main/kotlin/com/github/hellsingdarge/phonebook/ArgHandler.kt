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
            description = "Get all registered departments"
    )

    init
    {
        parser.parse(args)
    }
}