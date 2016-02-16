package club.guacamoledragon.plugin

import club.guacamoledragon.plugin.kapchat.Client
import club.guacamoledragon.plugin.kapchat.Message
import club.guacamoledragon.plugin.ui.ChatRoom
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class TwitchChat: ToolWindowFactory {
    private val chatroom = ChatRoom()
    // TODO: Don't hardcode the channel n00b
    private val channel = "pianoimproman"
    private var client = Client(channel)

    private val handler = { msg: Message ->
        chatroom.appendMessage(msg.nick, msg.message, msg.userData.color)
    }

    init {
        client.onMessage { msg ->
            chatroom.appendMessage(msg.nick, msg.message, msg.userData.color)
        }

        chatroom.goButton.addActionListener { event ->
            client.onDisconnect {
                client = Client(chatroom.channelField.text)
                client
                        .onMessage(handler)
                        .connect()
            }
        }
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(chatroom.panel, channel, false)
        toolWindow.contentManager.addContent(content)
        client.connect()
    }
}