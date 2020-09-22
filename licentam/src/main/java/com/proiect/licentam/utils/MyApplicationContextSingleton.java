package com.proiect.licentam.utils;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MyApplicationContextSingleton implements ApplicationContextAware {

    private static MyApplicationContextSingleton instance = null;
    public ApplicationContext applicationContext;

    private MyApplicationContextSingleton(){}

    public static MyApplicationContextSingleton getInstance(){
        if(instance == null){
            instance = new MyApplicationContextSingleton();
        }
        return instance;
    }

    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }
}
