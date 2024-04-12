package app.pankaj.utils

import app.pankaj.Logger
import jakarta.mail.*
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeBodyPart
import jakarta.mail.internet.MimeMessage
import jakarta.mail.internet.MimeMultipart
import org.koin.core.component.KoinComponent
import kotlin.collections.set


object EmailUtils : KoinComponent {

    private fun sendEmail(
        subject: String,
        body: String,
        to: String,
    ) {
        if (environment == Environment.LOCAl && (Props.Mail.login.isEmpty() || Props.Mail.password.isEmpty())) {
            Logger.info("EmailUtils - $body")
            return
        }

        val properties = System.getProperties()
        properties["mail.smtp.host"] = Props.Mail.SMTP.host
        properties["mail.smtp.port"] = Props.Mail.SMTP.port
        properties["mail.smtp.auth"] = Props.Mail.SMTP.auth
        properties["mail.smtp.starttls.enable"] = Props.Mail.SMTP.starttls

        val authenticator: Authenticator = object : Authenticator() {
            override fun getPasswordAuthentication() = PasswordAuthentication(Props.Mail.login, Props.Mail.password)
        }

        val session = Session.getInstance(properties, authenticator)

        val message = MimeMessage(session)
        message.setFrom(InternetAddress(Props.Mail.login))
        message.addRecipient(Message.RecipientType.TO, InternetAddress(to))
        message.subject = subject

        val htmlPart = MimeBodyPart()
        htmlPart.setContent(
            body, "text/html"
        )
        val multipart = MimeMultipart()
        multipart.addBodyPart(htmlPart)
        message.setContent(multipart)

        Transport.send(message)
    }

    fun sendResetPasswordEmail(
        email: String,
        token: String,
    ) {
        val link = "${Props.Ktor.serverURL}/auth/reset-password?token=$token"

        val resetPasswordTemplate = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="utf-8">
                <title>Reset Password</title>
            </head>
            <body>
                <h2>Reset Password</h2>
                <p>Dear $email,</p>
                <p>We received a request to reset your password. If you did not request this change, please ignore this email.</p>
                <p>To reset your password, please click on the following link:</p>
                <p><a href="$link">Reset Password</a></p>
                <p>This link will expire in 24 hours.</p>
                <p>If you have any questions or need further assistance, please contact our support team.</p>
                <p>Thank you,</p>
                <p>The HireMate Team</p>
            </body>
            </html>
        """.trimIndent()

        sendEmail(
            subject = "Reset password", body = resetPasswordTemplate, to = email
        )
    }

}