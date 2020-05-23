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

    fun findEmployeeByPhoneNumber(phoneNumber: String): List<Employee>
    {
        val query = "SELECT * FROM Employees WHERE (externalNumber = ? OR internalNumber = ? OR homeNumber = ?)"
        val employees = mutableListOf<Employee>()

        connectionPool.connection.use { connection ->
            val statement = connection.prepareStatement(query)

            (1..3).forEach { statement.setString(it, phoneNumber) }

            statement.use {
                val result = it.executeQuery()

                while (result.next())
                {
                    employees.add(
                            Employee(
                                    result.getString("name"),
                                    Department(result.getString("department"), null),
                                    result.getString("internalNumber"),
                                    result.getString("externalNumber"),
                                    result.getString("homeNumber")
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

                statement.setString(1, name)
                statement.setString(2, departmentName)
                statement.setString(3, externalNumber)
                statement.setString(4, internalNumber)
                statement.setString(5, homeNumber)

                statement.use {
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