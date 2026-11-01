module io.github.jimmyboombots3000.fontawesomeswing.materialstackicons {
    requires transitive java.desktop;
    requires transitive io.github.jimmyboombots3000.fontawesomeswing.commons;
    requires transitive io.github.jimmyboombots3000.fontawesomeswing.materialicons;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialstackicons;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialstackicons.demo;
    opens io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialstackicons to io.github.jimmyboombots3000.fontawesomeswing.commons;
}
