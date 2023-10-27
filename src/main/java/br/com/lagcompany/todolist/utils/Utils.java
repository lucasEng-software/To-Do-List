package br.com.lagcompany.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

    public static void copyNonNullPropperties ( Object source, Object target){
        // copy valores de um objeto para outro objeto, utiliza uma regra que seria a classe passada
        BeanUtils.copyProperties(source, target, getNullProppertyNames(source));
    }
    public static String[] getNullProppertyNames(Object source){
        // interface que permite acessar as propriedades de um objeto no java
        // e IMPL é a implementação da inteface
        BeanWrapper bean = new BeanWrapperImpl(source);
        
        // array de propriedades do objeto
        PropertyDescriptor[] pds = bean.getPropertyDescriptors();
        //propriedades de valores nulos
        Set<String> emptyNames = new HashSet<>();

        for(PropertyDescriptor pd:pds){
            Object beanValue = bean.getPropertyValue(pd.getName());
            if(beanValue == null){
                emptyNames.add(pd.getName());
            }
        }
        
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
