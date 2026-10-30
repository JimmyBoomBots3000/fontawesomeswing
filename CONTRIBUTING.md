# Contributing to FontAwesomeSwing

This is an independent Swing adaptation of FontAwesomeFX by
[Jens Deters](https://github.com/Jerady). Preserve original attribution and the
separate licenses for fonts and artwork. Java contributions use Apache 2.0.

Use JDK 17 or newer and Maven Wrapper. Library bytecode targets Java 11.

```sh
./mvnw clean verify
./mvnw -Pjavadoc verify
```

Tests normally run headlessly. They verify catalogs, font coverage, rendering
bounds, colors, graphics state, HiDPI transforms, stacks, control updates,
search, and compilable viewer examples. Material Design `CANCEL` is an explicitly
documented inherited font coverage gap.

For viewer changes, optionally run the desktop smoke test:

```sh
./mvnw -pl :fontawesomeswing-fontawesome -am \
  -Djava.awt.headless=false -Dfontawesomeswing.uiTests=true test
```

It requires a desktop and briefly replaces clipboard contents. Modify Swing
components on the event dispatch thread. Icons are immutable; views rebuild them
when their glyph or size changes. Verify classpath and module-path loading when
changing bundled resources or module descriptors.

For bug reports include the version/commit, JDK, OS, look and feel, reproduction
steps, and a runnable example. For rendering problems include pack, enum name,
logical size, display scale, and color.

Do not edit generated Java under `target/`. Change pinned Font Awesome inputs or
the enum template; see README for the explicit upstream import command.
