/**
 * Copyright (c) 2013-2016 Jens Deters http://www.jensd.de
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 *
 */
// Adapted for native Swing rendering and components.
package io.github.jimmyboombots3000.fontawesomeswing.glyphs;
import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;

/** Convenience methods for native Swing controls. */
public class GlyphsFactory {
    public GlyphsFactory() { }
    public GlyphIcon createIcon(GlyphIcons glyph) { return new GlyphIcon(glyph); }
    public GlyphIcon createIcon(GlyphIcons glyph, double size) { return new GlyphIcon(glyph, size); }
    public JLabel createIconLabel(GlyphIcons glyph, String text, double size) { return new JLabel(text, createIcon(glyph, size), SwingConstants.LEADING); }
    public JButton createIconButton(GlyphIcons glyph) { return createIconButton(glyph, ""); }
    public JButton createIconButton(GlyphIcons glyph, String text) { return new JButton(text, createIcon(glyph)); }
    public JToggleButton createIconToggleButton(GlyphIcons glyph) { return createIconToggleButton(glyph, ""); }
    public JToggleButton createIconToggleButton(GlyphIcons glyph, String text) { return new JToggleButton(text, createIcon(glyph)); }
    public void setIcon(JLabel label, GlyphIcons glyph) { label.setIcon(createIcon(glyph)); }
    public void setIcon(JLabel label, GlyphIcons glyph, double size) { label.setIcon(createIcon(glyph, size)); }
    public void setIcon(AbstractButton button, GlyphIcons glyph) { button.setIcon(createIcon(glyph)); }
    public void setIcon(AbstractButton button, GlyphIcons glyph, double size) { button.setIcon(createIcon(glyph, size)); }
    public void setIcon(JTabbedPane tabs, int index, GlyphIcons glyph, double size) { tabs.setIconAt(index, createIcon(glyph, size)); }
    public void setIcon(DefaultTreeCellRenderer renderer, GlyphIcons glyph, double size) {
        Icon icon = createIcon(glyph, size);
        renderer.setLeafIcon(icon); renderer.setOpenIcon(icon); renderer.setClosedIcon(icon);
    }
}
