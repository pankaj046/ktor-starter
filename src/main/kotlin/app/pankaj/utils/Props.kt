package app.pankaj.utils

import app.pankaj.utils.Props.Ktor.serverURL

object Props {
    object Ktor {

        val serverURL = prop(
            local = "http://0.0.0.0:8080", server = "http://0.0.0.0:8080", testServer = "https://example.com"
        )
        val port = prop(local = 8080, server = 80, testServer = 8080)
    }

    object Database {
        val username = prop(local = "", server = "freedb_todo-user")
        val password =  prop(local = "", server = "7?Cw2\$D?aMcHt2y")
        val port =  prop(local = "", server = "3306")
        val database = prop(local = "blogdb", server = "freedb_todo-database")
        val url = prop("localhost", "sql.freedb.tech", "")
    }

    object JWT {
        val secret = prop(local = "secret", server = env("JWT_SECRET"), testServer = env("DEV_JWT_SECRET"))
        val issuer = serverURL
        val audience = serverURL
        const val realm = ""
    }

    object Mail {
        val login = env("MAIL_LOGIN")
        val password = env("MAIL_PASSWORD")

        object SMTP {
            const val host = "smtp.gmail.com"
            const val port = 587
            const val auth = true
            const val starttls = true
        }
    }


    object FireStore {
        val initializeFireStore = env("FIRESTORE_SERVICE_ALLOWED")
        val serviceAccountPath = env("FIRESTORE_SERVICE_ACCOUNT_PATH")
        val databaseUrl = env("FIRESTORE_DATABASE_URL")
    }
}