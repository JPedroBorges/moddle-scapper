package com.roomelephant.moopper.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class AbstractController<T extends Map<LocalDate, List<S>>, S> {

    protected static final int URL_MAX_LENGTH = 82;
    protected static final int NAME_MAX_LENGTH = 44;
    protected static final int EXERCISE_MAX_LENGTH = 30;
    protected static final String TAB = " ".repeat(4);
    protected static final String TABLE_SEPARATOR = "-".repeat(TAB.length() + URL_MAX_LENGTH + TAB.length() + NAME_MAX_LENGTH + TAB.length() + EXERCISE_MAX_LENGTH);

    public void show(T elements) {
        printHeader(elements);
        pintBody(elements);
        printFooter();
    }

    public abstract void close();

    private void printHeader(T elements) {
        System.out.println(TABLE_SEPARATOR);
        headerSpecifics(elements);
        System.out.println(TABLE_SEPARATOR);
    }

    protected abstract void headerSpecifics(T elements);

    protected abstract void pintBody(T elements);

    private void printFooter() {
        System.out.println(TABLE_SEPARATOR);
    }

    protected int elementsCount(T elements) {
        return elements.values().stream()
                .flatMap(Collection::stream)
                .toArray(Object[]::new)
                .length;
    }

    protected Colors getColor(LocalDate date, LocalDate dueDate) {
        Period period = Period.between(dueDate, date);
        int days = period.getDays();
        return (days < 1) ? Colors.RED_BACKGROUND : switch (days) {
            case 1 -> Colors.RED;
            case 2 -> Colors.YELLOW;
            default -> Colors.GREEN;
        };
    }

    protected String fillSpaces(String name, int num) {
        return name + " ".repeat(Math.max(0, num - name.length()));
    }
}
