module io.github.jimmyboombots3000.fontawesomeswing.weathericons {
    requires transitive java.desktop;
    requires transitive io.github.jimmyboombots3000.fontawesomeswing.commons;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.weathericons;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.weathericons.demo;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.weathericons.utils;
    opens io.github.jimmyboombots3000.fontawesomeswing.glyphs.weathericons to io.github.jimmyboombots3000.fontawesomeswing.commons;
}
