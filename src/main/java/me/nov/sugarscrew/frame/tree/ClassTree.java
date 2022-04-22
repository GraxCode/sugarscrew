package me.nov.sugarscrew.frame.tree;

import com.github.weisj.swingdsl.components.BreadcrumbBar;
import com.github.weisj.swingdsl.components.NavigationListener;
import me.nov.sugarscrew.dex.Descriptors;
import me.nov.sugarscrew.frame.MessageDisplay;
import me.nov.sugarscrew.frame.TabFrame;
import me.nov.sugarscrew.frame.transfer.DexTreeTransferHandler;
import me.nov.sugarscrew.io.Import;
import me.nov.sugarscrew.logging.Log;
import me.nov.sugarscrew.translation.Translation;
import org.jf.dexlib2.dexbacked.DexBackedClassDef;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Collections;

public class ClassTree extends JTree {

  private DexBackedDexFile dex;
  private File currentFile;
  private BreadcrumbBar<ClassTreeNode, String> breadcrumbBar;

  public ClassTree(TabFrame tabFrame) {
    // drag and drop handling
    this.setTransferHandler(new DexTreeTransferHandler(this::tryLoadFile));
    this.setCellRenderer(new ClassTreeCellRenderer());

    // add on double click listener
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
          ClassTreeNode node = (ClassTreeNode) getLastSelectedPathComponent();
          if (node.member != null) {
            tabFrame.openClasses.openClass(node.member);
          }
        }
      }
    });

    // add on right click listener
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        if (evt.getButton() == MouseEvent.BUTTON3) {
          ClassTreeNode node = (ClassTreeNode) getLastSelectedPathComponent();
          // add a popup menu for the right click
          JPopupMenu popup = new JPopupMenu();
          if (node.member != null) {
            var openClass = new JMenuItem(Translation.get("popup_open_class"));
            popup.add(openClass);
            openClass.addActionListener(e -> tabFrame.openClasses.openClass(node.member));
          }
          popup.show(evt.getComponent(), evt.getX(), evt.getY());
        }
      }
    });


    // clear model
    this.setModel(new DefaultTreeModel(new ClassTreeNode("")));

  }

  public void tryLoadFile(File file) {
    try {
      dex = Import.loadDexFile(file);
      currentFile = file;

      generateTreeStructure();
    } catch (Exception e) {
      Log.error(e, "Failed to load file: %s", file.getAbsolutePath());
      MessageDisplay.showException(e, Translation.get("import_failed"), file.getAbsolutePath());
    }
  }

  public void generateTreeStructure() {
    var root = new ClassTreeNode(currentFile.getName());
    var model = new DefaultTreeModel(root);
    for (DexBackedClassDef classDef : dex.getClasses()) {
      String realName = Descriptors.toTypeName(classDef.getType());
      String[] split = realName.split("\\.");
      addToTree(root, split, classDef, 0);
    }
    for (var n : Collections.list(root.depthFirstEnumeration())) {
      ClassTreeNode node = (ClassTreeNode) n;
      if (!node.isLeaf() && node != root) {
        if (node.getChildCount() == 1) {
          ClassTreeNode child = (ClassTreeNode) node.getChildAt(0);
          if (child.member == null) {
            node.combinePackage(child);
          }
        }
      }
      node.sort();
    }


    this.setModel(model);

    SwingUtilities.invokeLater(() -> {
      var path = new TreePath(root.getPath());
      this.setSelectionPath(path);
      this.scrollPathToVisible(path);
    });
  }

  /**
   * Recursive method to add a class to the tree.
   */
  private void addToTree(ClassTreeNode current, String[] splitPath, DexBackedClassDef classDef, int packageIndex) {
    if (packageIndex == splitPath.length - 1) { // last element of splitPath is the class
      // add class
      current.add(new ClassTreeNode(classDef));
    } else {
      // find or create package
      String packageName = splitPath[packageIndex];
      ClassTreeNode packageNode = current.findChild(packageName);
      if (packageNode == null) {
        packageNode = new ClassTreeNode(packageName);
        current.add(packageNode);
      }
      addToTree(packageNode, splitPath, classDef, packageIndex + 1);
    }
  }

  public void selectEntry(DexBackedClassDef classDef) {
    // find the class
    ClassTreeNode node = findClass(classDef);
    if (node != null) {
      var path = new TreePath(node.getPath());
      this.setSelectionPath(path);
      this.scrollPathToVisible(path);
    }
  }

  public void selectEntrySilent(DexBackedClassDef classDef) {
    ClassTreeNode node = findClass(classDef);
    if (node != null) {
      var path = new TreePath(node.getPath());
      this.setSelectionPath(path);
    }
  }

  private ClassTreeNode findClass(DexBackedClassDef classDef) {
    ClassTreeNode root = (ClassTreeNode) this.getModel().getRoot();
    return findClass(root, classDef);
  }

  private ClassTreeNode findClass(ClassTreeNode root, DexBackedClassDef classDef) {
    for (int i = 0; i < root.getChildCount(); i++) {
      ClassTreeNode child = (ClassTreeNode) root.getChildAt(i);
      if (child.member != null && child.member == classDef) {
        return child;
      }
      ClassTreeNode found = findClass(child, classDef);
      if (found != null) {
        return found;
      }
    }
    return null;
  }

  public void setBreadcrumbBar(BreadcrumbBar<ClassTreeNode, String> breadcrumbBar) {
    this.breadcrumbBar = breadcrumbBar;
    breadcrumbBar.addNavigationListener(new NavigationListener<ClassTreeNode, String>() {
      @Override
      public boolean onClick(int i, ClassTreeNode classTreeNode, String s) {
        return false;
      }

      @Override
      public void onClickInPopup(ClassTreeNode node, String item) {
        var path = new TreePath(node.getPath());
        setSelectionPath(path);
        scrollPathToVisible(path);
      }
    });
  }
}
