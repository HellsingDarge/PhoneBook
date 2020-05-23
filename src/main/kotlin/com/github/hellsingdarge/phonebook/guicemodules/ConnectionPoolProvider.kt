package com.github.hellsingdarge.phonebook.guicemodules

import com.google.inject.AbstractModule
import com.google.inject.Provider
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

class ConnectionPoolProvider : Provider<DataSource>
{
    override fun get(): DataSource
    {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:h2:./PhoneBook"
        config.username = "sa"
        config.password = null
        return HikariDataSource(config)
    }
}

class ConnectionPoolModule : AbstractModule()
{
    override fun configure()
    {
        bind(DataSource::class.java).toProvider(ConnectionPoolProvider::class.java)
    }
}
