package me.nov.sugarscrew.frame.decompiler;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.extensions.rsyntaxarea.DarklafRSyntaxTheme;
import com.github.weisj.darklaf.theme.event.ThemeInstalledListener;
import me.nov.sugarscrew.logging.Log;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;

public class DecompilerTextArea extends RSyntaxTextArea {
  private static final long serialVersionUID = 1L;

  public DecompilerTextArea() {
    this.setSyntaxEditingStyle("text/java");
    this.setCodeFoldingEnabled(true);
    this.setAntiAliasingEnabled(true);
    this.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    this.setEditable(false);
    updateSyntaxTheme();
    LafManager.addThemeChangeListener((ThemeInstalledListener) e -> updateSyntaxTheme());
    addHierarchyListener(e -> {
      if ((e.getChangeFlags() & HierarchyEvent.PARENT_CHANGED) != 0) {
        SwingUtilities.invokeLater(this::updateSyntaxTheme);
      }
    });

    // add spacing to edges
    this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
  }

  private void updateSyntaxTheme() {
    try {
      new DarklafRSyntaxTheme(this).apply(this);
    } catch (Throwable e) {
      e.printStackTrace();
      Log.error(e, "Failed to apply syntax theme");
    }
  }
}
