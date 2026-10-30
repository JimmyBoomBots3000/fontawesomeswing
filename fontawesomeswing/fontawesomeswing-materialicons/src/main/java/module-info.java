module io.github.jimmyboombots3000.fontawesomeswing.materialicons {
    requires transitive java.desktop;
    requires transitive io.github.jimmyboombots3000.fontawesomeswing.commons;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialicons;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialicons.demo;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialicons.utils;
    opens io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialicons to io.github.jimmyboombots3000.fontawesomeswing.commons;
}
