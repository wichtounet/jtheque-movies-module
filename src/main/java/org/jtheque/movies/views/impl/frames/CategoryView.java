package org.jtheque.movies.views.impl.frames;

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

import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.views.able.ICategoryView;
import org.jtheque.movies.views.able.models.ICategoryModel;
import org.jtheque.movies.views.impl.actions.categories.ValidateCategoryViewAction;
import org.jtheque.movies.views.impl.models.CategoriesComboModel;
import org.jtheque.movies.views.impl.models.CategoryModel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.constraints.MaxLengthConstraint;
import org.jtheque.ui.utils.filthy.FilthyRenderer;
import org.jtheque.ui.utils.windows.dialogs.SwingFilthyBuildedDialogView;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.ui.utils.filthy.FilthyTextField;

import javax.annotation.Resource;
import javax.swing.Action;

/**
 * User interface to add or modify a view.
 *
 * @author Baptiste Wicht
 */
public final class CategoryView extends SwingFilthyBuildedDialogView<ICategoryModel> implements ICategoryView {
    private FilthyTextField fieldName;
    private CategoriesComboModel categoriesModel;

    private static final int FIELD_COLUMNS = 15;

    @Resource
    private ICategoriesService categoriesService;
	
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
        SwingUtils.addFieldValidateAction(fieldName, saveAction);

	    addConstraint(fieldName.getField(), new MaxLengthConstraint(Category.NAME_LENGTH, Category.NAME, false, false));

        builder.addI18nLabel(Category.PARENT, builder.gbcSet(0, 1));

        categoriesModel = new CategoriesComboModel(categoriesService);

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
    public void refreshText(ILanguageService languageService) {
        super.refreshText(languageService);

        if (getModel().getCategory() != null) {
            setTitle(getMessage("category.view.title.modify") + getModel().getCategory().getDisplayableText());
        }
    }
}