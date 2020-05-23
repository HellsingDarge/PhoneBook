package com.github.hellsingdarge.phonebook

import kotlinx.cli.ExperimentalCli

@ExperimentalCli
fun main(args: Array<String>)
{
    val argHandler = ArgHandler(args)
    val departments = listOf(
            Department("Division 66", "123456789"),
            Department("Division 79", "456789132"),
            Department("Division 42", "321654897"),
            Department("SET", "753869421"),
            Department("Dispatch", "159487263"),
            Department("ONIGRU", null)

    )

    val employees = listOf(
            Employee("Anashel", departments[0], null, null, null),
            Employee("Aphelion", departments[3], null, "111", null),
            Employee("Miller", departments[4], "55555555", "453", "78945632111")
    )

    if (argHandler.listAllDepartments != null)
    {
        println("Departments and their phone numbers:")
        departments.forEach { println(" * $it") }
    }

    if (argHandler.listEmployeesInfo != null)
    {
        println("Employees:")
        employees.forEach {
            println(" * ${it.name} - ${it.department}")

            if (it.externalPhoneNumber != null)
            {
                println("  - External number: ${it.externalPhoneNumber}")
            }

            if (it.internalPhoneNumber != null)
            {
                println("  - Internal number: ${it.internalPhoneNumber}")
            }

            if (it.homePhoneNumber != null)
            {
                println("  - Home number: ${it.homePhoneNumber}")
            }
        }
    }
}