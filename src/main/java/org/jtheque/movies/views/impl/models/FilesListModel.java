package org.jtheque.movies.views.impl.models;

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

import javax.swing.DefaultListModel;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.io.File;

/**
 * A list model for files.
 *
 * @author Baptiste Wicht
 */
public final class FilesListModel extends DefaultListModel {
    private final List<File> files = new ArrayList<File>(25);
    
    @Override
    public Object getElementAt(int index) {
        return files.get(index);
    }

    @Override
    public Object get(int index) {
        return getElementAt(index);
    }

    @Override
    public int getSize() {
        return files.size();
    }

    @Override
    public Object remove(int i) {
        File category = files.remove(i);
        fireIntervalRemoved(this, i, i);
        return category;
    }

    @Override
    public boolean removeElement(Object obj) {
        File category = (File)obj;
        
        int index = files.indexOf(category);
        boolean remove = files.remove(category);
        fireIntervalRemoved(this, index, index);
        return remove;
    }

    @Override
    public void addElement(Object obj) {
        files.add((File) obj);
        fireIntervalAdded(this, getSize(), getSize());
    }

    @Override
    public void removeAllElements() {
        files.clear();
        fireContentsChanged(this, 0, getSize());
    }

    /**
     * Set the files contained on the model. 
     * 
     * @param files The files to set on the model. 
     */
    public void setFiles(Collection<File> files) {
        this.files.clear();
        this.files.addAll(files);
        fireContentsChanged(this, 0, getSize());
    }

    /**
     * Return all the files of the model. 
     * 
     * @return A List containing all the files of the model. 
     */
    public Collection<File> getFiles() {
        return files;
    }
}