package club.guacamoledragon.plugin.ui

import java.awt.BorderLayout
import java.awt.Color
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*
import javax.swing.text.*

class ChatRoom {
    private val styledDocument = DefaultStyledDocument(StyleContext())

    val textPane: JTextPane = JTextPane(styledDocument)
    val channelField: JTextField = JTextField()
    val goButton: JButton = JButton("Go!")
    val panel: JPanel = JPanel(BorderLayout())

    init {
        val caret: DefaultCaret = textPane.caret as DefaultCaret
        caret.updatePolicy = DefaultCaret.ALWAYS_UPDATE

        val channelSelectPanel = JPanel(BorderLayout())
        channelSelectPanel.add(channelField, BorderLayout.CENTER)
        channelSelectPanel.add(goButton, BorderLayout.EAST)

        panel.add(channelSelectPanel, BorderLayout.NORTH)
        panel.add(JScrollPane(textPane), BorderLayout.CENTER)
    }

    fun appendMessage(nick: String, message: String, color: Color) {
        SwingUtilities.invokeLater {
            val set = SimpleAttributeSet()
            set.addAttribute(StyleConstants.Foreground, color)
            styledDocument.insertString(styledDocument.length, nick + ": ", set)
            styledDocument.insertString(styledDocument.length, message + "\n", null)
        }
    }
}