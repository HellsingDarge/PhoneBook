package com.github.hellsingdarge.phonebook.service

import com.github.hellsingdarge.phonebook.dao.EmployeeDAO
import com.google.inject.Inject
import java.sql.SQLIntegrityConstraintViolationException

class EmployeeService @Inject constructor(private val employeeDAO: EmployeeDAO)
{
    fun addEmployee(name: String, departmentName: String, internalNumber: String?, externalNumber: String?, homeNumber: String?): Boolean
    {
        return try
        {
            employeeDAO.addEmployee(name, departmentName, internalNumber, externalNumber, homeNumber)
            true
        }
        catch (ex: SQLIntegrityConstraintViolationException)
        {
            false
        }
    }
}