package app.pankaj.database

import app.pankaj.dao.UsersTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureTables(){
    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            UsersTable
        )
    }
}