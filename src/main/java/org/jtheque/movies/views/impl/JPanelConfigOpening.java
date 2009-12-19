package org.jtheque.movies.views.impl;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.movies.IMovieConfiguration;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.utils.OSUtils;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.util.Collection;

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
 * A config panel to configure the opening system.
 *
 * @author Baptiste Wicht
 */
public final class JPanelConfigOpening extends JPanel implements IOpeningConfigView {
    private static final long serialVersionUID = 1549545390644920034L;

    private JComboBox combo;

    @Override
    public String getTitle(){
        return Managers.getManager(ILanguageManager.class).getMessage("movie.config");
    }

    @Override
    public void build(){
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run(){
                PanelBuilder parent = new PanelBuilder(JPanelConfigOpening.this);

                PanelBuilder builder = parent.addPanel(parent.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING));
                builder.getPanel().setBorder(Borders.createTitledBorder("movie.config.opening"));

                combo = builder.add(new JComboBox(), builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING));

                combo.addItem(IMovieConfiguration.Opening.SYSTEM);

                if (OSUtils.isLinux()){
                    combo.addItem(IMovieConfiguration.Opening.VLC);
                } else if (OSUtils.isWindows()){
                    combo.addItem(IMovieConfiguration.Opening.WMP);
                }

                fillAllFields();
            }
        });
    }

    /**
     * Fill all the fields with the current informations.
     */
    private void fillAllFields(){
        IMoviesModule module = Managers.getManager(IBeansManager.class).getBean("moviesModule");

        combo.setSelectedItem(module.getConfig().getOpeningSystem());
    }

    @Override
    public void apply(){
        IMoviesModule module = Managers.getManager(IBeansManager.class).getBean("moviesModule");

        module.getConfig().setOpeningSystem((IMovieConfiguration.Opening) combo.getSelectedItem());
    }

    @Override
    public void cancel(){
        fillAllFields();
    }

    @Override
    public void validate(Collection<JThequeError> errors){
        //Nothing to validate in the view
    }

    @Override
    public JComponent getComponent(){
        return this;
    }
}