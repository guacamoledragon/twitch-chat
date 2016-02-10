import club.guacamoledragon.plugin.kapchat.Client
import club.guacamoledragon.plugin.ui.ChatRoom
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame
import javax.swing.WindowConstants

fun main(args: Array<String>) {
    val frame = JFrame("Twitch Chat")
    val chatroom = ChatRoom()

    frame.size = Dimension(500, 500)
    frame.contentPane.add(chatroom.panel)
    frame.setLocationRelativeTo(null)
    frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    frame.isVisible = true

    val channel = "bacon_donut"

    val kapchatClient = Client(channel)

    kapchatClient.messageHandler = { msg ->
        chatroom.appendMessage(msg.nick, msg.message, msg.userData.color)
    }

    kapchatClient.connect()

    frame.addWindowListener(object: WindowAdapter() {
        override fun windowClosing(e: WindowEvent?) {
            kapchatClient.disconnect()
        }
    })
}

// Part 4: 1080p @ 30fps/3000bps 02/09/2016