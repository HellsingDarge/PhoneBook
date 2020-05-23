package com.github.hellsingdarge.phonebook.dao

import com.github.hellsingdarge.phonebook.Department
import com.github.hellsingdarge.phonebook.Employee
import java.sql.Connection

class EmployeeDAO(private val dbConnection: Connection)
{
    fun getEmployeesList(): List<Employee>
    {
        val query = "SELECT Employees.*, Departments.phoneNumber as DepartmentPhoneNumber FROM Employees INNER JOIN Departments ON Employees.department = Departments.name"
        val employees = mutableListOf<Employee>()

        dbConnection.use { connection ->
            val statement = connection.createStatement()

            statement.use {
                val result = it.executeQuery(query)

                while (result.next())
                {
                    employees.add(
                            Employee(
                                    name = result.getString("name"),
                                    department = Department(
                                            result.getString("department"),
                                            result.getString("DepartmentPhoneNumber")
                                    ),
                                    internalPhoneNumber = result.getString("internalNumber"),
                                    externalPhoneNumber = result.getString("externalNumber"),
                                    homePhoneNumber = result.getString("homeNumber")
                            )
                    )
                }
            }
        }

        return employees.toList()
    }

    fun addEmployee(employee: Employee)
    {

    }
}