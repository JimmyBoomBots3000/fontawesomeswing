/**
 * Copyright (c) 2016 Diego Cirujano Cuesta (diego.cirujano-cuesta@zeiss.com)
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
import java.awt.Color;
import javax.swing.*;

/** Mutable component displaying a catalog stack. */
public class GlyphStackIcon<T extends Enum<T> & GlyphStackIcons<W>, W extends Enum<W> & GlyphIcons> extends JLabel {
    private final Class<T> type;
    private T glyph;
    private double size;
    private Color[] colors = new Color[0];
    public GlyphStackIcon(Class<T> type, T glyph, double size) {
        this.type = type; this.glyph = java.util.Objects.requireNonNull(glyph); this.size = size;
        updateIcon();
    }
    public T getGlyph() { return glyph; }
    public String getGlyphName() { return glyph.name(); }
    public void setGlyphName(String name) { setGlyph(Enum.valueOf(type, name)); }
    public void setGlyph(T value) {
        T old = glyph; glyph = java.util.Objects.requireNonNull(value); updateIcon(); firePropertyChange("glyph", old, value);
    }
    public double getGlyphSize() { return size; }
    public void setGlyphSize(double value) {
        if (!Double.isFinite(value) || value <= 0 || value > 4096) throw new IllegalArgumentException("Invalid size");
        double old = size; size = value; updateIcon(); firePropertyChange("glyphSize", old, value);
    }
    public void setLayerColors(Color... values) { colors = values.clone(); updateIcon(); }
    private void updateIcon() {
        W[] glyphs = glyph.getGlyphs();
        Icon[] layers = new Icon[glyphs.length];
        for (int i=0; i<layers.length; i++) layers[i] = new GlyphIcon(glyphs[i], size, i < colors.length ? colors[i] : null);
        super.setIcon(new GlyphsStack(layers));
    }
}
