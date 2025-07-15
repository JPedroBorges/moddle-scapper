package com.roomelephant.moopper.controller;

import com.roomelephant.moopper.model.Gradable;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

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
        System.out.println(Colors.BOLD
                + TAB + fillSpaces("Link", URL_MAX_LENGTH)
                + TAB + fillSpaces("Name", NAME_MAX_LENGTH)
                + TAB + fillSpaces("Exercise", EXERCISE_MAX_LENGTH));
    }

    @Override
    protected void pintBody(Map<LocalDate, List<Gradable>> gradablesByDate) {
        AtomicBoolean shouldContinue = new AtomicBoolean(true);

        LocalDate oneWeekBefore = LocalDate.now().minusWeeks(1);
        LocalDate now = LocalDate.now();
        try {
            gradablesByDate.keySet()
                    .forEach(date -> {
                        if (!shouldContinue.get()) return;

                        List<Gradable> dailyGradable = gradablesByDate.get(date);
                        Colors color = getColor(date, oneWeekBefore);

                        if (iterable) {
                            if (color.equals(Colors.YELLOW) || color.equals(Colors.GREEN)) {
                                waitFeedback(shouldContinue);
                            }

                        }

                        LocalDate deadLine = date.plusWeeks(1);
                        Period period = Period.between(date, now);
                        System.out.println("due: " + color.toString() + deadLine + Colors.RESET
                                + "\tsubmitted: " + date + "\telapsed: " + period.getDays() + " days " + "\t(" + dailyGradable.size() + ")");
                        AtomicBoolean contrast = new AtomicBoolean(false);
                        dailyGradable.stream()
                                .sorted(Comparator.comparing(Gradable::exercise))
                                .map(this::gradable)
                                .forEach(e -> {
                                    System.out.println(contrast.get() ? Colors.GRAY_BACKGROUND + e + Colors.RESET : e);
                                    contrast.set(!contrast.get());
                                });
                    });
        } catch (RuntimeException ignored) {
        }
    }

    private void waitFeedback(AtomicBoolean shouldContinue) {
        System.out.print(":");
        String input = scanner.nextLine();
        if (EXIT_TOKENS.contains(input)) {
            shouldContinue.set(false);
        }
        System.out.print("\033[1A");
        System.out.print("\033[2K");
    }

    public String gradable(Gradable gradable) {
        return TAB + fillSpaces(gradable.link().toString(), URL_MAX_LENGTH)
                + TAB + fillSpaces(gradable.name(), NAME_MAX_LENGTH)
                + TAB + fillSpaces(gradable.exercise(), EXERCISE_MAX_LENGTH);
    }
}
