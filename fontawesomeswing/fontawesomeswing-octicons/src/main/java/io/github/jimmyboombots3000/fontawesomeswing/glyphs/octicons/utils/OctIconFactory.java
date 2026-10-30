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
package io.github.jimmyboombots3000.fontawesomeswing.glyphs.octicons.utils;

import io.github.jimmyboombots3000.fontawesomeswing.glyphs.GlyphsFactory;
import io.github.jimmyboombots3000.fontawesomeswing.glyphs.octicons.OctIconView;

/**
 *
 * @author Jens Deters
 */
public class OctIconFactory extends GlyphsFactory {

    private static OctIconFactory me;

    private OctIconFactory() {
        super();
    }

    public static OctIconFactory get() {
        if (me == null) {
            me = new OctIconFactory();
        }
        return me;
    }

}
