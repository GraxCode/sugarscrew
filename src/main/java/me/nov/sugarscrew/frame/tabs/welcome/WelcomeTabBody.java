package me.nov.sugarscrew.frame.tabs.welcome;

import me.nov.sugarscrew.Sugarscrew;
import me.nov.sugarscrew.frame.decompiler.DecompilerTextArea;
import me.nov.sugarscrew.translation.Translation;
import org.fife.ui.rtextarea.RTextScrollPane;

import java.awt.*;

public class WelcomeTabBody extends javax.swing.JPanel {

  public WelcomeTabBody(Sugarscrew sc) {
    super(new BorderLayout());
    var textArea = new DecompilerTextArea();
    textArea.setText(Translation.get("tab_welcome_text", sc.getVersion()));
    add(new RTextScrollPane(textArea), BorderLayout.CENTER);
  }
}
