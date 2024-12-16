package com.roomelephant.moodlescrapper;

import com.roomelephant.moodlescrapper.configuration.bean.BeanManager;

public class Main {
    public static void main(String[] args) {
        BeanManager beanManager = new BeanManager();

        App app = beanManager.app();
        app.launch();
    }
}