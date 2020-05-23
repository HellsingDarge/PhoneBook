package com.github.hellsingdarge.phonebook

import kotlinx.cli.*
import java.lang.IllegalStateException

@ExperimentalCli
class ArgHandler(val args: Array<String>)
{
    val addEmployee = AddEmployee()
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

    class AddEmployee : Subcommand("addEmployee", "Add employee. Leavy argument blank to give no value")
    {
        val employeeName: String? by argument(
                ArgType.String,
                fullName = "name",
                description = "Name of the employee"
        )

        val department: String? by argument(
                ArgType.String,
                fullName = "department",
                description = "Name of the department. No spaces (\"Devision 72 \" -> \"Devision72\")"
        )

        val internalNumber: String? by argument(
                ArgType.String,
                fullName = "internalNumber",
                description = "Internal phone number"
        ).optional()

        val externalNumber: String? by argument(
                ArgType.String,
                fullName = "externalName",
                description = "External phone number"
        ).optional()

        val homeNumber: String? by argument(
                ArgType.String,
                fullName = "homeNumber",
                description = "Home phone number"
        ).optional()

        override fun execute()
        {
            println("$employeeName $department $internalNumber $externalNumber $homeNumber")
        }
    }

    init
    {
        parser.subcommands(addEmployee)
        parser.parse(args)
    }
}