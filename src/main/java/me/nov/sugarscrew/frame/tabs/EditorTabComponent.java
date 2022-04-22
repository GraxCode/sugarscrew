package me.nov.sugarscrew.frame.tabs;

import com.github.weisj.darklaf.components.CloseButton;
import me.nov.sugarscrew.translation.Translation;

import javax.swing.*;
import java.awt.*;

public class EditorTabComponent extends JPanel {

  public EditorTabComponent(EditorTabbedPane editorTabbedPane, Icon iconSrc, String labelSrc) {
    super(new FlowLayout(FlowLayout.LEFT, 0, 0));

    setOpaque(false);
    setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));

    var icon = new JLabel(iconSrc);
    icon.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
    add(icon);

    var label = new JLabel(labelSrc);
    label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
    add(label);

    var closeButton = new CloseButton();
    closeButton.addActionListener(l -> editorTabbedPane.removeTabAt(editorTabbedPane.indexOfTabComponent(this)));
    closeButton.setToolTipText(Translation.get("editor_tab_close"));
    add(closeButton);
  }
}
