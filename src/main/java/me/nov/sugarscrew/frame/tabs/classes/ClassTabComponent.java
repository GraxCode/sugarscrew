package me.nov.sugarscrew.frame.tabs.classes;

import com.github.weisj.darklaf.properties.icons.IconLoader;
import me.nov.sugarscrew.dex.Descriptors;
import me.nov.sugarscrew.frame.tabs.EditorTabComponent;
import me.nov.sugarscrew.frame.tabs.EditorTabbedPane;
import me.nov.sugarscrew.frame.tree.ClassTreeCellRenderer;
import me.nov.sugarscrew.translation.Translation;
import org.jf.dexlib2.dexbacked.DexBackedClassDef;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClassTabComponent extends EditorTabComponent {

  public ClassTabComponent(EditorTabbedPane editorTabbedPane, DexBackedClassDef classDef) {
    super(editorTabbedPane, IconLoader.get(ClassTreeCellRenderer.class).getIcon("class.svg"), Descriptors.getSimpleName(classDef));

    // add right click listener
    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
          // add popup menu
          var popupMenu = new JPopupMenu();
          var tabClose = new JMenuItem(Translation.get("editor_tab_close"));
          int index = editorTabbedPane.indexOfTabComponent(ClassTabComponent.this);
          tabClose.addActionListener(l -> editorTabbedPane.removeTabAt(index));
          popupMenu.add(tabClose);

          var tabCloseAll = new JMenuItem(Translation.get("editor_tab_close_all"));
          tabCloseAll.addActionListener(l -> editorTabbedPane.removeAllTabs());
          popupMenu.add(tabCloseAll);

          var tabCloseAllButThis = new JMenuItem(Translation.get("editor_tab_close_other"));
          tabCloseAllButThis.addActionListener(l -> editorTabbedPane.removeAllTabsButThis(ClassTabComponent.this));
          popupMenu.add(tabCloseAllButThis);


          var tabCloseToTheRight = new JMenuItem(Translation.get("editor_tab_close_right"));
          tabCloseToTheRight.addActionListener(l -> editorTabbedPane.removeAllTabsToTheRight(ClassTabComponent.this));
          popupMenu.add(tabCloseToTheRight);

          var tabCloseToTheLeft = new JMenuItem(Translation.get("editor_tab_close_left"));
          tabCloseToTheLeft.addActionListener(l -> editorTabbedPane.removeAllTabsToTheLeft(ClassTabComponent.this));
          popupMenu.add(tabCloseToTheLeft);

          popupMenu.add(new JSeparator());

          var tabSelectInTree = new JMenuItem(Translation.get("editor_tab_select_in_tree"));
          tabSelectInTree.addActionListener(l -> editorTabbedPane.tabFrame.tree.selectEntry(classDef));
          popupMenu.add(tabSelectInTree);

          popupMenu.show(ClassTabComponent.this, 0, getHeight());
        } else {
          editorTabbedPane.setSelectedIndex(editorTabbedPane.indexOfTabComponent(ClassTabComponent.this));
        }
      }
    });
  }
}
