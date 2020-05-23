package com.github.hellsingdarge.phonebook.dao

import com.github.hellsingdarge.phonebook.Department
import com.github.hellsingdarge.phonebook.Employee
import com.google.inject.Inject
import java.sql.SQLIntegrityConstraintViolationException
import javax.sql.DataSource

class EmployeeDAO
{
    @Inject
    lateinit var connectionPool: DataSource

    fun getEmployeesList(): List<Employee>
    {
        val query = "SELECT Employees.*, Departments.phoneNumber as DepartmentPhoneNumber FROM Employees INNER JOIN Departments ON Employees.department = Departments.name"
        val employees = mutableListOf<Employee>()

        connectionPool.connection.use { connection ->
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

    fun addEmployee(name: String, departmentName: String, internalNumber: String?, externalNumber: String?, homeNumber: String?): Boolean
    {
        val query = "INSERT INTO Employees VALUES(?, ?, ?, ?, ?)"

        try
        {
            connectionPool.connection.use { connection ->
                val statement = connection.prepareStatement(query)

                statement.use {
                    it.setString(1, name)
                    it.setString(2, departmentName)
                    it.setString(3, externalNumber)
                    it.setString(4, internalNumber)
                    it.setString(5, homeNumber)

                    it.executeUpdate()
                }
            }

            return true
        }
        catch (eg: SQLIntegrityConstraintViolationException)
        {
            return false
        }
    }
}