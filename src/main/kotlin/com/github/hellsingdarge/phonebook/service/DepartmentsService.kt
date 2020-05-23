package com.github.hellsingdarge.phonebook.service

import com.github.hellsingdarge.phonebook.dao.DepartmentDAO

class DepartmentsService(private val departmentDAO: DepartmentDAO)
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
}