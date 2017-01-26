/*
 * Copyright 2012 Gabriel Hauber gabriel@ghostdust.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License
 *       at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jsyntaxpane.actions;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import jsyntaxpane.SyntaxDocument;


/**
 * Duplicates the current line, or all selected lines, inserting the duplicates
 * above the current selection. On completion of the action, the inserted lines
 * are selected.
 * 
 * @author Gabriel Hauber (gabriel@ghostdust.com)
 */
public class DuplicateLinesUpAndSelectAction extends DefaultSyntaxAction {

    public DuplicateLinesUpAndSelectAction() {
        super("DUPLICATE_LINES_UP");
    }
    
    @Override public void actionPerformed(JTextComponent textComponent, SyntaxDocument syntaxDocument, int dot, ActionEvent event) {
        try {
            // get the current selection start and end, extending to the beginning of the line at the start
            // of the selection, and the end of the line at the end of the selection, so complete lines are
            // selected
            int start = syntaxDocument.getLineStartOffset(textComponent.getSelectionStart());
            int end = syntaxDocument.getLineEndOffset(textComponent.getSelectionEnd());
            
            String content = syntaxDocument.getText(start, end - start);
            
            // to insert *before* the current selection, we need to insert at the current start location
            syntaxDocument.insertString(start, content, null);
            
            // select the inserted text, excluding the final carriage-return
            textComponent.select(start, end-1);
            
        } catch (BadLocationException ble) {
            Logger.getLogger(DuplicateLinesUpAndSelectAction.class.getName()).log(Level.SEVERE, null, ble);
        }
    }
}
