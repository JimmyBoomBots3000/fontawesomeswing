module io.github.jimmyboombots3000.fontawesomeswing.materialdesignfont {
    requires transitive java.desktop;
    requires transitive io.github.jimmyboombots3000.fontawesomeswing.commons;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialdesignicons;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialdesignicons.demo;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialdesignicons.utils;
    opens io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialdesignicons to io.github.jimmyboombots3000.fontawesomeswing.commons;
}
