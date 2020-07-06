package rest.mail

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service


@Service
class MailService {

    @Autowired
    val env: Environment? = null
    @Autowired
    val emailSender: JavaMailSender? = null


    fun sendTemporaryPassword(psw: String, name: String, email: String) {
        val message = SimpleMailMessage()
        message.from = env!!.get("mail.from")
        message.setTo(email)
        message.subject =  env!!.get("mail.subject")
        message.text = "An account has been created for ${name} with a temporary password: ${psw}"

        emailSender!!.send(message)
    }
}