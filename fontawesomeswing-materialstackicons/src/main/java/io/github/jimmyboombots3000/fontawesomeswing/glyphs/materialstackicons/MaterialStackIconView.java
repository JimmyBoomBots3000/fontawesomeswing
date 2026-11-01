package io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialstackicons;
import io.github.jimmyboombots3000.fontawesomeswing.glyphs.GlyphStackIcon;
import io.github.jimmyboombots3000.fontawesomeswing.glyphs.materialicons.MaterialIcon;
public class MaterialStackIconView extends GlyphStackIcon<MaterialStackIcons, MaterialIcon> {
    public MaterialStackIconView() { this(MaterialStackIcons.FLASH_NOT_ALLOWED); }
    public MaterialStackIconView(MaterialStackIcons glyph) { this(glyph, 32); }
    public MaterialStackIconView(MaterialStackIcons glyph, double size) { super(MaterialStackIcons.class, glyph, size); setLayerColors(new java.awt.Color(0x247cbc), java.awt.Color.WHITE, new java.awt.Color(220, 40, 40, 180)); }
}
