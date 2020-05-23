package com.github.hellsingdarge.phonebook.service

import com.github.hellsingdarge.phonebook.dao.EmployeeDAO
import com.google.inject.Inject
import java.sql.SQLIntegrityConstraintViolationException

class EmployeeService @Inject constructor(private val employeeDAO: EmployeeDAO)
{
    fun addEmployee(name: String, departmentName: String, internalNumber: String?, externalNumber: String?, homeNumber: String?)
    {
        try
        {
            employeeDAO.addEmployee(name, departmentName, internalNumber, externalNumber, homeNumber)
            println("Successfully added new employee: $name")
        }
        catch (ex: SQLIntegrityConstraintViolationException)
        {
            println("Can't add employee that's already registered. Employee name: $name")
        }
    }

    fun findEmployeeByPhoneNumber(phoneNumber: String)
    {
        val employees = employeeDAO.findEmployeeByPhoneNumber(phoneNumber)

        if (employees.isEmpty())
        {
            println("No employees found with this phone number: $phoneNumber")
        }
        else
        {
            employees.forEach {
                println(it.fancyPrint())
            }
        }
    }

    fun change(whatToChange: String, newValue: String)
    {
        when (whatToChange)
        {
            "name" -> employeeDAO.changeName(newValue)
            "department" ->
                try
                {
                    employeeDAO.changeDepartment(newValue)
                }
                catch (ex: SQLIntegrityConstraintViolationException)
                {
                    println("Can't change department to know which doesn't exist")
                }

            "internal" -> employeeDAO.changeInternalPhone(newValue)
            "external" -> employeeDAO.changeExternalPhone(newValue)
            "home" -> employeeDAO.changeHomePhone(newValue)
        }
    }
}