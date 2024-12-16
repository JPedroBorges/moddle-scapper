package com.roomelephant.moopper;

import com.roomelephant.moopper.configuration.bean.BeanManager;

public class Main {
    public static void main(String[] args) {
        BeanManager beanManager = new BeanManager();

        App app = beanManager.app();
        app.launch();
    }
}