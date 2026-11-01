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
package io.github.jimmyboombots3000.fontawesomeswing.glyphs.weathericons;
import io.github.jimmyboombots3000.fontawesomeswing.glyphs.GlyphIconView;

/** Standalone Swing label displaying a WeatherIcon glyph. */
public class WeatherIconView extends GlyphIconView<WeatherIcon> {
    public static final String TTF_PATH = "/io/github/jimmyboombots3000/fontawesomeswing/glyphs/weathericons/weathericons-regular-webfont.ttf";
    public WeatherIconView() { this(WeatherIcon.CLOUD); }
    public WeatherIconView(WeatherIcon icon) { super(WeatherIcon.class, icon); }
    public WeatherIconView(WeatherIcon icon, double size) { super(WeatherIcon.class, icon, size); }
}
