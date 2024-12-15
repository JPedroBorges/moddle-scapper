package com.roomelephant.moodlescrapper.Presentation;

import com.roomelephant.moodlescrapper.model.Gradable;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class GradablesPresentation {
    public void presentGrdables(TreeMap<LocalDate, List<Gradable>> gradablesByDate) {
        LocalDate oneWeekBefore = LocalDate.now().minusWeeks(1);


        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Total gradablesByDate: " + gradablesByDate.values().stream().flatMap(Collection::stream).toArray(Gradable[]::new).length);
        System.out.println(Colors.BOLD + "\tLink\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tName\t\t\t\t\t\t\t\t\t\tExercise                     ");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");


        TreeMap<LocalDate, List<Gradable>> finalGradablesByDate = gradablesByDate;
        gradablesByDate.keySet().

                forEach(date ->

                {
                    List<Gradable> dailyGradable = finalGradablesByDate.get(date);
                    Colors color = getColor(date, oneWeekBefore);

                    System.out.println("due: " + color.toString() + date.plusWeeks(1) + Colors.RESET + "\tsubmitted: " + date + "\t(" + dailyGradable.size() + ")");


                    dailyGradable.stream()
                            .sorted(Comparator.comparing(Gradable::exercise))
                            .map(Gradable::toString)
                            .forEach(System.out::println);
                });

        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");
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
}
