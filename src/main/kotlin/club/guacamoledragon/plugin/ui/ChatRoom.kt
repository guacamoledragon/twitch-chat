package club.guacamoledragon.plugin.ui

import java.awt.BorderLayout
import java.awt.Color
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextPane
import javax.swing.SwingUtilities
import javax.swing.text.*

class ChatRoom {
    private val styledDocument = DefaultStyledDocument(StyleContext())

    val textPane: JTextPane = JTextPane(styledDocument)
    val panel: JPanel = JPanel(BorderLayout())

    init {
        val caret: DefaultCaret = textPane.caret as DefaultCaret
        caret.updatePolicy = DefaultCaret.ALWAYS_UPDATE
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