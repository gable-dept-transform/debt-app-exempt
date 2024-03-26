package th.co.ais.mimo.debt.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public interface GenerateSourceable {

    default void trim(){
    	if(this.getClass() != null && ArrayUtils.isNotEmpty(this.getClass().getDeclaredFields())) {
    		for (Field field : this.getClass().getDeclaredFields()) {
    			if(field != null) {
    				try {
    					field.setAccessible(true);
    					Object value = field.get(this);
    					if (value != null){
    						if (value instanceof String){
    							field.set(this, StringUtils.trim((String) value));
    						}
    					}
    				} catch(Exception e) {
    					e.printStackTrace();
    				}
    			}
    		}
    	}
    }

}