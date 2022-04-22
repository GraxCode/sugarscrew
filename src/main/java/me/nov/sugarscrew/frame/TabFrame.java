package me.nov.sugarscrew.frame;

import com.github.weisj.darklaf.components.OverlayScrollPane;
import com.github.weisj.darklaf.components.tabframe.JTabFrame;
import com.github.weisj.darklaf.components.tabframe.PanelPopup;
import com.github.weisj.darklaf.components.tabframe.TabFramePopup;
import com.github.weisj.darklaf.iconset.AllIcons;
import com.github.weisj.darklaf.properties.icons.IconLoader;
import com.github.weisj.darklaf.util.Alignment;
import me.nov.sugarscrew.Sugarscrew;
import me.nov.sugarscrew.frame.list.MemberList;
import me.nov.sugarscrew.frame.tabs.EditorTabbedPane;
import me.nov.sugarscrew.frame.tree.ClassTree;
import me.nov.sugarscrew.translation.Translation;

import javax.swing.*;

public class TabFrame extends JTabFrame {
  public final Sugarscrew sugarscrew;
  public final ClassTree tree;
  public final EditorTabbedPane openClasses;
  public final MemberList currentMembers;

  public TabFrame(Sugarscrew sugarscrew) {
    this.sugarscrew = sugarscrew;
    this.tree = new ClassTree(this);
    this.openClasses = new EditorTabbedPane(this);
    this.currentMembers = new MemberList();
    var projectIcon = IconLoader.get(TabFrame.class).getIcon("projectTab.svg");
    var popup = new PanelPopup(Translation.get("tabframe_tree_title"), projectIcon, new OverlayScrollPane(tree));
    this.addTab((TabFramePopup) popup, Translation.get("tabframe_tree_title_short"), projectIcon, Alignment.NORTH_WEST);
    this.openTab((TabFramePopup) popup);

    var membersIcon = AllIcons.Files.Folder.get();
    popup = new PanelPopup(Translation.get("tabframe_members_title"), membersIcon, new OverlayScrollPane(currentMembers));
    this.addTab((TabFramePopup) popup, Translation.get("tabframe_members_title_short"), membersIcon, Alignment.EAST);

    // add member settings

    var memberSettingsIcon = AllIcons.Menu.Settings.get();
    popup = new PanelPopup(Translation.get("tabframe_member_settings_title"), memberSettingsIcon, new OverlayScrollPane(new JTextArea()));
    this.addTab((TabFramePopup) popup, Translation.get("tabframe_member_settings_title_short"), memberSettingsIcon, Alignment.EAST);

    this.setContent(openClasses);

  }
}
