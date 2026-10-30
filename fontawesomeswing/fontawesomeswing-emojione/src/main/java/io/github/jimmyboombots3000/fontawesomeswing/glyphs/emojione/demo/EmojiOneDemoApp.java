/**
 * Copyright (c) 2015 Jens Deters http://www.jensd.de
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */
// Adapted for native Swing rendering and components.
package io.github.jimmyboombots3000.fontawesomeswing.glyphs.emojione.demo;
import java.awt.*;
import javax.swing.*;
import io.github.jimmyboombots3000.fontawesomeswing.glyphs.emojione.*;
public final class EmojiOneDemoApp {
    public static void main(String[] args) { SwingUtilities.invokeLater(() -> {
        JFrame frame = new JFrame("emojione — Swing glyph gallery");
        JPanel panel = new JPanel(new GridLayout(0, 12, 12, 12));
        for (EmojiOne glyph : EmojiOne.values()) { EmojiOneView view = new EmojiOneView(glyph, 24); view.setToolTipText(glyph.name()); panel.add(view); }
        frame.setContentPane(new JScrollPane(panel)); frame.setSize(900, 650);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); frame.setLocationByPlatform(true); frame.setVisible(true);
    }); }
}
