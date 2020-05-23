package com.github.hellsingdarge.phonebook

import com.github.hellsingdarge.phonebook.dao.DepartmentDAO
import com.github.hellsingdarge.phonebook.dao.EmployeeDAO
import kotlinx.cli.ExperimentalCli
import org.flywaydb.core.Flyway
import java.sql.DriverManager

@ExperimentalCli
fun main(args: Array<String>)
{
    Flyway.configure()
            .dataSource(
                    "jdbc:h2:./PhoneBook",
                    "sa",
                    null
            )
            .load()
            .migrate()

    val argHandler = ArgHandler(args)
    val connection = DriverManager.getConnection("jdbc:h2:./PhoneBook", "sa", null)


    if (argHandler.listAllDepartments != null)
    {
        val departmentsDAO = DepartmentDAO(connection)

        println("Departments and their phone number (if present)")
        departmentsDAO.getAllDepartments().forEach { println("* $it") }
    }

    if (argHandler.listEmployeesInfo != null)
    {
        val employeeDAO = EmployeeDAO(connection)

        println("Employees and their department and phone numbers")
        employeeDAO.getEmployeesList().forEach {
            println("* ${it.name} - ${it.department}")

            if (it.internalPhoneNumber != null)
            {
                println(" - Internal number: ${it.internalPhoneNumber}")
            }

            if (it.externalPhoneNumber != null)
            {
                println(" - External number: ${it.externalPhoneNumber}")
            }

            if (it.homePhoneNumber != null)
            {
                println(" - Home number: ${it.homePhoneNumber}")
            }
        }
    }
}