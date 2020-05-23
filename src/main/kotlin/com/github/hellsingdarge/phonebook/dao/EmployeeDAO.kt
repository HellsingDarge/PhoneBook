package com.github.hellsingdarge.phonebook.dao

import com.github.hellsingdarge.phonebook.Department
import com.github.hellsingdarge.phonebook.Employee
import com.google.inject.Inject
import mu.KotlinLogging
import javax.sql.DataSource

class EmployeeDAO
{
    @Inject
    lateinit var connectionPool: DataSource

    private val log = KotlinLogging.logger {}

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
        log.debug { "Trying to find employee by phone number: $phoneNumber" }

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

    fun addEmployee(name: String, departmentName: String, internalNumber: String?, externalNumber: String?, homeNumber: String?)
    {
        log.debug { "Adding new employee. Name: $name, department: $departmentName" }

        val query = "INSERT INTO Employees VALUES(?, ?, ?, ?, ?)"

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
    }

    fun changeName(oldName: String, newName: String)
    {
        log.debug { "Changing name of an employee $oldName to $newName" }

        val query = "UPDATE Employees SET name = ? WHERE name = ?"

        connectionPool.connection.use { connection ->
            val statement = connection.prepareStatement(query)

            statement.setString(1, newName)
            statement.setString(2, oldName)

            statement.use {
                it.executeUpdate()
            }
        }
    }

    fun changeDepartment(name: String, newDepartment: String)
    {
        log.debug { "Changing internal name of $name to $newDepartment" }

        val query = "UPDATE Employees SET department = ?"

        connectionPool.connection.use { connection ->
            val statement = connection.prepareStatement(query)

            statement.setString(1, newDepartment)

            statement.use {
                it.executeUpdate()
            }
        }
    }

    fun changeInternalPhone(name: String, newPhone: String)
    {
        log.debug { "Changing internal name of $name to $newPhone" }

        val query = "UPDATE Employees SET internalPhone = ?"

        connectionPool.connection.use { connection ->
            val statement = connection.prepareStatement(query)

            statement.setString(1, newPhone)

            statement.use {
                it.executeUpdate()
            }
        }
    }

    fun changeExternalPhone(name: String, newPhone: String)
    {
        log.debug { "Changing internal name of $name to $newPhone" }

        val query = "UPDATE Employees SET internalPhone = ?"

        connectionPool.connection.use { connection ->
            val statement = connection.prepareStatement(query)

            statement.setString(1, newPhone)

            statement.use {
                it.executeUpdate()
            }
        }
    }

    fun changeHomePhone(name: String, newPhone: String)
    {
        log.debug { "Changing internal name of $name to $newPhone" }

        val query = "UPDATE Employees SET homePhone = ?"

        connectionPool.connection.use { connection ->
            val statement = connection.prepareStatement(query)

            statement.setString(1, newPhone)

            statement.use {
                it.executeUpdate()
            }
        }
    }
}