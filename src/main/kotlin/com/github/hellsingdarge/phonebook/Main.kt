package com.github.hellsingdarge.phonebook

fun main(args: Array<String>)
{
    val argHandler = ArgHandler(args)

    if (argHandler.listAllDepartments != null)
    {
        val departments = Departments().departments
        departments.forEach { println(it) }
    }
}