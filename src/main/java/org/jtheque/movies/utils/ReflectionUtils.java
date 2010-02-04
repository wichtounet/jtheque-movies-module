package org.jtheque.movies.utils;

import org.jtheque.utils.StringUtils;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

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
 * temporary reflection utility class.
 *
 * @author Baptiste Wicht
 */
public class ReflectionUtils {
    /**
     * Utility class, not instanciable.
     */
    private ReflectionUtils() {
        super();
    }

    /**
     * Create a quick memento using the specified fields.
     *
     * @param bean The bean to create the memento from.
     * @param fields The fields to use.
     *
     * @param <T> The type of data.
     *
     * @return A memento of the bean.
     */
    public static <T> T createQuickMemento(T bean, String... fields) {
        T instance = null;

        try {
            instance = (T) bean.getClass().newInstance();

            for (String field : fields) {
                Object value = org.jtheque.utils.bean.ReflectionUtils.getPropertyValue(bean, field);

                getSetterMethod(instance, field).invoke(instance, value);
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
        }

        return instance;
    }

    /**
     * Restore the quick memento to the bean.
     *
     * @param bean The bean to restore.
     * @param memento The memento to restore.
     * @param fields The fields to use. 
     */
    public static void restoreQuickMemento(Object bean, Object memento, String... fields) {
        try {
            for (String field : fields) {
                Object value = org.jtheque.utils.bean.ReflectionUtils.getPropertyValue(memento, field);

                getSetterMethod(bean, field).invoke(bean, value);
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(ReflectionUtils.class).error(e.getMessage(), e);
        }
    }



    /**
     * Return the name of the setter for the specified property name.
     *
     * @param property The property name.
     *
     * @return The name of the setter for the specified property.
     */
    public static String getSetter(String property) {
        return "set" + StringUtils.setFirstLetterUpper(property);
    }

    /**
     * Return the setter method for the property of the specified bean.
     *
     * @param bean     The bean to set the property to.
     * @param property The property name.
     *
     * @return The setter Method object or null if there is no setter for this property.
     */
    public static Method getSetterMethod(Object bean, String property) {
        String setter = getSetter(property);

        for(Method m : bean.getClass().getMethods()){
            if(m.getName().equals(setter)){
                return m;
            }
        }

        return null;
    }
}