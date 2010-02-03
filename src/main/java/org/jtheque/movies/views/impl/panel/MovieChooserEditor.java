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
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
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