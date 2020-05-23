package com.github.hellsingdarge.phonebook

import com.github.hellsingdarge.phonebook.dao.EmployeeDAO
import com.google.inject.Injector
import kotlinx.cli.*

@ExperimentalCli
class ArgHandler(args: Array<String>, injector: Injector)
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

    class AddEmployee(private val injector: Injector) : Subcommand("addEmployee", "Add employee")
    {
        val employeeName: String by argument(
                ArgType.String,
                fullName = "name",
                description = "Name of the employee"
        )

        val department: String by argument(
                ArgType.String,
                fullName = "department",
                description = "Name of the department. No spaces (\"Devision 72 \" -> \"Devision72\")"
        )

        val internalNumber: String? by option(
                ArgType.String,
                shortName = "int",
                fullName = "internalNumber",
                description = "Internal phone number"
        )

        val externalNumber: String? by option(
                ArgType.String,
                shortName = "ext",
                fullName = "externalNumber",
                description = "External phone number"
        )

        val homeNumber: String? by option(
                ArgType.String,
                shortName = "home",
                fullName = "homeNumber",
                description = "Home phone number"
        )

        override fun execute()
        {
            val employeeDAO = injector.getInstance(EmployeeDAO::class.java)

            if (!employeeDAO.addEmployee(employeeName, department, internalNumber, externalNumber, homeNumber))
            {
                println("Mayn't add employee that already exists")
                kotlin.system.exitProcess(1);
            }
        }
    }

    class Find : Subcommand("find", "find employee/department by phone number")
    {
        val target: String by argument(
                ArgType.Choice(listOf("employee", "department")),
                fullName = "target",
                description = "What to search"
        )

        val phoneNumber: String by argument(
                ArgType.String,
                fullName = "phoneNumber",
                description = "Search target with this phone number. Be it internal, external or home one"
        )

        override fun execute()
        {
            TODO("Not yet implemented")
        }
    }

    init
    {
        val addEmployee = AddEmployee(injector)
        val find = Find()
        parser.subcommands(addEmployee, find)
        parser.parse(args)
    }
}