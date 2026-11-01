/* Licensed under the Apache License, Version 2.0. See LICENSE. */
package io.github.jimmyboombots3000.fontawesomeswing.glyphs;
import java.util.Objects;
import javax.swing.JLabel;

/** Mutable Swing component wrapper. Modify on the event dispatch thread. */
public class GlyphIconView<T extends Enum<T> & GlyphIcons> extends JLabel {
    private final Class<T> type;
    private T glyph;
    private double glyphSize;
    public GlyphIconView(Class<T> type, T glyph) { this(type, glyph, GlyphIcon.DEFAULT_FONT_SIZE); }
    public GlyphIconView(Class<T> type, T glyph, double size) {
        this.type = Objects.requireNonNull(type, "type");
        setHorizontalAlignment(CENTER);
        this.glyph = Objects.requireNonNull(glyph, "glyph");
        setGlyphSize(size);
    }
    public T getGlyph() { return glyph; }
    public String getGlyphName() { return glyph.name(); }
    public void setGlyphName(String name) { setIcon(Enum.valueOf(type, name)); }
    public void setIcon(T value) {
        Objects.requireNonNull(value, "glyph");
        GlyphIcon replacement = new GlyphIcon(value, glyphSize);
        T previous = glyph;
        glyph = value;
        super.setIcon(replacement);
        firePropertyChange("glyph", previous, value);
        firePropertyChange("glyphName", previous == null ? null : previous.name(), value.name());
    }
    public double getGlyphSize() { return glyphSize; }
    public void setGlyphSize(double size) {
        GlyphIcon replacement = new GlyphIcon(glyph, size);
        double previous = glyphSize;
        glyphSize = size;
        super.setIcon(replacement);
        firePropertyChange("glyphSize", previous, size);
    }
    public String unicode() { return glyph.unicode(); }
}
