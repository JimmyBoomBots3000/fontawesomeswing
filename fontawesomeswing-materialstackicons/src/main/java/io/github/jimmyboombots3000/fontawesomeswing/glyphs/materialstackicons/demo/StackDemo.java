/* Licensed under the Apache License, Version 2.0. See LICENSE. */
package io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialstackicons.demo;
import java.awt.*;
import javax.swing.*;
import io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialstackicons.*;
public final class StackDemo {
    public static void main(String[] args) { SwingUtilities.invokeLater(() -> {
        JFrame frame = new JFrame("materialstackicons — Swing glyph gallery");
        JPanel panel = new JPanel(new GridLayout(0, 12, 12, 12));
        for (MaterialStackIcons glyph : MaterialStackIcons.values()) { MaterialStackIconView view = new MaterialStackIconView(glyph); view.setLayerColors(Color.WHITE, Color.BLACK, Color.RED); panel.add(view); }
        frame.setContentPane(new JScrollPane(panel)); frame.setSize(900, 650);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); frame.setLocationByPlatform(true); frame.setVisible(true);
    }); }
}
