package me.nov.sugarscrew.frame.tree;

import me.nov.sugarscrew.dex.Descriptors;
import org.jf.dexlib2.dexbacked.DexBackedClassDef;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class ClassTreeNode extends DefaultMutableTreeNode implements Comparator<TreeNode> {
  public DexBackedClassDef member;

  public String text;

  public ClassTreeNode(DexBackedClassDef member) {
    super(member.getType());
    this.member = member;
    this.text = generateText();
  }

  public ClassTreeNode(String text) {
    super(text);
    this.text = Objects.requireNonNull(text);
  }

  public boolean hasMember() {
    return member != null;
  }

  @Override
  public String toString() {
    return format(text);
  }

  private String format(String text) {
    return "<html>" + text.replace("<", "&lt;").replace(">", "&gt;");
  }

  private String generateText() {
    return Descriptors.getSimpleName(Descriptors.toTypeName(member.getType()));
  }

  public void sort() {
    if (children != null)
      children.sort(this);
  }

  public void combinePackage(ClassTreeNode pckg) {
    if (pckg.member != null)
      throw new IllegalArgumentException("cannot merge package with file");
    if (pckg == this)
      throw new IllegalArgumentException("cannot merge itself");
    if (!children.contains(pckg))
      throw new IllegalArgumentException("package is not a child");
    if (this.getChildCount() != 1)
      throw new IllegalArgumentException("child count over 1");

    text += "." + pckg.text; // combine package names
    this.removeAllChildren(); // remove old package

    // to avoid dirty OOB exceptions
    new ArrayList<>(pckg.children).forEach(m -> this.add((ClassTreeNode) m));
  }

  public int compare(TreeNode o1, TreeNode o2) {
    // first compare both by if member is null
    boolean leaf1 = ((ClassTreeNode) o1).member != null;
    boolean leaf2 = ((ClassTreeNode) o2).member != null;

    if (leaf1 != leaf2) {
      return leaf1 ? 1 : -1;
    }
    // compare by cachedText
    return ((ClassTreeNode) o1).text.compareTo(((ClassTreeNode) o2).text);
  }

  public ClassTreeNode findChild(String packageName) {
    if (children == null) return null;
    for (TreeNode tn : children) {
      if (((ClassTreeNode) tn).text.equals(packageName))
        return (ClassTreeNode) tn;
    }
    return null;
  }
}
