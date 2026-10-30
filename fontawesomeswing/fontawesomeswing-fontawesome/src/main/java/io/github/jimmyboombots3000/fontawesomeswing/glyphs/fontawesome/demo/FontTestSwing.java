/* Licensed under the Apache License, Version 2.0. See LICENSE. */
package io.github.jimmyboombots3000.fontawesomeswing.glyphs.fontawesome.demo;
import java.awt.*;
import javax.swing.*;
import io.github.jimmyboombots3000.fontawesomeswing.glyphs.fontawesome.*;
public final class FontTestSwing {
    public static void main(String[] args) { SwingUtilities.invokeLater(() -> {
        JFrame frame = new JFrame("fontawesome — Swing glyph gallery");
        JPanel panel = new JPanel(new GridLayout(0, 12, 12, 12));
        for (FontAwesomeIcon glyph : FontAwesomeIcon.values()) { FontAwesomeIconView view = new FontAwesomeIconView(glyph, 24); view.setToolTipText(glyph.name()); panel.add(view); }
        frame.setContentPane(new JScrollPane(panel)); frame.setSize(900, 650);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); frame.setLocationByPlatform(true); frame.setVisible(true);
    }); }
}
