package com.roomelephant.moopper.controller;

import com.roomelephant.moopper.model.Gradable;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class GradablesController extends AbstractController<Map<LocalDate, List<Gradable>>, Gradable> {

    private static final String TOTAL_GRADABLES = "Total gradables: ";

    @Override
    protected void headerSpecifics(Map<LocalDate, List<Gradable>> gradablesByDate) {
        System.out.println(TOTAL_GRADABLES + elementsCount(gradablesByDate));
        System.out.println(Colors.BOLD + TAB + fillSpaces("Link", URL_MAX_LENGTH)
                + TAB + fillSpaces("Name", NAME_MAX_LENGTH)
                + TAB + fillSpaces("Exercise", EXERCISE_MAX_LENGTH));
    }

    @Override
    protected void pintBody(Map<LocalDate, List<Gradable>> gradablesByDate) {
        LocalDate oneWeekBefore = LocalDate.now().minusWeeks(1);
        gradablesByDate.keySet()
                .forEach(date -> {
                    List<Gradable> dailyGradable = gradablesByDate.get(date);
                    Colors color = getColor(date, oneWeekBefore);

                    System.out.println("due: " + color.toString() + date.plusWeeks(1) + Colors.RESET
                            + "\tsubmitted: " + date + "\t(" + dailyGradable.size() + ")");

                    dailyGradable.stream()
                            .sorted(Comparator.comparing(Gradable::exercise))
                            .map(this::gradable)
                            .forEach(System.out::println);
                });
    }

    public String gradable(Gradable gradable) {
        return TAB + fillSpaces(gradable.link().toString(), URL_MAX_LENGTH)
                + TAB + fillSpaces(gradable.name(), NAME_MAX_LENGTH)
                + TAB + fillSpaces(gradable.exercise(), EXERCISE_MAX_LENGTH);
    }
}
