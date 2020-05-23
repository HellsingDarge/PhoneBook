package com.github.hellsingdarge.phonebook

import com.github.hellsingdarge.phonebook.dao.DepartmentDAO
import com.github.hellsingdarge.phonebook.dao.EmployeeDAO
import com.github.hellsingdarge.phonebook.service.DepartmentService
import com.github.hellsingdarge.phonebook.service.EmployeeService
import com.google.inject.Injector
import kotlinx.cli.*
import org.h2.mvstore.tx.TransactionStore

@ExperimentalCli
class ArgHandler(args: Array<String>, private val injector: Injector)
{
    private val parser = ArgParser("PhoneBook.jar", true)

    val listAllDepartments: Boolean? by parser.option(
            ArgType.Boolean,
            fullName = "listAllDepartments",
            description = "List all registered departments' names (if present)"
    )

    val listEmployeesInfo: Boolean? by parser.option(
            ArgType.Boolean,
            fullName = "listEmployeesInfo",
            description = "List name and departments of the employee. Optionally also lists internal, extranal and home phone numbers"
    )

    inner class AddEmployee : Subcommand("addEmployee", "Add employee")
    {
        val employeeName: String by argument(
                ArgType.String,
                fullName = "name",
                description = "Name of the employee"
        )

        val department: String by argument(
                ArgType.String,
                fullName = "department",
                description = "Name of the department. No spaces (\"Devision 72 \" -> \"Devision72\")"
        )

        val internalNumber: String? by option(
                ArgType.String,
                shortName = "int",
                fullName = "internalNumber",
                description = "Internal phone number"
        )

        val externalNumber: String? by option(
                ArgType.String,
                shortName = "ext",
                fullName = "externalNumber",
                description = "External phone number"
        )

        val homeNumber: String? by option(
                ArgType.String,
                shortName = "home",
                fullName = "homeNumber",
                description = "Home phone number"
        )

        override fun execute()
        {
            val employeeDAO = injector.getInstance(EmployeeDAO::class.java)
            val employeeService = EmployeeService(employeeDAO)
            employeeService.addEmployee(employeeName, department, internalNumber, externalNumber, homeNumber)
        }
    }

    inner class AddDepartment : Subcommand("addDepartment", "Add department with specified name and optionally phone number")
    {
        val departmentName: String by argument(
                ArgType.String,
                fullName = "name",
                description = "Name of a department to add"
        )

        val phoneNumber: String? by argument(
                ArgType.String,
                fullName = "phoneNumber",
                description = "Phone number of a department"
        ).optional()

        override fun execute()
        {
            val departmentDAO = injector.getInstance(DepartmentDAO::class.java)
            departmentDAO.addDepartment(departmentName, phoneNumber)
        }
    }

    inner class Find : Subcommand("find", "find employee/department by phone number")
    {
        val target: String by argument(
                ArgType.Choice(listOf("employee", "department")),
                fullName = "target",
                description = "What to search"
        )

        val phoneNumber: String by argument(
                ArgType.String,
                fullName = "phoneNumber",
                description = "Search target with this phone number. Be it internal, external or home one"
        )

        override fun execute()
        {
            when (target)
            {
                "employee" ->
                {
                    val employeeDAO = injector.getInstance(EmployeeDAO::class.java)
                    val employeeService = EmployeeService(employeeDAO)
                    employeeService.findEmployeeByPhoneNumber(phoneNumber)
                }

                "department" ->
                {
                    val departmentDAO = injector.getInstance(DepartmentDAO::class.java)
                    val departmentsService = DepartmentService(departmentDAO)
                    departmentsService.findDepartmentByPhoneNumber(phoneNumber)
                }
            }
        }
    }

    inner class ChangeEmployeeInfo : Subcommand("changeEmployeeInfo", "Change name or phone number of employee/department")
    {
        val employeeName: String by argument(
                ArgType.String,
                fullName = "name",
                description = "Name of employee whose info to change"
        )

        val whatToChange: String by argument(
                ArgType.Choice(listOf("name", "department", "internalPhone", "externalPhone", "homePhone")),
                fullName = "whatToChange",
                description = "What to change"
        )

        val newValue: String by argument(
                ArgType.String,
                fullName = "newValue",
                description = "New valuew for phone number or name"
        )

        override fun execute()
        {
            val employeeDAO = injector.getInstance(EmployeeDAO::class.java)
            val employeeService = EmployeeService(employeeDAO)
            employeeService.change(name, whatToChange, newValue)
        }
    }

    inner class ChangeDepartmentInfo : Subcommand("changeDepartmentInfo", "Change name or phone number of a department")
    {
        val target: String by argument(
                ArgType.Choice(listOf("name", "phoneNumber")),
                fullName = "target",
                description = "What to change"
        )

        val oldValue: String by argument(
                ArgType.String,
                fullName = "oldValue",
                description = "Either old name of department or department's name to change phone number of"
        )

        val newValue: String by argument(
                ArgType.String,
                fullName = "newValue",
                description = """
                            | New valuew of name or phone number.
                            | NOTE: changing name changes info of all affected employees
                            | NOTE: DELETING DEPARTMENT ISN'T POSSIBLE
                            |""".trimMargin()
        )

        override fun execute()
        {
            val departmentDAO = injector.getInstance(DepartmentDAO::class.java)
            val departmentsService = DepartmentService(departmentDAO)
            departmentsService.change(target, oldValue, newValue)
        }
    }

    init
    {
        val addEmployee = AddEmployee()
        val addDepartment = AddDepartment()
        val changeEmployeeInfo = ChangeEmployeeInfo()
        val changeDepartmentInfo = ChangeDepartmentInfo()
        val find = Find()

        parser.subcommands(
                addEmployee,
                addDepartment,
                changeEmployeeInfo,
                changeDepartmentInfo,
                find
        )
        parser.parse(args)
    }
}