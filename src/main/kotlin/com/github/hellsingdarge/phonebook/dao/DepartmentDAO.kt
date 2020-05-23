package com.github.hellsingdarge.phonebook.dao

import com.github.hellsingdarge.phonebook.Department
import com.google.inject.Inject
import mu.KotlinLogging
import javax.sql.DataSource

class DepartmentDAO
{
    @Inject
    lateinit var connectionPool: DataSource

    private val log = KotlinLogging.logger {}

    fun getAllDepartments(): List<Department>
    {
        val query = "SELECT * FROM Departments"
        val departments = mutableListOf<Department>()

        connectionPool.connection.use { connection ->
            val statement = connection.createStatement()

            statement.use {
                val result = it.executeQuery(query)

                while (result.next())
                {
                    departments.add(
                            Department(
                                    result.getString("name"),
                                    result.getString("phoneNumber")
                            )
                    )
                }
            }
        }

        return departments.toList()
    }

    fun findDepartmentByPhoneNumber(phoneNumber: String): Department?
    {
        log.debug { "Trying to find department by phone number: $phoneNumber" }

        val query = "SELECT name FROM Departments WHERE phoneNumber = ?"
        var department: Department? = null

        connectionPool.connection.use { connection ->
            val statement = connection.prepareStatement(query)

            statement.setString(1, phoneNumber)

            statement.use {

                val result = statement.executeQuery()

                if (result.next())
                {
                    department = Department(result.getString("name"), phoneNumber)
                }
            }
        }

        return department
    }

    fun addDepartment(name: String, phoneNumber: String?)
    {
        log.debug { "Adding new department $name with phone number $phoneNumber" }

        val query = "INSERT INTO Departments VALUES(?, ?)"

        connectionPool.connection.use { connection ->
            val statement = connection.prepareStatement(query)

            statement.setString(1, name)
            statement.setString(2, phoneNumber)

            statement.use {
                it.executeUpdate()
            }
        }
    }

    fun changeName(oldName: String, newName: String)
    {
        log.debug { "Changing name of $oldName to $newName" }

        val query = "UPDATE Departments SET name = ? WHERE name = ?"

        connectionPool.connection.use { connection ->
            val statement = connection.prepareStatement(query)

            statement.setString(1, newName)
            statement.setString(2, oldName)

            statement.use {
                it.executeUpdate()
            }
        }
    }

    fun changePhoneNumber(department: String, newPhoneNumber: String)
    {
        log.debug { "Changing phone number of $department to $newPhoneNumber" }

        val query = "UPDATE Departments SET phoneNumber = ? WHERE name = ?"

        connectionPool.connection.use { connection ->
            val statement = connection.prepareStatement(query)

            statement.setString(1, department)
            statement.setString(2, newPhoneNumber)

            statement.use {
                it.executeUpdate()
            }
        }
    }
}
