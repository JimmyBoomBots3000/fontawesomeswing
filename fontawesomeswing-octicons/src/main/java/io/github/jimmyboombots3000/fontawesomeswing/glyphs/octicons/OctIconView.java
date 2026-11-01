/**
 * Copyright (c) 2013-2016 Jens Deters http://www.jensd.de
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
package io.github.jimmyboombots3000.fontawesomeswing.glyphs.octicons;
import io.github.jimmyboombots3000.fontawesomeswing.glyphs.GlyphIconView;

/** Standalone Swing label displaying a OctIcon glyph. */
public class OctIconView extends GlyphIconView<OctIcon> {
    public static final String TTF_PATH = "/io/github/jimmyboombots3000/fontawesomeswing/glyphs/octicons/octicons.ttf";
    public OctIconView() { this(OctIcon.MARK_GITHUB); }
    public OctIconView(OctIcon icon) { super(OctIcon.class, icon); }
    public OctIconView(OctIcon icon, double size) { super(OctIcon.class, icon, size); }
}
