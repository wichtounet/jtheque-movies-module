package org.jtheque.movies.views.impl.edits.delete;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;

import javax.annotation.Resource;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 * An edit corresponding to a delete of a film.
 *
 * @author Baptiste Wicht
 */
public final class DeletedMovieEdit extends AbstractUndoableEdit {
    private final Movie movie;

    @Resource
    private IMoviesService moviesService;

    /**
     * Construct a new DeletedFilmEdit.
     *
     * @param movie The deleted movie.
     */
    public DeletedMovieEdit(Movie movie){
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        this.movie = movie;
    }

    @Override
    public void undo() throws CannotUndoException{
        super.undo();

        moviesService.create(movie);
    }

    @Override
    public void redo() throws CannotRedoException{
        super.redo();

        moviesService.delete(movie);
    }

    @Override
    public String getPresentationName(){
        return Managers.getManager(ILanguageManager.class).getMessage("undo.edits.delete");
    }
}