package com.github.hellsingdarge.phonebook

import com.github.hellsingdarge.phonebook.dao.EmployeeDAO
import kotlinx.cli.*
import java.lang.IllegalStateException
import java.sql.DriverManager
import java.sql.SQLIntegrityConstraintViolationException

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
                shortName = "internalNumber",
                description = "Internal phone number"
        )

        val externalNumber: String? by option(
                ArgType.String,
                shortName = "externalName",
                description = "External phone number"
        )

        val homeNumber: String? by option(
                ArgType.String,
                shortName = "homeNumber",
                description = "Home phone number"
        )

        override fun execute()
        {
            DriverManager.getConnection("jdbc:h2:./PhoneBook", "sa", null).use {
                val employeeDAO = EmployeeDAO(it)
                try
                {
                    employeeDAO.addEmployee(employeeName, department, internalNumber, externalNumber, homeNumber)
                }
                catch (ex: SQLIntegrityConstraintViolationException)
                {
                    println("Tried to add employee to non existing department. Depeartment: $department")
                }
            }
        }
    }

    init
    {
        parser.subcommands(addEmployee)
        parser.parse(args)
    }
}