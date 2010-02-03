package org.jtheque.movies.views.impl.models;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.language.Internationalizable;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
 * A table model for invalid files.
 *
 * @author Baptiste Wicht 
 */
public final class FilesTableModel extends AbstractTableModel implements Internationalizable {
    /**
     * The different columns of the files table model.
     *
     * @author Baptiste Wicht
     */
    private interface Columns {
        int NAME = 0;
        int FILE = 1;
    }
    
    private String[] headers;

    private final List<Movie> movies = new ArrayList<Movie>(25);

    /**
     * Construct a new <code>FilmsToBuyTableModel</code>.
     */
    public FilesTableModel() {
        super();

        refreshHeaders();
    }

    @Override
    public void refreshText() {
        refreshHeaders();
    }

    /**
     * Refresh the headers.
     */
    public void refreshHeaders() {
        headers = new String[]{
                Managers.getManager(ILanguageManager.class).getMessage("files.table.name"),
                Managers.getManager(ILanguageManager.class).getMessage("files.table.file")
        };

        fireTableStructureChanged();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;

        Movie movie = movies.get(rowIndex);

        if (movie != null) {
            switch (columnIndex) {
                case Columns.NAME:
                    value = movie.getTitle();
                    break;
                case Columns.FILE:
                    value = new File(movie.getFile());
                    break;
                default:
                    value = "";
                    break;
            }
        }

        return value;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (value != null && columnIndex == Columns.FILE){
            Movie movie = movies.get(rowIndex);

            movie.setFile(((File) value).getAbsolutePath());

            CoreUtils.<IMoviesService>getBean("moviesService").save(movie);
        }
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == Columns.FILE;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex == Columns.FILE){
            return File.class;
        }

        return super.getColumnClass(columnIndex);
    }

    /**
     * Refresh the data. 
     *
     */
    public void refresh() {
        movies.clear();
        movies.addAll(CoreUtils.<IMoviesService>getBean("moviesService").getMoviesWithInvalidFiles());

        fireTableStructureChanged();
    }
}