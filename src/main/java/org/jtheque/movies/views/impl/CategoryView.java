package org.jtheque.movies.views.impl;

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
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.impl.actions.utils.CloseViewAction;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.views.able.ICategoryView;
import org.jtheque.movies.views.impl.actions.categories.AcValidateCategoryView;
import org.jtheque.movies.views.impl.models.CategoryModel;
import org.jtheque.movies.views.impl.models.able.ICategoryModel;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.Action;
import javax.swing.JTextField;
import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

/**
 * User interface to add or modify a view.
 *
 * @author Baptiste Wicht
 */
public final class CategoryView extends SwingDialogView implements ICategoryView {
    private JTextField fieldName;

    private static final int CATEGORY_NAME_MAX_LENGTH = 100;
    private static final int FIELD_COLUMNS = 15;

    /**
     * Construct a new Category View.
     *
     * @param parent       The parent frame.
     */
    public CategoryView(Frame parent){
        super(parent);

        setModel(new CategoryModel());

        setContentPane(buildContentPane());
        setResizable(false);

        reload();
        pack();

        setLocationRelativeTo(getOwner());
    }

    @Override
    public void reload(){
        setTitleKey("category.view.title");

        getModel().setCategory(null);

        fieldName.setText("");
    }

    @Override
    public void reload(Category category){
        getModel().setCategory(category);

        setTitle(Managers.getManager(ILanguageManager.class).getMessage("category.view.title.modify") +
                category.getDisplayableText());

        fieldName.setText(category.getTitle());
    }

    /**
     * Build and return the content pane.
     *
     * @return Le contentPane
     */
    private Container buildContentPane(){
        PanelBuilder builder = new PanelBuilder();

        builder.addI18nLabel("category.view.name", builder.gbcSet(0, 0));

        Action saveAction = new AcValidateCategoryView();

        fieldName = builder.add(new JTextField(FIELD_COLUMNS), builder.gbcSet(1, 0));
        SwingUtils.addFieldValidateAction(fieldName, saveAction);
        SwingUtils.addFieldLengthLimit(fieldName, CATEGORY_NAME_MAX_LENGTH);

        CloseViewAction closeAction = new CloseViewAction("category.actions.cancel");
        closeAction.setView(this);

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, 2, 1), saveAction, closeAction);

        return builder.getPanel();
    }

    @Override
    public JTextField getFieldName(){
        return fieldName;
    }

    @Override
    public ICategoryModel getModel(){
        return (ICategoryModel) super.getModel();
    }

    @Override
    public void refreshText(){
        super.refreshText();

        if (getModel().getCategory() != null){
            setTitle(Managers.getManager(ILanguageManager.class).getMessage("kind.view.title.modify") +
                    getModel().getCategory().getDisplayableText());
        }
    }

    @Override
    protected void validate(Collection<JThequeError> errors){
        ValidationUtils.rejectIfEmpty(fieldName.getText(), "category.view.name", errors);
        ValidationUtils.rejectIfLongerThan(fieldName.getText(), "category.view.name", CATEGORY_NAME_MAX_LENGTH, errors);
    }
}