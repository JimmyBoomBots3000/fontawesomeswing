package io.github.jimmyboombots3000.fontawesomeswing.viewer;
import io.github.jimmyboombots3000.fontawesomeswing.glyphs.fontawesome.FontAwesomeIcon;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.*;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;
import javax.tools.ToolProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
class IconViewerTest {
    @TempDir Path temp;
    @Test void examplesCompileAgainstPublicApi() throws Exception {
        String imports="", body="";
        for(String example:new String[]{IconViewer.iconExample(FontAwesomeIcon.HOUSE,24),IconViewer.viewExample(FontAwesomeIcon.REGULAR_HEART,32)}) {
            for(String line:example.split("\n")) {
                if(line.startsWith("import ")) imports += line+"\n";
                else body += line+"\n";
            }
        }
        Path source=temp.resolve("Example.java");
        Files.writeString(source,imports+"public class Example { void build() {"+body+"} }");
        assertEquals(0,ToolProvider.getSystemJavaCompiler().run(null,null,null,"-classpath",System.getProperty("java.class.path"),"-d",temp.toString(),source.toString()));
        assertEquals("Solid",IconViewer.styleOf(FontAwesomeIcon.HOUSE));
        assertEquals("Regular",IconViewer.styleOf(FontAwesomeIcon.REGULAR_HEART));
        assertEquals("Brands",IconViewer.styleOf(FontAwesomeIcon.GITHUB));
        assertEquals("Legacy",IconViewer.styleOf(FontAwesomeIcon.MEANPATH));
    }
    @Test @EnabledIfSystemProperty(named="fontawesomeswing.uiTests",matches="true")
    void desktopFilteringResizingSelectionAndClipboard() throws Exception {
        IconViewer[] holder=new IconViewer[1];
        try {
            SwingUtilities.invokeAndWait(() -> {
                IconViewer viewer=new IconViewer(); holder[0]=viewer; viewer.setVisible(true);
                JTable table=find(viewer,JTable.class); assertNotNull(table); assertTrue(table.getRowCount()>2000);
                JSpinner spinner=find(viewer,JSpinner.class); spinner.setValue(48); assertEquals(60,table.getRowHeight());
                JComboBox<?> style=find(viewer,JComboBox.class); style.setSelectedItem("Brands");
                for(int i=0;i<table.getRowCount();i++) assertEquals("Brands",table.getValueAt(i,2));
                style.setSelectedItem("All styles");
                JTextField search=findNamed(viewer,"icon-search",JTextField.class); search.setText("github");
            });
            // Wait outside the EDT so the search debounce can fire.
            Thread.sleep(250);
            SwingUtilities.invokeAndWait(() -> {
                IconViewer viewer=holder[0]; JTable table=find(viewer,JTable.class);
                assertTrue(table.getRowCount()>0); assertTrue(table.getRowCount()<100);
                table.setRowSelectionInterval(table.getRowCount()-1,table.getRowCount()-1);
                JTextArea code=find(viewer,JTextArea.class); assertTrue(code.getText().contains("GlyphIcon icon"));
                JButton copy=findButton(viewer,"Copy Swing Icon"); copy.doClick();
                try { assertEquals(code.getText(),Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor)); }
                catch(Exception ex) { throw new AssertionError(ex); }
                JTextField search=findNamed(viewer,"icon-search",JTextField.class); search.setText("zzzzzzzzzzzzzzzzzzzzzz");
            });
            Thread.sleep(250);
            SwingUtilities.invokeAndWait(() -> {
                IconViewer viewer=holder[0]; assertEquals(0,find(viewer,JTable.class).getRowCount());
                assertEquals("",find(viewer,JTextArea.class).getText()); assertFalse(findButton(viewer,"Copy Swing Icon").isEnabled());
                JTextField search=findNamed(viewer,"icon-search",JTextField.class);
                search.getActionMap().get("clear").actionPerformed(new ActionEvent(search,0,"clear")); assertEquals("",search.getText());
            });
        } finally { if(holder[0]!=null) SwingUtilities.invokeAndWait(holder[0]::dispose); }
    }
    private static JButton findButton(Container c,String text) {
        for(Component child:c.getComponents()) {
            if(child instanceof JButton && ((JButton)child).getText().equals(text)) return (JButton)child;
            if(child instanceof Container) { JButton found=findButton((Container)child,text); if(found!=null) return found; }
        } return null;
    }
    private static <T extends Component> T find(Container c,Class<T> type) { return findNamed(c,null,type); }
    private static <T extends Component> T findNamed(Container c,String name,Class<T> type) {
        for(Component child:c.getComponents()) {
            if(type.isInstance(child) && (name==null || name.equals(child.getName()))) return type.cast(child);
            if(child instanceof Container) { T found=findNamed((Container)child,name,type); if(found!=null) return found; }
        } return null;
    }
}
