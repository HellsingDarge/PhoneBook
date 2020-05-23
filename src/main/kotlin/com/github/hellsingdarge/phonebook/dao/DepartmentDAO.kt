package com.github.hellsingdarge.phonebook.dao

import com.github.hellsingdarge.phonebook.Department
import java.sql.Connection

class DepartmentDAO(private val dbConnection: Connection)
{
    fun getAllDepartments(): List<Department>
    {
        val query = "SELECT * FROM Departments"
        val departments = mutableListOf<Department>()

        dbConnection.use { connection ->
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
}
