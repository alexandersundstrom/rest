package rest.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import rest.model.db.User


@Service
class MailService {

    @Autowired
    val env: Environment? = null

    @Autowired
    val emailSender: JavaMailSender? = null

    fun sendTemporaryPassword(user: User, psw: String) {
        val message = SimpleMailMessage()
        message.from = env!!.get("mail.from")
        message.setTo(user.email)
        message.subject = env!!.get("mail.subject")
        message.text = """Hi ${user.firstname} ${user.lastname} and welcome to our service!
            |
|An account has been created with a temporary password. First time you login you must change it. 

|Username: ${user.username}
|Password: ${psw}
|
|Regards
|The team""".trimMargin()

        emailSender!!.send(message)
    }
}