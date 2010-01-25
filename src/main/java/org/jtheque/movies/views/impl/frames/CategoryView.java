package org.jtheque.movies.views.impl.frames;

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

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingBuildedDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.constraints.ConstraintManager;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.views.able.ICategoryView;
import org.jtheque.movies.views.able.models.ICategoryModel;
import org.jtheque.movies.views.impl.actions.categories.AcValidateCategoryView;
import org.jtheque.movies.views.impl.models.CategoryModel;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.Action;
import javax.swing.JTextField;
import java.util.Collection;

/**
 * User interface to add or modify a view.
 *
 * @author Baptiste Wicht
 */
public final class CategoryView extends SwingBuildedDialogView<ICategoryModel> implements ICategoryView {
    private JTextField fieldName;

    private static final int FIELD_COLUMNS = 15;

    public CategoryView(){
        super();

        build();
    }

    @Override
    protected void initView(){
        setModel(new CategoryModel());

        setResizable(false);
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

        setTitle(getMessage("category.view.title.modify") + category.getDisplayableText());

        fieldName.setText(category.getTitle());
    }

    @Override
    protected void buildView(PanelBuilder builder){
        builder.addI18nLabel(Category.NAME, builder.gbcSet(0, 0));

        Action saveAction = new AcValidateCategoryView();

        fieldName = builder.add(new JTextField(FIELD_COLUMNS), builder.gbcSet(1, 0));
		ConstraintManager.configure(fieldName, Category.NAME);
        SwingUtils.addFieldValidateAction(fieldName, saveAction);

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, 2, 1),
                saveAction, getCloseAction("category.actions.cancel"));

        reload();
    }

    @Override
    public JTextField getFieldName(){
        return fieldName;
    }

    @Override
    public void refreshText(){
        super.refreshText();

        if (getModel().getCategory() != null){
            setTitle(getMessage("category.view.title.modify") + getModel().getCategory().getDisplayableText());
        }
    }

    @Override
    protected void validate(Collection<JThequeError> errors){
		ConstraintManager.validate(Category.NAME, fieldName.getText(), errors);
    }
}