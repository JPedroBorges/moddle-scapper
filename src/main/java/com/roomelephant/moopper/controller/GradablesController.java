package com.roomelephant.moopper.controller;

import com.roomelephant.moopper.model.Gradable;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class GradablesController {

    public static final int URL_MAX_LENGTH = 82;
    public static final int NAME_MAX_LENGTH = 42;
    public static final int EXERCISE_MAX_LENGTH = 30;
    public static final String TAB = " ".repeat(4);
    public static final String TABLE_SEPARATOR = "-".repeat(TAB.length() + URL_MAX_LENGTH + TAB.length() + NAME_MAX_LENGTH + TAB.length() + EXERCISE_MAX_LENGTH);
    public static final String TOTAL_GRADABLES = "Total gradablesByDate: ";

    public void presentGrdables(Map<LocalDate, List<Gradable>> gradablesByDate) {
        printHeader(gradablesByDate);
        pintBody(gradablesByDate);
        printFooter();
    }

    private void printHeader(Map<LocalDate, List<Gradable>> gradablesByDate) {
        System.out.println(TABLE_SEPARATOR);
        System.out.println(TOTAL_GRADABLES + gradablesCount(gradablesByDate));
        System.out.println(Colors.BOLD + TAB + fillSpaces("Link", URL_MAX_LENGTH) + TAB + fillSpaces("Name", NAME_MAX_LENGTH) + TAB + fillSpaces("Exercise", EXERCISE_MAX_LENGTH));
        System.out.println(TABLE_SEPARATOR);
    }

    private void pintBody(Map<LocalDate, List<Gradable>> gradablesByDate) {
        LocalDate oneWeekBefore = LocalDate.now().minusWeeks(1);
        gradablesByDate.keySet().
                forEach(date -> {
                    List<Gradable> dailyGradable = gradablesByDate.get(date);
                    Colors color = getColor(date, oneWeekBefore);

                    System.out.println("due: " + color.toString() + date.plusWeeks(1) + Colors.RESET + "\tsubmitted: " + date + "\t(" + dailyGradable.size() + ")");

                    dailyGradable.stream()
                            .sorted(Comparator.comparing(Gradable::exercise))
                            .map(this::gradable)
                            .forEach(System.out::println);
                });
    }

    private static void printFooter() {
        System.out.println(TABLE_SEPARATOR);
    }

    private static int gradablesCount(Map<LocalDate, List<Gradable>> gradablesByDate) {
        return gradablesByDate.values().stream().flatMap(Collection::stream).toArray(Gradable[]::new).length;
    }


    private Colors getColor(LocalDate date, LocalDate oneWeekBefore) {
        Period period = Period.between(oneWeekBefore, date);
        int days = period.getDays();
        return (days < 1) ? Colors.RED_BACKGROUND : switch (days) {
            case 1 -> Colors.RED;
            case 2 -> Colors.YELLOW;
            default -> Colors.GREEN;
        };
    }

    public String gradable(Gradable gradable) {
        return TAB + fillSpaces(gradable.link().toString(), URL_MAX_LENGTH)
                + TAB + fillSpaces(gradable.name(), NAME_MAX_LENGTH)
                + TAB + fillSpaces(gradable.exercise(), EXERCISE_MAX_LENGTH);
    }

    private String fillSpaces(String name, int num) {
        return name + " ".repeat(Math.max(0, num - name.length()));
    }
}
