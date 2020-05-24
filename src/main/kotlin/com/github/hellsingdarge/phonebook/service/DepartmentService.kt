package com.github.hellsingdarge.phonebook.service

import com.github.hellsingdarge.phonebook.dao.DepartmentDAO
import mu.KotlinLogging

class DepartmentService(private val departmentDAO: DepartmentDAO)
{
    private val log = KotlinLogging.logger {}

    fun findDepartmentByPhoneNumber(phoneNumber: String)
    {
        val department = departmentDAO.findDepartmentByPhoneNumber(phoneNumber)

        if (department == null)
        {
            log.info { "No department found with this phone number: $phoneNumber" }
        }
        else
        {
            log.info { department.fancyPrint() }
        }
    }

    fun change(whatToChange: String, oldValue: String, newValue: String)
    {
        when (whatToChange)
        {
            "name" -> departmentDAO.changeName(oldValue, newValue)
            "phoneNumber" -> departmentDAO.changePhoneNumber(oldValue, newValue)
        }
    }
}