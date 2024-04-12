package app.pankaj.database

import app.pankaj.utils.Props
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*

fun Application.configureDatabases() {
    if (Props.Database.username.isNotEmpty()) {
        Database.connect(
            url = "jdbc:mysql://${Props.Database.url}:${Props.Database.port}/${Props.Database.database}",
            driver = "com.mysql.cj.jdbc.Driver",
            user = Props.Database.username,
            password = Props.Database.password
        )
    } else {
        Database.connect("jdbc:sqlite:database.sqlite", "org.sqlite.JDBC")
    }
}
