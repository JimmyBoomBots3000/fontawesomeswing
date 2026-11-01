package io.github.jimmyboombots3000.fontawesomeswing.glyphs;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class GlyphRenderingTest {
    enum Fixture implements GlyphIcons {
        HEART;
        public String unicode() { return "\uf004"; }
        public String fontFamily() { return "FontAwesome"; }
        public String fontResource() { return "/fixture.ttf"; }
    }
    @Test void respectsScaleColorOriginAndGraphicsState() {
        GlyphIcon icon = new GlyphIcon(Fixture.HEART, 24);
        BufferedImage image = new BufferedImage(100,100,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.scale(2,2); g.setColor(Color.GREEN); g.setFont(new Font("Dialog",Font.BOLD,9));
        AffineTransform transform = g.getTransform(); Font font = g.getFont(); RenderingHints hints=g.getRenderingHints();
        JLabel host = new JLabel(); host.setForeground(Color.RED);
        icon.paintIcon(host,g,5,5);
        assertEquals(transform,g.getTransform()); assertEquals(Color.GREEN,g.getColor()); assertEquals(font,g.getFont()); assertEquals(hints,g.getRenderingHints());
        g.dispose();
        int ink=0;
        for(int y=0;y<100;y++) for(int x=0;x<100;x++) if(image.getRGB(x,y)>>>24 != 0) {
            ink++; assertEquals(0xff0000,image.getRGB(x,y)&0xffffff);
            assertTrue(x>=10 && y>=10 && x<10+icon.getIconWidth()*2 && y<10+icon.getIconHeight()*2);
        }
        assertTrue(ink>300);
    }
    @Test void stackCentersLayersAndPaintsInOrder() {
        Icon back = new Solid(20,20,Color.RED), front = new Solid(4,4,Color.BLUE);
        GlyphsStack stack = new GlyphsStack(back,front);
        BufferedImage image = new BufferedImage(24,24,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g=image.createGraphics(); stack.paintIcon(null,g,2,2); g.dispose();
        assertEquals(Color.RED.getRGB(),image.getRGB(2,2)); assertEquals(Color.BLUE.getRGB(),image.getRGB(10,10));
        assertEquals(0,image.getRGB(1,1)); assertEquals(20,stack.getIconWidth());
        assertThrows(UnsupportedOperationException.class,()->stack.getLayers().clear());
    }
    @Test void factoryBuilderAndValidation() {
        GlyphsFactory factory = new GlyphsFactory();
        assertNotNull(factory.createIconButton(Fixture.HEART).getIcon());
        assertNotNull(factory.createIconToggleButton(Fixture.HEART).getIcon());
        JMenuItem menu = new JMenuItem(); factory.setIcon(menu,Fixture.HEART,20); assertNotNull(menu.getIcon());
        JTabbedPane tabs=new JTabbedPane(); tabs.addTab("Test",new JPanel()); factory.setIcon(tabs,0,Fixture.HEART,20); assertNotNull(tabs.getIconAt(0));
        javax.swing.tree.DefaultTreeCellRenderer renderer=new javax.swing.tree.DefaultTreeCellRenderer(); factory.setIcon(renderer,Fixture.HEART,20); assertNotNull(renderer.getLeafIcon());
        GlyphIcon icon=GlyphsBuilder.create(Fixture.HEART).size(18).color(Color.BLUE).build();
        assertEquals(18,icon.getGlyphSize()); assertEquals(Color.BLUE,icon.getColor());
        for(double size:new double[]{0,-1,Double.NaN,Double.POSITIVE_INFINITY,4097}) assertThrows(IllegalArgumentException.class,()->new GlyphIcon(Fixture.HEART,size));
        assertThrows(NullPointerException.class,()->new GlyphIcon(null));
    }
    private static class Solid implements Icon {
        final int w,h; final Color color;
        Solid(int w,int h,Color color){this.w=w;this.h=h;this.color=color;}
        public int getIconWidth(){return w;} public int getIconHeight(){return h;}
        public void paintIcon(Component c,Graphics g,int x,int y){g.setColor(color);g.fillRect(x,y,w,h);}
    }
}
