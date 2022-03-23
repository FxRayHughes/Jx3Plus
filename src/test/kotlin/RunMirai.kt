package org.example.mirai.plugin

import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart


@ConsoleExperimentalApi
suspend fun main() {
    sendMail("1523165110@qq.com", "你好，这是一封测试邮件，无需回复。", "测试邮件")
}

fun sendMail(to: String?, text: String?, title: String?): Boolean {
    val from = "rayhughes@yeah.net" // 发件人邮箱地址
    val user = "rayhughes@yeah.net" // 发件人称号，同邮箱地址
    val password = "ZHSEEFDZTFFKOCIT" // 发件人邮箱客户端授权码
    val props = Properties()
    props.setProperty("mail.smtp.host", "smtp.yeah.net") // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
    props["mail.smtp.host"] = "smtp.yeah.net" // 需要经过授权，也就是用户名和密码的校验，这样才能通过验证（一定要有这一条）
    props["mail.smtp.auth"] = "true" // 用刚刚设置好的props对象构建一个session
    val session = Session.getDefaultInstance(props) // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
    // 用（你可以在控制台（console)上看到发送邮件的过程）
    session.debug = true // 用session为参数定义消息对象
    val message = MimeMessage(session) // 加载发件人地址
    try {
        message.setFrom(InternetAddress(from))
        message.addRecipient(Message.RecipientType.TO, InternetAddress(to)) // 加载收件人地址
        message.subject = title // 加载标题
        val multipart: Multipart = MimeMultipart() // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
        val contentPart: BodyPart = MimeBodyPart() // 设置邮件的文本内容
        contentPart.setContent(text, "text/html;charset=utf-8")
        multipart.addBodyPart(contentPart)
        message.setContent(multipart)
        message.saveChanges() // 保存变化
        val transport = session.getTransport("smtp") // 连接服务器的邮箱
        transport.connect("smtp.yeah.net", user, password) // 把邮件发送出去
        transport.sendMessage(message, message.allRecipients)
        transport.close()
    } catch (e: MessagingException) {
        e.printStackTrace()
        return false
    }
    return true
}