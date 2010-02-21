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
import org.jtheque.core.managers.view.impl.components.filthy.FilthyRenderer;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyTextField;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingFilthyBuildedDialogView;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.core.utils.ui.constraints.ConstraintManager;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.views.able.ICategoryView;
import org.jtheque.movies.views.able.models.ICategoryModel;
import org.jtheque.movies.views.impl.actions.categories.ValidateCategoryViewAction;
import org.jtheque.movies.views.impl.models.CategoriesComboModel;
import org.jtheque.movies.views.impl.models.CategoryModel;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.Action;
import java.util.Collection;

/**
 * User interface to add or modify a view.
 *
 * @author Baptiste Wicht
 */
public final class CategoryView extends SwingFilthyBuildedDialogView<ICategoryModel> implements ICategoryView {
    private FilthyTextField fieldName;
    private CategoriesComboModel categoriesModel;

    private static final int FIELD_COLUMNS = 15;

    /**
     * Construct a new CategoryView.
     */
    public CategoryView() {
        super();

        build();
    }

    @Override
    protected void initView() {
        setModel(new CategoryModel());

        setResizable(false);
    }

    @Override
    public void reload() {
        Category category = getModel().getCategory();

        if (category.isSaved()) {
            setTitle(getMessage("category.view.title.modify") + category.getDisplayableText());
        } else {
            setTitleKey("category.view.title");
        }

        fieldName.setText(category.getTitle());
        categoriesModel.setSelectedItem(category.getParent());
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        builder.addI18nLabel(Category.NAME, builder.gbcSet(0, 0));

        Action saveAction = new ValidateCategoryViewAction();

        fieldName = builder.add(new FilthyTextField(FIELD_COLUMNS), builder.gbcSet(1, 0));
        ConstraintManager.configure(fieldName.getField(), Category.NAME);
        SwingUtils.addFieldValidateAction(fieldName, saveAction);

        builder.addI18nLabel(Category.PARENT, builder.gbcSet(0, 1));

        categoriesModel = new CategoriesComboModel();

        builder.addComboBox(categoriesModel, new FilthyRenderer(), builder.gbcSet(1, 1));

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL, 2, 1), saveAction, getCloseAction("category.actions.cancel"));
    }

    @Override
    public String getCategoryName() {
        return fieldName.getText();
    }

    @Override
    public Category getSelectedCategory() {
        return categoriesModel.getSelectedCategory();
    }

    @Override
    public void refreshText() {
        super.refreshText();

        if (getModel().getCategory() != null) {
            setTitle(getMessage("category.view.title.modify") + getModel().getCategory().getDisplayableText());
        }
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
        ConstraintManager.validate(Category.NAME, fieldName.getText(), errors);
    }
}