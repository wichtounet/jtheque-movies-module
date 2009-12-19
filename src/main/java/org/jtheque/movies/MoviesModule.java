package org.jtheque.movies;

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

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.IErrorManager;
import org.jtheque.core.managers.error.InternationalizedError;
import org.jtheque.core.managers.feature.Feature;
import org.jtheque.core.managers.feature.Feature.FeatureType;
import org.jtheque.core.managers.feature.IFeatureManager;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.module.annotations.Module;
import org.jtheque.core.managers.module.annotations.Plug;
import org.jtheque.core.managers.module.annotations.PrePlug;
import org.jtheque.core.managers.module.annotations.UnPlug;
import org.jtheque.core.managers.module.beans.CollectionBasedModule;
import org.jtheque.core.managers.schema.ISchemaManager;
import org.jtheque.core.managers.schema.Schema;
import org.jtheque.core.managers.state.IStateManager;
import org.jtheque.core.managers.state.StateException;
import org.jtheque.core.managers.view.ViewComponent;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.components.TabComponent;
import org.jtheque.movies.persistence.MoviesSchema;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.impl.IOpeningConfigView;
import org.jtheque.primary.PrimaryUtils;
import org.jtheque.primary.services.able.ICollectionsService;
import org.jtheque.primary.utils.DataTypeManager;
import org.jtheque.primary.view.impl.choice.ChoiceAction;
import org.jtheque.primary.view.impl.choice.ChoiceActionFactory;
import org.jtheque.primary.view.impl.sort.Sorter;
import org.jtheque.primary.view.impl.sort.SorterFactory;

/**
 * A JTheque Module for managing movies.
 *
 * @author Baptiste Wicht
 */
@Module(id = "jtheque-movies-module", i18n = "classpath:org/jtheque/movies/i18n/movies", version = "1.3-SNAPSHOT", core = "2.0.2",
        jarFile = "jtheque-movies-module-1.3-SNAPSHOT.jar", updateURL = "http://jtheque.developpez.com/public/versions/MoviesModule.versions")
public final class MoviesModule implements CollectionBasedModule, IMoviesModule {
    private final Sorter[] sorters;
    private final ChoiceAction[] choiceActions;

    private Feature categoriesFeature;

    private Schema schema;

    private IMovieConfiguration config;

    private final IOpeningConfigView panelConfig;

    /**
     * Create a new MovieModule. This constructor must only be accessed using Spring, not directly, expect for tests.
     *
     * @param choiceActions The choice actions to inject.
     * @param sorters       The sorters to inject.
     * @param panelConfig   The panel config.
     */
    public MoviesModule(ChoiceAction[] choiceActions, Sorter[] sorters, IOpeningConfigView panelConfig){
        super();

        this.choiceActions = choiceActions;
        this.sorters = sorters;
        this.panelConfig = panelConfig;
    }

    /**
     * Pre plug the module.
     */
    @PrePlug
    private void prePlug(){
        schema = new MoviesSchema();

        Managers.getManager(ISchemaManager.class).registerSchema(schema);

        PrimaryUtils.setPrimaryImpl("Movies");
        PrimaryUtils.prePlug();
    }

    /**
     * Plug the module.
     */
    @Plug
    private void plug(){
        PrimaryUtils.plug();

        loadConfiguration();

        DataTypeManager.bindDataTypeToKey(ICollectionsService.DATA_TYPE, "data.titles.collection");
        DataTypeManager.bindDataTypeToKey(ICategoriesService.DATA_TYPE, "data.titles.category");
        DataTypeManager.bindDataTypeToKey(IMoviesService.DATA_TYPE, "data.titles.movie");

        for (Sorter sorter : sorters){
            SorterFactory.getInstance().addSorter(sorter);
        }
    }

    /**
     * Load the configuration.
     */
    private void loadConfiguration(){
        config = Managers.getManager(IStateManager.class).getState(MovieConfiguration.class);

        if (config == null){
            try {
                config = Managers.getManager(IStateManager.class).createState(MovieConfiguration.class);
            } catch (StateException e){
                Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
                config = new MovieConfiguration();
                Managers.getManager(IErrorManager.class).addError(new InternationalizedError("error.loading.configuration"));
            }
        }
    }

    @Override
    public boolean chooseCollection(String collection, String password, boolean create){
        ICollectionsService collectionsService = Managers.getManager(IBeansManager.class).getBean("collectionsService");

        if (create){
            collectionsService.createCollectionAndUse(collection, password);
        } else {
            if (!collectionsService.login(collection, password)){
                return false;
            }
        }

        return true;
    }

    @Override
    public void plugCollection(){
        NativeInterface.open();

        for (ChoiceAction action : choiceActions){
            ChoiceActionFactory.addChoiceAction(action);
        }

        panelConfig.build();
        Managers.getManager(IViewManager.class).addConfigTabComponent(panelConfig);

        Managers.getManager(IViewManager.class).setMainComponent(Managers.getManager(IBeansManager.class).<ViewComponent>getBean("movieView"));

        IFeatureManager manager = Managers.getManager(IFeatureManager.class);

        manager.addSubFeature(manager.getFeature(IFeatureManager.CoreFeature.FILE), "importFolderAction", FeatureType.SEPARATED_ACTION, 100);
        manager.addSubFeature(manager.getFeature(IFeatureManager.CoreFeature.FILE), "cleanCategoryAction", FeatureType.SEPARATED_ACTION, 101);

        categoriesFeature = manager.createFeature(500, FeatureType.PACK, "category.menu.title");

        manager.addSubFeature(categoriesFeature, "newCategoryAction", FeatureType.ACTION, 1);
        manager.addSubFeature(categoriesFeature, "editCategoryAction", FeatureType.ACTION, 2);
        manager.addSubFeature(categoriesFeature, "deleteCategoryAction", FeatureType.ACTION, 3);

        Managers.getManager(IFeatureManager.class).addFeature(categoriesFeature);
    }

    /**
     * Unplug the module.
     */
    @UnPlug
    private void unplug(){
        Managers.getManager(IFeatureManager.class).removeFeature(categoriesFeature);

        for (ChoiceAction action : choiceActions){
            ChoiceActionFactory.removeChoiceAction(action);
        }

        Managers.getManager(IViewManager.class).removeConfigTabComponent(panelConfig);

        DataTypeManager.unbindDataType(ICategoriesService.DATA_TYPE);
        DataTypeManager.unbindDataType(IMoviesService.DATA_TYPE);
        DataTypeManager.unbindDataType(ICollectionsService.DATA_TYPE);

        for (Sorter sorter : sorters){
            SorterFactory.getInstance().removeSorter(sorter);
        }

        Managers.getManager(IViewManager.class).removeTabComponent(Managers.getManager(IBeansManager.class).<TabComponent>getBean("movieView"));

        PrimaryUtils.unplug();

        Managers.getManager(ISchemaManager.class).unregisterSchema(schema);

        NativeInterface.runEventPump();
    }

    @Override
    public IMovieConfiguration getConfig(){
        return config;
    }
}
