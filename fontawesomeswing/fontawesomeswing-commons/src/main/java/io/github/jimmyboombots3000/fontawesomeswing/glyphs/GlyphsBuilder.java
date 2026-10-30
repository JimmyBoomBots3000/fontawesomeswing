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
import java.awt.Color;
import java.util.Objects;

/** Fluent builder for an immutable glyph icon. */
public final class GlyphsBuilder {
    private GlyphIcons glyph;
    private double size = GlyphIcon.DEFAULT_FONT_SIZE;
    private Color color;
    private GlyphsBuilder(GlyphIcons glyph) { this.glyph = Objects.requireNonNull(glyph); }
    public static GlyphsBuilder create(GlyphIcons glyph) { return new GlyphsBuilder(glyph); }
    public GlyphsBuilder glyph(GlyphIcons value) { glyph = Objects.requireNonNull(value); return this; }
    public GlyphsBuilder size(double value) { size = value; return this; }
    public GlyphsBuilder color(Color value) { color = value; return this; }
    public GlyphIcon build() { return new GlyphIcon(glyph, size, color); }
}
