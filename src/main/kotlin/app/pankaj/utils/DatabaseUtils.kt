package app.pankaj.utils

import org.jetbrains.exposed.sql.statements.api.ExposedConnection
import org.koin.core.component.KoinComponent

object DatabaseUtils : KoinComponent {

    private fun toCountQuery(query: String, criteria: List<String>): String {
        val tables = query.split("from")[1].trim()
        if (criteria.isEmpty()) {
            return "select count(1) as cnt from $tables"
        }
        return "select count(1) as cnt from $tables where ${criteria.joinToString(" and ")}"
    }

    fun toSelectQuery(query: String, criteria: List<String>, pageNo: Long = 1, pageSize: Int = 10): String {
        if (criteria.isEmpty()) {
            return "$query limit $pageSize offset ${(pageNo - 1) * pageSize}"
        }
        return "$query where ${criteria.joinToString(" and ")} limit $pageSize offset ${(pageNo - 1) * pageSize}"
    }

    fun count(connection: ExposedConnection<*>, query: String, criteria: MutableList<String>): Long {
        val rs = connection.prepareStatement(toCountQuery(query, criteria), false).executeQuery()
        rs.next()
        return rs.getLong("cnt")
    }
}
