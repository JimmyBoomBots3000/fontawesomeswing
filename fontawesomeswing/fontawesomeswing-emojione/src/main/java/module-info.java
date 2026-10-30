module io.github.jimmyboombots3000.fontawesomeswing.emojione {
    requires transitive java.desktop;
    requires transitive io.github.jimmyboombots3000.fontawesomeswing.commons;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.emojione;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.emojione.demo;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.emojione.utils;
    opens io.github.jimmyboombots3000.fontawesomeswing.glyphs.emojione to io.github.jimmyboombots3000.fontawesomeswing.commons;
}
