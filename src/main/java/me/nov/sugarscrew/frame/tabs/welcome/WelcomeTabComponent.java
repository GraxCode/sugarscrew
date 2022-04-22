package me.nov.sugarscrew.frame.tabs.welcome;

import me.nov.sugarscrew.frame.tabs.EditorTabComponent;
import me.nov.sugarscrew.frame.tabs.EditorTabbedPane;
import me.nov.sugarscrew.translation.Translation;

import javax.swing.*;

public class WelcomeTabComponent extends EditorTabComponent {
  public WelcomeTabComponent(EditorTabbedPane editorTabbedPane) {
    super(editorTabbedPane, null, Translation.get("tab_welcome_title"));
  }
}
