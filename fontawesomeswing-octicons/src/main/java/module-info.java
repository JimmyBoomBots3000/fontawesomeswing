module io.github.jimmyboombots3000.fontawesomeswing.octicons {
    requires transitive java.desktop;
    requires transitive io.github.jimmyboombots3000.fontawesomeswing.commons;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.octicons;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.octicons.demo;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.octicons.utils;
    opens io.github.jimmyboombots3000.fontawesomeswing.glyphs.octicons to io.github.jimmyboombots3000.fontawesomeswing.commons;
}
