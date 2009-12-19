package org.jtheque.movies.controllers.impl.states;

import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.Data;

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
 * @author Baptiste Wicht
 */
public class AbstractMovieState implements ControllerState {
    @Override
    public void apply(){
        //No action
    }

    @Override
    public ControllerState save(FormBean infos){
        //No action

        return null;
    }

    @Override
    public ControllerState cancel(){
        //No action

        return null;
    }

    @Override
    public ControllerState create(){
        //No action

        return null;
    }

    @Override
    public ControllerState delete(){
        //No action

        return null;
    }

    @Override
    public ControllerState manualEdit(){
        //No action

        return null;
    }

    @Override
    public ControllerState view(Data data){
        //No action

        return null;
    }

    @Override
    public ControllerState autoEdit(Data data){
        throw new UnsupportedOperationException();
    }
}