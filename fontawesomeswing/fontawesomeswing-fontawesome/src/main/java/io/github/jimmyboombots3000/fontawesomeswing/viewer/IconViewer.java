/* Licensed under the Apache License, Version 2.0. See LICENSE. */
package io.github.jimmyboombots3000.fontawesomeswing.viewer;

import io.github.jimmyboombots3000.fontawesomeswing.glyphs.GlyphIcon;
import io.github.jimmyboombots3000.fontawesomeswing.glyphs.fontawesome.FontAwesomeIcon;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/** Searchable Swing catalog. Construct and modify on the event dispatch thread. */
public final class IconViewer extends JFrame {
    private final List<IconSearch> catalog = Arrays.stream(FontAwesomeIcon.values()).map(IconSearch::new).collect(Collectors.toList());
    private List<IconSearch> visible = new ArrayList<>(catalog);
    private final JTextField search = new JTextField();
    private final JComboBox<String> style = new JComboBox<>(new String[]{"All styles", "Solid", "Regular", "Brands", "Legacy"});
    private final JSpinner size = new JSpinner(new SpinnerNumberModel(24, 8, 96, 1));
    private final JLabel count = new JLabel();
    private final JLabel status = new JLabel(" ");
    private final JLabel selectedPreview = new JLabel();
    private final JTextArea iconCode = codeArea();
    private final JTextArea viewCode = codeArea();
    private final javax.swing.Timer debounce = new javax.swing.Timer(100, event -> filter());
    private final Map<FontAwesomeIcon, GlyphIcon> previews = new EnumMap<>(FontAwesomeIcon.class);
    private final AbstractTableModel model = new AbstractTableModel() {
        private final String[] columns = {"Icon", "Enum name", "Style"};
        @Override public int getRowCount() { return visible.size(); }
        @Override public int getColumnCount() { return columns.length; }
        @Override public String getColumnName(int column) { return columns[column]; }
        @Override public Class<?> getColumnClass(int column) { return column == 0 ? FontAwesomeIcon.class : String.class; }
        @Override public Object getValueAt(int row, int column) {
            FontAwesomeIcon glyph = visible.get(row).icon;
            return column == 0 ? glyph : column == 1 ? glyph.name() : styleOf(glyph);
        }
    };
    private final JTable table = new JTable(model);

    public IconViewer() {
        super("FontAwesomeSwing — Icon Viewer");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        debounce.setRepeats(false);
        search.setName("icon-search");
        search.setToolTipText("Search names, initials, or approximate spelling");
        search.getAccessibleContext().setAccessibleName("Search icons");
        style.getAccessibleContext().setAccessibleName("Filter icon style");
        size.getAccessibleContext().setAccessibleName("Icon size in logical pixels");
        search.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { debounce.restart(); }
            public void removeUpdate(DocumentEvent e) { debounce.restart(); }
            public void changedUpdate(DocumentEvent e) { debounce.restart(); }
        });
        search.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "clear");
        search.getActionMap().put("clear", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent event) { search.setText(""); }
        });
        style.addActionListener(event -> filter());
        size.addChangeListener(event -> {
            previews.clear(); table.setRowHeight(iconSize() + 12); table.repaint(); updateExamples();
        });
        size.addMouseWheelListener(event -> {
            int next = Math.max(8, Math.min(96, iconSize() - event.getWheelRotation()));
            size.setValue(next); event.consume();
        });
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        ((TableRowSorter<?>) table.getRowSorter()).setSortable(0, false);
        table.setRowHeight(iconSize() + 12);
        table.setDefaultRenderer(FontAwesomeIcon.class, new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable t, Object value, boolean selected, boolean focus, int row, int column) {
                super.getTableCellRendererComponent(t, "", selected, focus, row, column);
                setHorizontalAlignment(CENTER);
                FontAwesomeIcon glyph = (FontAwesomeIcon) value;
                setIcon(previews.computeIfAbsent(glyph, key -> new GlyphIcon(key, iconSize())));
                return this;
            }
        });
        table.getColumnModel().getColumn(0).setMaxWidth(130);
        table.getColumnModel().getColumn(1).setPreferredWidth(480);
        table.getColumnModel().getColumn(2).setMaxWidth(110);
        table.getSelectionModel().addListSelectionListener(event -> { if (!event.getValueIsAdjusting()) updateExamples(); });
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEADING));
        toolbar.add(new JLabel("Size:")); toolbar.add(size); toolbar.add(style); toolbar.add(count);
        JPanel searchRow = new JPanel(new BorderLayout(8, 0));
        JLabel searchLabel = new JLabel("Search:"); searchLabel.setLabelFor(search);
        searchRow.add(searchLabel, BorderLayout.WEST); searchRow.add(search);
        JPanel top = new JPanel(new BorderLayout(0, 8)); top.add(toolbar, BorderLayout.NORTH); top.add(searchRow);
        JPanel examples = new JPanel(); examples.setLayout(new BoxLayout(examples, BoxLayout.Y_AXIS));
        selectedPreview.setAlignmentX(Component.LEFT_ALIGNMENT); examples.add(selectedPreview);
        addExample(examples, "Swing Icon",
                "javax.swing.Icon — attach to a button, label, menu item, or tab.", iconCode);
        addExample(examples, "Standalone view",
                "JLabel component — add directly to a panel; change its glyph, size, or color.", viewCode);
        status.setAlignmentX(Component.LEFT_ALIGNMENT); examples.add(status);
        JPanel root = new JPanel(new BorderLayout(0, 12)); root.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        root.add(top, BorderLayout.NORTH); root.add(new JScrollPane(table)); root.add(examples, BorderLayout.SOUTH);
        setContentPane(root); setSize(950, 800); setMinimumSize(new Dimension(700, 600)); setLocationByPlatform(true);
        filter();
    }
    private int iconSize() { return (Integer) size.getValue(); }
    private FontAwesomeIcon selection() {
        int row = table.getSelectedRow();
        return row < 0 ? null : visible.get(table.convertRowIndexToModel(row)).icon;
    }
    private void filter() {
        FontAwesomeIcon selected = selection();
        String query = search.getText();
        String selectedStyle = (String) style.getSelectedItem();
        table.clearSelection();
        visible = catalog.stream().filter(entry -> entry.matches(query)
                && ("All styles".equals(selectedStyle) || styleOf(entry.icon).equals(selectedStyle))).collect(Collectors.toList());
        model.fireTableDataChanged();
        count.setText(visible.size() + " icons");
        int row = -1;
        for (int i=0; i<visible.size(); i++) if (visible.get(i).icon == selected) { row = i; break; }
        if (row >= 0) row = table.convertRowIndexToView(row);
        else if (!visible.isEmpty()) row = 0;
        if (row >= 0) table.setRowSelectionInterval(row, row);
        updateExamples();
    }
    static String styleOf(FontAwesomeIcon icon) {
        String font = icon.fontFamily();
        if (font.contains("Brands")) return "Brands";
        if (font.endsWith("Solid")) return "Solid";
        if (font.endsWith("Regular")) return "Regular";
        return "Legacy";
    }
    static String iconExample(FontAwesomeIcon glyph, int size) {
        return "import javax.swing.JButton;\nimport " + GlyphIcon.class.getName() + ";\nimport " + FontAwesomeIcon.class.getName()
                + ";\n\nGlyphIcon icon = new GlyphIcon(FontAwesomeIcon." + glyph.name() + ", " + size + ");\nJButton button = new JButton(\"Action\", icon);";
    }
    static String viewExample(FontAwesomeIcon glyph, int size) {
        return "import " + FontAwesomeIcon.class.getName() + ";\nimport io.github.jimmyboombots3000.fontawesomeswing.glyphs.fontawesome.FontAwesomeIconView;\n\n"
                + "FontAwesomeIconView view = new FontAwesomeIconView(FontAwesomeIcon." + glyph.name() + ", " + size + ");";
    }
    private void updateExamples() {
        FontAwesomeIcon selected = selection();
        iconCode.setText(selected == null ? "" : iconExample(selected, iconSize())); iconCode.setCaretPosition(0);
        viewCode.setText(selected == null ? "" : viewExample(selected, iconSize())); viewCode.setCaretPosition(0);
        selectedPreview.setIcon(selected == null ? null : new GlyphIcon(selected, iconSize()));
        selectedPreview.setText(selected == null ? "No matching icons. Try a shorter search or another style." : selected.name());
        status.setText(" ");
    }
    private static JTextArea codeArea() {
        JTextArea area = new JTextArea(5, 60); area.setEditable(false); area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12)); return area;
    }
    private void addExample(JPanel parent, String title, String description, JTextArea area) {
        JButton copy = new JButton("Copy " + title);
        copy.setEnabled(false);
        area.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }
            private void update() { copy.setEnabled(!area.getText().isEmpty()); }
        });
        copy.addActionListener(event -> copy(area, title));
        area.addMouseListener(new MouseAdapter() { @Override public void mouseClicked(MouseEvent event) { copy(area, title); } });
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEADING)); header.add(new JLabel(title)); header.add(copy);
        header.setAlignmentX(Component.LEFT_ALIGNMENT); parent.add(header);
        JLabel explanation = new JLabel(description);
        explanation.setAlignmentX(Component.LEFT_ALIGNMENT); parent.add(explanation);
        JScrollPane scroll = new JScrollPane(area); scroll.setAlignmentX(Component.LEFT_ALIGNMENT); parent.add(scroll);
    }
    private void copy(JTextArea area, String title) {
        if (area.getText().isEmpty()) return;
        try {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(area.getText()), null);
            area.selectAll(); status.setText(title + " copied to clipboard");
        } catch (IllegalStateException | java.awt.HeadlessException | SecurityException ex) { status.setText("Clipboard unavailable"); }
    }
    @Override public void dispose() { debounce.stop(); super.dispose(); }
    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new IconViewer().setVisible(true)); }
}
