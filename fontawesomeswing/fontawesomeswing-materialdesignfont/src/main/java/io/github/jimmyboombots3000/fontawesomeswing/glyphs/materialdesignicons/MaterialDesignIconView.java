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
package io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialdesignicons;
import io.github.jimmyboombots3000.fontawesomeswing.glyphs.GlyphIconView;

/** Standalone Swing label displaying a MaterialDesignIcon glyph. */
public class MaterialDesignIconView extends GlyphIconView<MaterialDesignIcon> {
    public static final String TTF_PATH = "/io/github/jimmyboombots3000/fontawesomeswing/glyphs/materialdesignicons/materialdesignicons-webfont.ttf";
    public MaterialDesignIconView() { this(MaterialDesignIcon.ANDROID); }
    public MaterialDesignIconView(MaterialDesignIcon icon) { super(MaterialDesignIcon.class, icon); }
    public MaterialDesignIconView(MaterialDesignIcon icon, double size) { super(MaterialDesignIcon.class, icon, size); }
}
