package io.github.jimmyboombots3000.fontawesomeswing.glyphs.fontawesome;
import io.github.jimmyboombots3000.fontawesomeswing.glyphs.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FontAwesomeIconTest {
    @Test void everyEntryLoadsAndPaintsItsBundledFont() {
        for (FontAwesomeIcon glyph : FontAwesomeIcon.values()) {
            GlyphIcon icon = new GlyphIcon(glyph, 24, Color.BLUE);
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
            FontAwesomeIconView view = new FontAwesomeIconView();
            view.setGlyphSize(32); view.setForeground(Color.RED);
            FontAwesomeIcon glyph = FontAwesomeIcon.values()[FontAwesomeIcon.values().length-1];
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
    @Test void styleSwitchingAndHistoricalAliases() throws Exception {
        assertEquals(FontAwesomeIcon.HOUSE.unicode(),FontAwesomeIcon.HOME.unicode());
        assertEquals(FontAwesomeIcon.REGULAR_ADDRESS_BOOK.unicode(),FontAwesomeIcon.ADDRESS_BOOK_ALT.unicode());
        SwingUtilities.invokeAndWait(() -> {
            FontAwesomeIconView view=new FontAwesomeIconView(FontAwesomeIcon.HOUSE,24);
            for(FontAwesomeIcon glyph:new FontAwesomeIcon[]{FontAwesomeIcon.GITHUB,FontAwesomeIcon.REGULAR_HEART,FontAwesomeIcon.MEANPATH,FontAwesomeIcon.HOUSE}) {
                view.setIcon(glyph);
                GlyphIcon icon=(GlyphIcon)view.getIcon();
                assertEquals(glyph.fontFamily(),icon.getFont().getFontName(java.util.Locale.ROOT));
                assertEquals(24,icon.getGlyphSize());
            }
        });
    }
}
