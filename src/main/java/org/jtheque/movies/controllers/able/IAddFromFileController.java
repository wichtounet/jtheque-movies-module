package org.jtheque.movies.controllers.able;

import org.jtheque.core.managers.view.able.controller.Controller;
import org.jtheque.movies.services.impl.parsers.FileParser;
import org.jtheque.movies.views.able.IAddFromFileView;

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

public interface IAddFromFileController extends Controller {
	@Override
    IAddFromFileView getView();
    
    void add(String filePath, Collection<FileParser> parsers);
}