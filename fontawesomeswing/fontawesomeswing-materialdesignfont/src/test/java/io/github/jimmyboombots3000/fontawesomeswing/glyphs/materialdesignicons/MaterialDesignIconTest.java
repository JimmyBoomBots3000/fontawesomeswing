package io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialdesignicons;
import io.github.jimmyboombots3000.fontawesomeswing.glyphs.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MaterialDesignIconTest {
    @Test void everyEntryLoadsAndPaintsItsBundledFont() {
        for (MaterialDesignIcon glyph : MaterialDesignIcon.values()) {
            GlyphIcon icon = new GlyphIcon(glyph, 24, Color.BLUE);
            // Inherited 1.7.22 catalog declares CANCEL (U+F739), absent from its bundled font.
            if (glyph == MaterialDesignIcon.CANCEL) {
                assertEquals(0, icon.getFont().canDisplayUpTo(glyph.unicode()));
                continue;
            }
            assertEquals(-1, icon.getFont().canDisplayUpTo(glyph.unicode()), glyph.name());
            BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            icon.paintIcon(null, g, 0, 0); g.dispose();
            boolean ink = false;
            for (int y=0; y<image.getHeight(); y++) for (int x=0; x<image.getWidth(); x++) {
                int argb = image.getRGB(x,y);
                if ((argb >>> 24) != 0) {
                    ink = true;
                    assertEquals(0x0000ff, argb & 0xffffff, glyph.name());
                    assertTrue(x>0 && y>0 && x<image.getWidth()-1 && y<image.getHeight()-1, glyph.name()+" clipped");
                }
            }
            assertTrue(ink, glyph.name()+" blank");
        }
    }
    @Test void viewChangesGlyphSizeAndForegroundOnEdt() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            MaterialDesignIconView view = new MaterialDesignIconView();
            view.setGlyphSize(32); view.setForeground(Color.RED);
            MaterialDesignIcon glyph = MaterialDesignIcon.values()[MaterialDesignIcon.values().length-1];
            java.util.List<String> changed = new java.util.ArrayList<>();
            view.addPropertyChangeListener(e -> changed.add(e.getPropertyName()));
            view.setGlyphName(glyph.name());
            assertSame(glyph, view.getGlyph());
            assertSame(glyph, ((GlyphIcon)view.getIcon()).getGlyph());
            assertEquals(32, ((GlyphIcon)view.getIcon()).getGlyphSize());
            assertEquals(Color.RED, view.getForeground());
            assertTrue(changed.contains("glyph"));
            assertThrows(IllegalArgumentException.class, () -> view.setGlyphSize(Double.NaN));
            assertEquals(32, view.getGlyphSize());
            assertThrows(IllegalArgumentException.class, () -> view.setGlyphName("NOT_AN_ICON"));
        });
    }
}
