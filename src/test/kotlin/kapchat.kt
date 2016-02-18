import club.guacamoledragon.plugin.kapchat.Client
import club.guacamoledragon.plugin.kapchat.Message
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

    var kapchatClient: Client = Client("MrsViolence")

    val onMessageHandler = { msg: Message ->
        chatroom.appendMessage(msg.nick, msg.message, msg.userData.color)
    }

    val onDisconnectHandler: () -> Unit = {
        kapchatClient = Client(chatroom.channelField.text)
        kapchatClient
                .onMessage(onMessageHandler)
                .connect()
    }


    kapchatClient
            .onMessage(onMessageHandler)
            .onDisconnect(onDisconnectHandler)
            .connect()

    chatroom.goButton.addActionListener { event ->
        kapchatClient.disconnect()
    }

    frame.addWindowListener(object: WindowAdapter() {
        override fun windowClosing(e: WindowEvent?) {
            kapchatClient.disconnect()
        }
    })
}

// Part 4: 1080p @ 30fps/3000bps 02/09/2016
// Part 5: 1080p @ 30fps/3000bps 02/11/2016
// Part 6: 1080p @ 30fps/3000bps 02/11/2016
// Part 7: 1080p @ 30fps/3000bps 02/11/2016