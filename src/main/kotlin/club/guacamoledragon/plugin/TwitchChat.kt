package club.guacamoledragon.plugin

import club.guacamoledragon.plugin.kapchat.Client
import club.guacamoledragon.plugin.kapchat.Message
import club.guacamoledragon.plugin.ui.ChatRoom
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import javax.swing.SwingUtilities

class TwitchChat : ToolWindowFactory {
    private val chatroom = ChatRoom()
    private var client = Client("")

    private val handler = { msg: Message ->
        chatroom.appendMessage(msg.nick, msg.message, msg.userData.color)
    }

    private val contentFactory = ContentFactory.SERVICE.getInstance()
    private val content = contentFactory.createContent(chatroom.panel, client.channel, false)

    init {
        chatroom.goButton.addActionListener { event ->
            client.disconnect()

            client = Client(chatroom.channelField.text)
            setupClient(client)
            client.connect()
        }
    }

    private fun setupClient(client: Client) {
        client
            .onConnect {
                updateDisplayName("Connecting...")
            }
            .onJoin {
                updateDisplayName("${client.channel}")
            }
            .onMessage(handler)
            .onDisconnect {
                updateDisplayName("Disconnected from ${client.channel}")
                chatroom.clear()
            }
    }

    private fun updateDisplayName(displayName: String) {
        SwingUtilities.invokeAndWait {
            content.displayName = displayName
        }
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        toolWindow.contentManager.addContent(content)
    }
}