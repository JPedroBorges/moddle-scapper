package com.roomelephant.moopper.controller;

import com.roomelephant.moopper.model.Gradable;

import java.time.LocalDate;
import java.util.*;

public class GradablesController extends AbstractController<Map<LocalDate, List<Gradable>>, Gradable> implements AutoCloseable {

    private static final String TOTAL_GRADABLES = "Total gradables: ";
    private static final Set<String> EXIT_TOKENS = Set.of("q", ":q", "Q", ":Q", "ZZ");
    private final Scanner scanner;
    private boolean iterable;

    public GradablesController() {
        this.scanner = new Scanner(System.in);
        this.iterable = false;
    }

    @Override
    public void close() {
        scanner.close();
    }

    public void setIterable(boolean iterable) {
        this.iterable = iterable;
    }

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
        try {
            gradablesByDate.keySet()
                    .forEach(date -> {
                        List<Gradable> dailyGradable = gradablesByDate.get(date);
                        Colors color = getColor(date, oneWeekBefore);

                        if (iterable) {
                            if (color.equals(Colors.YELLOW) || color.equals(Colors.GREEN)) {
                                System.out.print(":");
                                String input = scanner.nextLine();
                                if (EXIT_TOKENS.contains(input)) {
                                    throw new RuntimeException();
                                }
                            }

                        }

                        System.out.println("due: " + color.toString() + date.plusWeeks(1) + Colors.RESET
                                + "\tsubmitted: " + date + "\t(" + dailyGradable.size() + ")");

                        dailyGradable.stream()
                                .sorted(Comparator.comparing(Gradable::exercise))
                                .map(this::gradable)
                                .forEach(System.out::println);
                    });
        } catch (RuntimeException ignored) {
        }
    }

    public String gradable(Gradable gradable) {
        return TAB + fillSpaces(gradable.link().toString(), URL_MAX_LENGTH)
                + TAB + fillSpaces(gradable.name(), NAME_MAX_LENGTH)
                + TAB + fillSpaces(gradable.exercise(), EXERCISE_MAX_LENGTH);
    }
}
