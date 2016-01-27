package club.guacamoledragon.plugin

import club.guacamoledragon.plugin.kapchat.Client
import club.guacamoledragon.plugin.ui.ChatRoom
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class TwitchChat: ToolWindowFactory {
    private val chatroom = ChatRoom()
    private val channel = "pianoimproman"
    private val client = Client(channel)

    init {
        client.messageHandler = { msg ->
            chatroom.appendMessage(msg.nick, msg.message, msg.userData.color)
        }
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(chatroom.panel, "", false)
        toolWindow.contentManager.addContent(content)
        client.connect()
    }
}