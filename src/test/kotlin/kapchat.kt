import club.guacamoledragon.plugin.kapchat.Client
import club.guacamoledragon.plugin.ui.ChatRoom
import java.awt.Dimension
import javax.swing.JFrame

fun main(args: Array<String>) {
    val frame = JFrame("Twitch Chat")
    val chatroom = ChatRoom()

    frame.size = Dimension(500, 500)
    frame.contentPane.add(chatroom.panel)
    frame.setLocationRelativeTo(null)
    frame.isVisible = true

    val channel = "bacon_donut"

    val kapchatClient = Client(channel)

    kapchatClient.messageHandler = { msg ->
        chatroom.appendMessage(msg.nick, msg.message, msg.userData.color)
    }

    kapchatClient.connect()
}
