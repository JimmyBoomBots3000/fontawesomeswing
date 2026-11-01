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
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.Icon;

/** Immutable centered layers, painted in insertion order. */
public final class GlyphsStack implements Icon {
    private final List<Icon> layers;
    private final int width, height;
    public GlyphsStack(Icon... layers) { this(Arrays.asList(layers)); }
    public GlyphsStack(Collection<? extends Icon> layers) {
        this.layers = List.copyOf(layers);
        width = this.layers.stream().mapToInt(Icon::getIconWidth).max().orElse(0);
        height = this.layers.stream().mapToInt(Icon::getIconHeight).max().orElse(0);
    }
    public List<Icon> getLayers() { return layers; }
    @Override public int getIconWidth() { return width; }
    @Override public int getIconHeight() { return height; }
    @Override public void paintIcon(Component c, Graphics g, int x, int y) {
        for (Icon layer : layers) {
            Graphics copy = g.create();
            try { layer.paintIcon(c, copy, x + (width-layer.getIconWidth())/2, y + (height-layer.getIconHeight())/2); }
            finally { copy.dispose(); }
        }
    }
}
