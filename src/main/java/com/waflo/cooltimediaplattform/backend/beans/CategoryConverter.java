package com.waflo.cooltimediaplattform.backend.beans;

import com.waflo.cooltimediaplattform.backend.model.Category;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.List;
import java.util.function.Predicate;

@FacesConverter(forClass = Category.class)
@Component
public class CategoryConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Object o = null;
        if (!(value == null || value.isEmpty())) {
            o = this.getSelectedItemAsEntity(component, value);
        }
        return o;
    }

    private Object getSelectedItemAsEntity(UIComponent component, String value) {

        Category item = null;

        List<Category> selectItems = null;
        for (UIComponent uic : component.getChildren()) {
            if (uic instanceof UISelectItems) {
                //Long itemId = Long.valueOf(value);
                selectItems = (List<Category>) ((UISelectItems) uic).getValue();

                if (selectItems != null && !selectItems.isEmpty()) {
                    Predicate<Category> predicate = i -> i.getName().equals(value);
                    item = selectItems.stream().filter(predicate).findFirst().orElse(null);
                }
            }

        }
        return item;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Category) {
            return ((Category) value).getName();
        }
        return "";

    }
}
