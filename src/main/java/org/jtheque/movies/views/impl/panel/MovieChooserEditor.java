package org.jtheque.movies.views.impl.panel;

import org.jdesktop.swingx.event.WeakEventListenerList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.EventObject;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * An editor to choose a movie file in a table.
 *
 * @author Baptiste Wicht
 */
public final class MovieChooserEditor extends JButton implements TableCellEditor, ActionListener {
    private final WeakEventListenerList listeners = new WeakEventListenerList();
    private final ChangeEvent event = new ChangeEvent(this);

    private File file;

    /**
     * Construct a new MovieChooserEditor.
     */
    public MovieChooserEditor() {
        super("");

        setBackground(Color.white);
        setBorderPainted(false);
        setMargin(new Insets(0, 0, 0, 0));
        addActionListener(this);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser("d:\\html");
        int returnVal = chooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();

            fireEditingStopped();
        } else {
            fireEditingCanceled();
        }
    }

    @Override
    public void addCellEditorListener(CellEditorListener listener) {
        listeners.add(CellEditorListener.class, listener);
    }

    @Override
    public void removeCellEditorListener(CellEditorListener listener) {
        listeners.remove(CellEditorListener.class, listener);
    }

    /**
     * Fire an event that say that the editing has stopped.
     */
    protected void fireEditingStopped() {
        CellEditorListener[] editorListeners = listeners.getListeners(CellEditorListener.class);

        for (CellEditorListener listener : editorListeners) {
            listener.editingStopped(event);
        }
    }

    /**
     * Fire an event that say that the editing has been canceled.  
     */
    protected void fireEditingCanceled() {
        CellEditorListener[] editorListeners = listeners.getListeners(CellEditorListener.class);

        for (CellEditorListener listener : editorListeners) {
            listener.editingCanceled(event);
        }
    }

    @Override
    public void cancelCellEditing() {
        fireEditingCanceled();
    }

    @Override
    public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }

    @Override
    public boolean isCellEditable(EventObject event) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject event) {
        return true;
    }

    @Override
    public Object getCellEditorValue() {
        return file;
    }
}