module io.github.jimmyboombots3000.fontawesomeswing.fontawesome {
    requires transitive java.desktop;
    requires transitive io.github.jimmyboombots3000.fontawesomeswing.commons;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.fontawesome;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.fontawesome.demo;
    exports io.github.jimmyboombots3000.fontawesomeswing.glyphs.fontawesome.utils;
    exports io.github.jimmyboombots3000.fontawesomeswing.viewer;
    opens io.github.jimmyboombots3000.fontawesomeswing.glyphs.fontawesome to io.github.jimmyboombots3000.fontawesomeswing.commons;
}
