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
}
