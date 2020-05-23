package com.github.hellsingdarge.phonebook

import com.github.hellsingdarge.phonebook.dao.DepartmentDAO
import com.github.hellsingdarge.phonebook.dao.EmployeeDAO
import com.github.hellsingdarge.phonebook.guicemodules.ConnectionPoolModule
import com.google.inject.Guice

class Application
{
    fun run(args: Array<String>)
    {
        val injector = Guice.createInjector(ConnectionPoolModule())
        val argHandler = ArgHandler(args, injector)

        if (argHandler.listAllDepartments != null)
        {
            val departmentsDAO = injector.getInstance(DepartmentDAO::class.java)

            println("Departments and their phone number (if present)")
            departmentsDAO.getAllDepartments().forEach { println("* $it") }
        }

        if (argHandler.listEmployeesInfo != null)
        {
            val employeeDAO = injector.getInstance(EmployeeDAO::class.java)

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
}