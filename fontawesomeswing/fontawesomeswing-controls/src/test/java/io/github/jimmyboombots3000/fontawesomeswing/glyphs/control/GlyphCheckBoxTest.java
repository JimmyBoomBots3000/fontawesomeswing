package io.github.jimmyboombots3000.fontawesomeswing.glyphs.control;
import javax.swing.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class GlyphCheckBoxTest {
    @Test void usesNativeSelectionAndIconProperties() throws Exception { SwingUtilities.invokeAndWait(() -> {
        Icon normal = new ImageIcon(new java.awt.image.BufferedImage(8,8,2));
        Icon selected = new ImageIcon(new java.awt.image.BufferedImage(8,8,2));
        GlyphCheckBox box = new GlyphCheckBox(normal, selected, "Check");
        assertSame(normal,box.getNotSelectedIcon()); assertSame(selected,box.getSelectedIcon());
        box.doClick(); assertTrue(box.isSelected()); box.doClick(); assertFalse(box.isSelected());
        box.setNotSelectedIcon(selected); assertSame(selected,box.getIcon());
    }); }
}
