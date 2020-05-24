package com.github.hellsingdarge.phonebook.service

import com.github.hellsingdarge.phonebook.dao.EmployeeDAO
import com.google.inject.Inject
import mu.KotlinLogging
import java.sql.SQLIntegrityConstraintViolationException

class EmployeeService @Inject constructor(private val employeeDAO: EmployeeDAO)
{
    private val log = KotlinLogging.logger {}

    fun addEmployee(name: String, departmentName: String, internalNumber: String?, externalNumber: String?, homeNumber: String?)
    {
        try
        {
            employeeDAO.addEmployee(name, departmentName, internalNumber, externalNumber, homeNumber)
            log.info { "Successfully added new employee: $name" }
        }
        catch (ex: SQLIntegrityConstraintViolationException)
        {
            log.error { "Can't add employee that's already registered. Employee name: $name" }
        }
    }

    fun findEmployeeByPhoneNumber(phoneNumber: String)
    {
        val employees = employeeDAO.findEmployeeByPhoneNumber(phoneNumber)

        if (employees.isEmpty())
        {
            log.info { "No employees found with this phone number: $phoneNumber" }
        }
        else
        {
            employees.forEach {
                log.info { it.fancyPrint() }
            }
        }
    }

    fun change(name: String, whatToChange: String, newValue: String)
    {
        when (whatToChange)
        {
            "name" -> employeeDAO.changeName(name, newValue)
            "department" ->
                try
                {
                    employeeDAO.changeDepartment(name, newValue)
                }
                catch (ex: SQLIntegrityConstraintViolationException)
                {
                    log.error { "Can't change name of a department that doesn't exist" }
                }

            "internal" -> employeeDAO.changeInternalPhone(name, newValue)
            "external" -> employeeDAO.changeExternalPhone(name, newValue)
            "home" -> employeeDAO.changeHomePhone(name, newValue)
        }
    }
}