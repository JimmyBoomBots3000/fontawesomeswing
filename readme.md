# FontAwesomeSwing

**Using JavaFX? See [FontAwesomeFX](https://github.com/JimmyBoomBots3000/fontawesomefx).**

Scalable icon fonts for Swing, including **Font Awesome Free 7.3.1**, all bundled
icon packs, layered icons, and a searchable desktop viewer. The library uses only
Swing/AWT at runtime.

This is an independent Swing adaptation of **[FontAwesomeFX](https://bitbucket.org/Jerady/fontawesomefx)
by [Jens Deters](https://github.com/Jerady)**. Original catalogs, bundled fonts,
historical aliases, and attribution are preserved. JavaFX properties, CSS, FXML,
and scene graph APIs have been replaced by native Swing icons and components.

## Build

For Maven Central publishing, see [RELEASING.md](RELEASING.md).

Use **JDK 17 or newer** and the supplied Maven Wrapper. Library classes target
Java 11. On Windows, use `mvnw.cmd`.

```sh
# On macOS, if needed:
export JAVA_HOME=$(/usr/libexec/java_home -v 17)

./mvnw clean verify       # Build and run headless tests
./mvnw clean install      # Also install locally
./mvnw -Pjavadoc verify   # Also package API documentation
```

The root POM builds ten modules under `fontawesomeswing/`. To build only Font
Awesome and commons, add `-pl :fontawesomeswing-fontawesome -am`.

## Use in a Swing application

After installing locally, add:

```xml
<dependency>
    <groupId>io.github.jimmyboombots3000</groupId>
    <artifactId>fontawesomeswing-fontawesome</artifactId>
    <version>1.0.2-SNAPSHOT</version>
</dependency>
```

Commons is transitive. These instructions do not imply publication to Maven
Central. No JavaFX dependencies are needed.

Create and modify Swing components on the event dispatch thread:

```java
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import io.github.jimmyboombots3000.fontawesomeswing.glyphs.GlyphIcon;
import io.github.jimmyboombots3000.fontawesomeswing.glyphs.fontawesome.FontAwesomeIcon;
import io.github.jimmyboombots3000.fontawesomeswing.glyphs.fontawesome.FontAwesomeIconView;

SwingUtilities.invokeLater(() -> {
    GlyphIcon icon = new GlyphIcon(FontAwesomeIcon.HOUSE, 24);
    JButton button = new JButton("Home", icon);

    FontAwesomeIconView view = new FontAwesomeIconView(FontAwesomeIcon.HOUSE, 24);
    view.setForeground(Color.RED);
    view.setIcon(FontAwesomeIcon.REGULAR_HEART);
    view.setGlyphName("GITHUB");
    view.setGlyphSize(32);
});
```

**Swing Icon:** `GlyphIcon` is an immutable `javax.swing.Icon` that decorates a
button, label, menu item, tab, or renderer. It selects the glyph's bundled font.
Without an explicit color it uses the host component's foreground, or black
when painted without a component. You can pass a `Color` as the third constructor
argument. Size is the font size in logical pixels, defaults to 16, and must be
finite and in `(0, 4096]`. Measured bounds and padding can make icon dimensions
larger than the font size. Painting respects HiDPI graphics transforms.

**Standalone view:** `FontAwesomeIconView` and the other pack-specific views
are `JLabel` components you can add directly to a panel. Setters change their
glyph, size, and foreground. `getGlyph()` returns the catalog enum;
`getIcon()` returns the rendered Swing icon. The label's font affects label text,
not glyph size. Glyph and size changes emit Swing property-change notifications.

`GlyphsBuilder.create(FontAwesomeIcon.HOUSE).size(24).color(Color.BLUE).build()`
constructs an icon fluently. Pack factories retain their `get()` entry points and
provide native Swing controls and setters for labels, buttons, tabs, and tree
renderers. Numeric sizes replace JavaFX CSS strings.

Modular applications require:

```java
requires io.github.jimmyboombots3000.fontawesomeswing.fontawesome;
```

Each pack transitively requires commons and `java.desktop`. This is a new Swing
API; JavaFX source and binary compatibility are not retained.

## Stacks and checkboxes

`new GlyphsStack(backIcon, frontIcon)` creates an immutable stack of centered
layers, painted in argument order. Layers can have independent sizes and colors.
`MaterialStackIconView` provides named Material stack presets, numeric sizing,
and `setLayerColors(Color...)`.

`GlyphCheckBox` extends `JCheckBox` and accepts `(Icon normal, Icon selected,
String text)`, using the native Swing selection model and icon states.

## Browse icons

After `./mvnw install`:

```sh
./mvnw -pl :fontawesomeswing-fontawesome exec:java
```

The viewer uses the default Swing look and feel. It includes debounced name,
initials, multiword, and approximate search; style filters; sortable names and
styles; a selected preview; and a size spinner with mouse-wheel adjustment.
Reusable table renderers draw visible cells. Both Java examples have explanatory
labels and copy buttons; clicking their code also copies it. Escape clears search.

Other font-pack galleries use the same command with the corresponding module
artifact ID, such as `-pl :fontawesomeswing-materialicons exec:java`.

The optional desktop integration test requires a graphical display and briefly
replaces clipboard contents:

```sh
./mvnw -pl :fontawesomeswing-fontawesome -am \
  -Djava.awt.headless=false -Dfontawesomeswing.uiTests=true test
```

## Catalogs and generated sources

Font Awesome retains the current pinned free solid, regular, and brand styles
plus all 786 historical enum names. Regular variants use `REGULAR_`, names
starting with digits use `FA_`, and additional style variants receive a style
prefix when needed. `MEANPATH` and `TRIPADVISOR` retain the Font Awesome 4.7 font.

Maven generates the enum through `scripts/generate-fontawesome.groovy` during
`generate-sources`. Pinned inputs live in the Font Awesome module's
`src/main/fontawesome/`: `metadata.json`, `legacy.json`, and
`FontAwesomeIcon.java.template`. Generated Java belongs only under `target/`.
Normal builds use checked-in inputs and fonts. Groovy is a build dependency only.

To import an official Font Awesome Free desktop ZIP explicitly:

```sh
./mvnw -pl :fontawesomeswing-fontawesome -am -Pupdate-fontawesome \
  -Dfontawesome.archive=/absolute/path/to/fontawesome-free-desktop.zip verify
```

Review updated metadata, resources, and license notices. Update
`fontinfo.properties` and version references before committing. Other packs
retain their inherited font versions.

One inherited catalog gap is preserved: Material Design Icons 1.7.22 declares
`CANCEL` (U+F739), absent from its bundled font; it shows the font's missing-glyph
marker. EmojiOne uses the bundled font's monochrome outlines rather than its
color SVG artwork.

## Modules

All artifacts use group ID `io.github.jimmyboombots3000`, with a shared parent
version independent of the bundled font versions.

| Artifact | Contents |
|---|---|
| `fontawesomeswing-commons` | Icons, views, layers, builders, factories |
| `fontawesomeswing-controls` | Glyph checkbox |
| `fontawesomeswing-fontawesome` | Font Awesome Free and viewer |
| `fontawesomeswing-materialdesignfont` | Material Design Icons |
| `fontawesomeswing-materialicons` | Google Material Icons |
| `fontawesomeswing-materialstackicons` | Named Material stacks |
| `fontawesomeswing-octicons` | Octicons |
| `fontawesomeswing-weathericons` | Weather Icons |
| `fontawesomeswing-icons525` | 525icons |
| `fontawesomeswing-emojione` | Legacy EmojiOne |

## Attribution and licenses

Java code uses [Apache License 2.0](LICENSE). Original copyright notices and
credits are retained. See [NOTICE](NOTICE), [THIRD-PARTY-NOTICES.md](THIRD-PARTY-NOTICES.md),
and [licenses/](licenses/) for derivation, font licenses, and attribution.
Each JAR bundles notices under `META-INF`.
