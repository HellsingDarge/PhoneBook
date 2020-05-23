package com.github.hellsingdarge.phonebook

import kotlinx.cli.ExperimentalCli
import org.flywaydb.core.Flyway

@ExperimentalCli
fun main(args: Array<String>)
{
    Flyway.configure()
            .dataSource(
                    "jdbc:h2:./PhoneBook",
                    "sa",
                    null
            )
            .load()
//            .repair()
            .migrate()

    val app = Application()
    app.run(args)
}