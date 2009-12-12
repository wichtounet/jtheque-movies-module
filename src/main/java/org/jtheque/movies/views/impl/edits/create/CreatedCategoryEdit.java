package org.jtheque.movies.views.impl.edits.create;

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
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;

import javax.annotation.Resource;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 * An edit corresponding to a creation of an editor.
 *
 * @author Baptiste Wicht
 */
public final class CreatedCategoryEdit extends AbstractUndoableEdit {
    private static final long serialVersionUID = -3037357344759861059L;

    private final Category category;

    @Resource
    private ICategoriesService categoriesService;

    /**
     * Construct a new CreatedEditorEdit.
     *
     * @param category The created editor.
     */
    public CreatedCategoryEdit(Category category) {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        this.category = category;
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();

        categoriesService.delete(category);
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();

        categoriesService.create(category);
    }

    @Override
    public String getPresentationName() {
        return Managers.getManager(ILanguageManager.class).getMessage("undo.edits.create");
    }
}