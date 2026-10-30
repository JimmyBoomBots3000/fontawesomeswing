package io.github.jimmyboombots3000.fontawesomeswing.glyphs.control;
import javax.swing.*;
/** Checkbox using Swing's native normal and selected icon states. */
public class GlyphCheckBox extends JCheckBox {
    public GlyphCheckBox() { super(""); }
    public GlyphCheckBox(Icon normal, Icon selected, String text) { super(text); setIcon(normal); setSelectedIcon(selected); }
    public Icon getNotSelectedIcon() { return getIcon(); }
    public void setNotSelectedIcon(Icon value) { setIcon(value); }
}
