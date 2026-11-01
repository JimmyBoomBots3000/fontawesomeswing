/**
 * Copyright (c) 2016 Jens Deters http://www.jensd.de
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
// Adapted for native Swing rendering and components.
package io.github.jimmyboombots3000.fontawesomeswing.glyphs;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.Icon;

/** Immutable, reusable Swing icon. Size is measured in logical pixels. */
public final class GlyphIcon implements Icon {
    public static final double DEFAULT_FONT_SIZE = 16;
    private static final ConcurrentHashMap<Class<?>, ConcurrentHashMap<String, Font>> FONTS = new ConcurrentHashMap<>();
    private final GlyphIcons glyph;
    private final double size;
    private final Color color;
    private final Font font;
    private final Shape outline;
    private final Rectangle2D bounds;
    private final int width, height;

    public GlyphIcon(GlyphIcons glyph) { this(glyph, DEFAULT_FONT_SIZE); }
    public GlyphIcon(GlyphIcons glyph, double size) { this(glyph, size, null); }
    public GlyphIcon(GlyphIcons glyph, double size, Color color) {
        this.glyph = Objects.requireNonNull(glyph, "glyph");
        if (!Double.isFinite(size) || size <= 0 || size > 4096) throw new IllegalArgumentException("Size must be in (0, 4096]");
        this.size = size;
        this.color = color;
        Class<?> owner = glyph instanceof Enum<?> ? ((Enum<?>) glyph).getDeclaringClass() : glyph.getClass();
        font = FONTS.computeIfAbsent(owner, key -> new ConcurrentHashMap<>())
                .computeIfAbsent(glyph.fontResource(), path -> load(owner, path)).deriveFont((float) size);
        char[] chars = glyph.unicode().toCharArray();
        outline = font.layoutGlyphVector(new FontRenderContext(null, true, true), chars, 0, chars.length, Font.LAYOUT_LEFT_TO_RIGHT).getOutline();
        bounds = outline.getBounds2D();
        width = (int) Math.ceil(Math.max(size, bounds.getWidth())) + 2;
        height = (int) Math.ceil(Math.max(size, bounds.getHeight())) + 2;
    }
    private static Font load(Class<?> owner, String path) {
        try (InputStream stream = owner.getResourceAsStream(path)) {
            if (stream == null) throw new IllegalStateException("Missing icon font: " + path);
            return Font.createFont(Font.TRUETYPE_FONT, stream);
        } catch (java.io.IOException | FontFormatException ex) {
            throw new IllegalStateException("Cannot load icon font: " + path, ex);
        }
    }
    public GlyphIcons getGlyph() { return glyph; }
    public double getGlyphSize() { return size; }
    public Color getColor() { return color; }
    public Font getFont() { return font; }
    @Override public int getIconWidth() { return width; }
    @Override public int getIconHeight() { return height; }
    @Override public void paintIcon(Component component, Graphics graphics, int x, int y) {
        Graphics2D g = (Graphics2D) graphics.create();
        try {
            Color foreground = color != null ? color : component == null ? Color.BLACK : component.getForeground();
            g.setColor(foreground == null ? Color.BLACK : foreground);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.translate(x + (width - bounds.getWidth()) / 2 - bounds.getX(),
                    y + (height - bounds.getHeight()) / 2 - bounds.getY());
            g.fill(outline);
        } finally { g.dispose(); }
    }
}
