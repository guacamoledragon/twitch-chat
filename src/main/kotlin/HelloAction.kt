import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.ui.Messages

class HelloAction : AnAction {
    constructor() : super("Hello")

    override fun actionPerformed(event: AnActionEvent?) {
        val project = event?.getData(PlatformDataKeys.PROJECT);
        Messages.showMessageDialog(project, "Hello world!", "Greeting", Messages.getInformationIcon());
    }
}