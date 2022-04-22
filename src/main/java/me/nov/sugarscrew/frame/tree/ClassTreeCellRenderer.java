package me.nov.sugarscrew.frame.tree;

import com.github.weisj.darklaf.properties.icons.IconLoader;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class ClassTreeCellRenderer extends DefaultTreeCellRenderer {

  private IconLoader icons = IconLoader.get(ClassTreeCellRenderer.class);
  private Icon classIcon = icons.getIcon("class.svg");
  private Icon packageIcon = icons.getIcon("package.svg");

  @Override
  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
    if (node instanceof ClassTreeNode) {
      ClassTreeNode ctn = (ClassTreeNode) node;
      if (ctn.hasMember()) {
        setIcon(classIcon);
      } else {
        setIcon(packageIcon);
      }
    }
    setText(node.toString());
    return this;
  }

  @Override
  public Font getFont() {
    return new Font(Font.SANS_SERIF, Font.PLAIN, new JLabel().getFont().getSize());
  }
}
