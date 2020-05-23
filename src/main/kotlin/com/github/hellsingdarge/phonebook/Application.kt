package com.github.hellsingdarge.phonebook

import com.github.hellsingdarge.phonebook.dao.DepartmentDAO
import com.github.hellsingdarge.phonebook.dao.EmployeeDAO
import com.github.hellsingdarge.phonebook.guicemodules.ConnectionPoolModule
import com.google.inject.Guice
import kotlinx.cli.ExperimentalCli
import mu.KotlinLogging

@ExperimentalCli
class Application
{
    private val log = KotlinLogging.logger {}

    fun run(args: Array<String>)
    {
        log.debug { "Passed arguments: ${args.joinToString(",")}" }

        val injector = Guice.createInjector(ConnectionPoolModule())
        val argHandler = ArgHandler(args, injector)

        if (argHandler.listAllDepartments != null)
        {
            val departmentsDAO = injector.getInstance(DepartmentDAO::class.java)

            println("Departments and their phone number (if present)")
            departmentsDAO.getAllDepartments().forEach { println("* ${it.fancyPrint()}") }
        }

        if (argHandler.listEmployeesInfo != null)
        {
            val employeeDAO = injector.getInstance(EmployeeDAO::class.java)

            println("Employees and their department and phone numbers")
            employeeDAO.getEmployeesList().forEach { println(it.fancyPrint()) }
        }
    }
}