package com.github.hellsingdarge.phonebook.dao

import com.github.hellsingdarge.phonebook.Department
import com.google.inject.Inject
import javax.sql.DataSource

class DepartmentDAO
{
    @Inject
    lateinit var connectionPool: DataSource

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
