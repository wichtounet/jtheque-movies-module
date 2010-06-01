package org.jtheque.movies.views.impl.models;

import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.i18n.able.Internationalizable;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;

import javax.swing.table.AbstractTableModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
 * A table model for invalid files.
 *
 * @author Baptiste Wicht
 */
public final class FilesTableModel extends AbstractTableModel implements Internationalizable {
    private final IMoviesService moviesService;

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
     *
     * @param moviesService The movies service.
     */
    public FilesTableModel(IMoviesService moviesService) {
        super();

        this.moviesService = moviesService;
    }

    @Override
    public void refreshText(ILanguageService languageService) {
        headers = new String[]{
                languageService.getMessage("files.table.name"),
                languageService.getMessage("files.table.file")
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
        if (value != null && columnIndex == Columns.FILE) {
            Movie movie = movies.get(rowIndex);

            movie.setFile(((File) value).getAbsolutePath());

            moviesService.save(movie);
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
        if (columnIndex == Columns.FILE) {
            return File.class;
        }

        return super.getColumnClass(columnIndex);
    }

    /**
     * Refresh the data.
     */
    public void refresh() {
        movies.clear();
        movies.addAll(moviesService.getMoviesWithInvalidFiles());

        fireTableStructureChanged();
    }
}