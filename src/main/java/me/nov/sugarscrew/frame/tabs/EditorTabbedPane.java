package me.nov.sugarscrew.frame.tabs;

import me.nov.sugarscrew.frame.TabFrame;
import me.nov.sugarscrew.frame.tabs.classes.ClassTabBody;
import me.nov.sugarscrew.frame.tabs.classes.ClassTabComponent;
import me.nov.sugarscrew.frame.tabs.welcome.WelcomeTabBody;
import me.nov.sugarscrew.frame.tabs.welcome.WelcomeTabComponent;
import me.nov.sugarscrew.translation.Translation;
import org.jf.dexlib2.dexbacked.DexBackedClassDef;

public class EditorTabbedPane extends javax.swing.JTabbedPane {
  private static final long serialVersionUID = 1L;
  public final TabFrame tabFrame;

  public EditorTabbedPane(TabFrame tabFrame) {
    super();

    this.tabFrame = tabFrame;

    // add on tab selected listener
    addChangeListener(e -> {
      if (getSelectedComponent() instanceof ClassTabBody) {
        tabFrame.tree.selectEntrySilent(((ClassTabBody) getSelectedComponent()).classDef);
      }
    });

    // add welcome tab
    addTab(Translation.get("tab_welcome_title"), new WelcomeTabBody(tabFrame.sugarscrew));
    setTabComponentAt(0, new WelcomeTabComponent(this));
  }

  public void openClass(DexBackedClassDef classDef) {
    String id = "" + classDef.hashCode();
    // if tab exists, select it
    int existingTab = indexOfTab(id);
    if (existingTab != -1) {
      setSelectedIndex(existingTab);
      return;
    }
    // if tab doesn't exist, create it
    var tab = new ClassTabBody(classDef);

    addTab(id, tab);
    setSelectedComponent(tab);
    int index = indexOfTab(id);
    setTabComponentAt(index, new ClassTabComponent(this, classDef));
  }

  public void removeAllTabs() {
    while (getTabCount() > 0) {
      removeTabAt(0);
    }
  }

  public void removeAllTabsButThis(ClassTabComponent component) {
    while (getTabCount() > 0) {
      if (getTabComponentAt(0) != component)
        removeTabAt(0);
      else
        break;
    }
    while (getTabCount() > 1) {
      removeTabAt(1);
    }
  }

  public void removeAllTabsToTheRight(ClassTabComponent component) {
    while (getTabCount() > 0) {
      if (getTabComponentAt(0) != component)
        removeTabAt(0);
      else
        break;
    }
  }


  public void removeAllTabsToTheLeft(ClassTabComponent component) {
    while (getTabCount() > 0) {
      if (getTabComponentAt(getTabCount() - 1) != component)
        removeTabAt(getTabCount() - 1);
      else
        break;
    }
  }
}
