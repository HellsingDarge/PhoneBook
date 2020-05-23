package com.github.hellsingdarge.phonebook.service

import com.github.hellsingdarge.phonebook.dao.DepartmentDAO

class DepartmentService(private val departmentDAO: DepartmentDAO)
{
    fun findDepartmentByPhoneNumber(phoneNumber: String)
    {
        val department = departmentDAO.findDepartmentByPhoneNumber(phoneNumber)

        if (department == null)
        {
            println("No department found with this phone number: $phoneNumber")
        }
        else
        {
            println(department.fancyPrint())
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