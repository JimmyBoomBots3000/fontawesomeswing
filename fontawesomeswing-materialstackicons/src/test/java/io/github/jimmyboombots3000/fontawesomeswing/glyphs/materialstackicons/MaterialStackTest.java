package io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialstackicons;
import io.github.jimmyboombots3000.fontawesomeswing.glyphs.*;
import java.awt.Color;
import javax.swing.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class MaterialStackTest {
    @Test void replacesLayersAndChangesSizeAndColors() throws Exception { SwingUtilities.invokeAndWait(() -> {
        MaterialStackIconView view = new MaterialStackIconView();
        view.setGlyphName("LIGHT"); view.setGlyphSize(40); view.setLayerColors(Color.RED,Color.YELLOW);
        GlyphsStack icon=(GlyphsStack)view.getIcon();
        assertEquals(2,icon.getLayers().size());
        assertEquals(Color.YELLOW,((GlyphIcon)icon.getLayers().get(1)).getColor());
        assertEquals(40,((GlyphIcon)icon.getLayers().get(0)).getGlyphSize());
        assertEquals(MaterialStackIcons.LIGHT,view.getGlyph());
    }); }
}
