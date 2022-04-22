package me.nov.sugarscrew;

import com.github.weisj.darklaf.iconset.AllIcons;
import com.github.weisj.swingdsl.components.BreadcrumbBar;
import com.github.weisj.swingdsl.components.TreeBreadCrumbModel;
import me.nov.sugarscrew.darklaf.DarkLookAndFeel;
import me.nov.sugarscrew.frame.SwingUtils;
import me.nov.sugarscrew.frame.TabFrame;
import me.nov.sugarscrew.frame.tree.ClassTree;
import me.nov.sugarscrew.frame.tree.ClassTreeNode;
import me.nov.sugarscrew.io.Import;
import me.nov.sugarscrew.listener.ExitListener;
import me.nov.sugarscrew.translation.Translation;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class Sugarscrew extends JFrame {
  private static final long serialVersionUID = 1L;

  private TabFrame tabFrame;

  public Sugarscrew() {
    this.initBounds();
    this.setTitle("Sugarscrew");
    this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    this.addWindowListener(new ExitListener(this));
    this.initializeFrame();
    this.initializeMenu();
  }

  public static void main(String[] args) {
    DarkLookAndFeel.init();
    DarkLookAndFeel.setLookAndFeel();
    new Sugarscrew().setVisible(true);
  }


  private void initializeMenu() {
    JMenuBar bar = new JMenuBar();
    JMenu file = new JMenu(Translation.get("menu_file"));

    JMenuItem open = new JMenuItem(Translation.get("menu_file_open"));
    open.setIcon(AllIcons.Action.Add.get());
    open.addActionListener(e -> {
      // open a file chooser that allows files specified by Import.SUPPORTED_FILE_TYPES
      JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
      chooser.setDialogTitle(Translation.get("menu_file_open"));
      chooser.setAcceptAllFileFilterUsed(false);
      chooser.setFileFilter(new Import.ImportFileFilter());
      chooser.setMultiSelectionEnabled(false);
      int returnVal = chooser.showOpenDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        tabFrame.tree.tryLoadFile(chooser.getSelectedFile());
      }
    });
    file.add(open);

    JMenuItem save = new JMenuItem(Translation.get("menu_file_save"));
    save.setIcon(AllIcons.Action.Save.get());
    save.addActionListener(e -> {
    });
    file.add(save);

    JMenuItem saveAs = new JMenuItem(Translation.get("menu_file_save_as"));
    saveAs.setIcon(AllIcons.Action.Save.get());
    saveAs.addActionListener(e -> {
      JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      chooser.setMultiSelectionEnabled(false);
    });
    file.add(saveAs);

    JMenuItem exit = new JMenuItem(Translation.get("menu_file_exit"));
    exit.setIcon(AllIcons.Navigation.Close.get());
    exit.addActionListener(e -> dispatchEvent(new WindowEvent(Sugarscrew.this, WindowEvent.WINDOW_CLOSING)));
    file.add(exit);

    JMenu edit = new JMenu(Translation.get("menu_edit"));

    JMenuItem undo = new JMenuItem(Translation.get("menu_edit_undo"));
    undo.addActionListener(e -> {

    });
    edit.add(undo);

    JMenuItem redo = new JMenuItem(Translation.get("menu_edit_redo"));
    redo.addActionListener(e -> {

    });
    edit.add(redo);

    JMenuItem settings = new JMenuItem(Translation.get("menu_edit_settings"));
    settings.setIcon(AllIcons.Menu.Settings.get());
    settings.addActionListener(e -> {
    });
    edit.add(settings);

    bar.add(file);
    bar.add(edit);

    this.setJMenuBar(bar);
  }

  private void initializeFrame() {
    JPanel content = new JPanel(new BorderLayout());
    tabFrame = new TabFrame(this);

    ClassTree tree = tabFrame.tree;
    TreeBreadCrumbModel<ClassTreeNode, String> breadCrumbModel = new TreeBreadCrumbModel<>(tree, ClassTreeNode::toString);
    BreadcrumbBar<ClassTreeNode, String> breadcrumbBar = new BreadcrumbBar<>(breadCrumbModel);
    tree.setBreadcrumbBar(breadcrumbBar);

    content.add(breadcrumbBar, BorderLayout.NORTH);

    content.add(SwingUtils.withTopLineBorder(tabFrame), BorderLayout.CENTER);
    setContentPane(content);
  }

  private void initBounds() {
    Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    int width = screenSize.width;
    int height = screenSize.height;
    setBounds(0, 0, width, height);
    setMinimumSize(new Dimension(width / 2, height / 2));
  }

  public String getVersion() {
    // get gradle version number
    try {
      return Objects.requireNonNull(Sugarscrew.class.getPackage().getImplementationVersion());
    } catch (NullPointerException e) {
      return "(dev)";
    }
  }
}