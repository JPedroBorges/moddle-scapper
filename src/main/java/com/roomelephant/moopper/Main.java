package com.roomelephant.moopper;

import com.roomelephant.moopper.configuration.bean.BeanFactory;

public class Main {
    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory();

        App app = beanFactory.app();
        app.launch();
    }
}