package com.roomelephant.moodlescrapper.courses;

import com.roomelephant.moodlescrapper.converter.Converter;
import com.roomelephant.moodlescrapper.converter.gradable.GradableConverter;
import com.roomelephant.moodlescrapper.model.Gradable;
import com.roomelephant.moodlescrapper.scrapper.GradableDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public final class JavaCourse implements Course {
    private final Converter<GradableDTO, Gradable>  gradableConverter;
    private final List<GradableDTO> reviews;

    public JavaCourse(GradableConverter gradableConverter, List<GradableDTO> reviews) {
        this.gradableConverter = gradableConverter;
        this.reviews = reviews;
    }

    public TreeMap<LocalDate, List<Gradable>> getReviews() {
        List<Gradable> gradables = convert(reviews);

        return gradables.stream()
                .filter(gradable -> !gradable.exercise().contains("Entregar objetivo definido"))
                //.sorted(Comparator.comparing(Gradable::date))
                .toList().stream()
                .collect(Collectors.groupingBy(
                        gradable -> gradable.date().toLocalDate(),
                        TreeMap::new,
                        Collectors.toList()
                ));
    }

    private List<Gradable> convert(List<GradableDTO> reviews) {
        return reviews.stream().map(gradableConverter::convert).map(this::enrichExercise).toList();
    }

    private Gradable enrichExercise(Gradable gradable) {
        String simpleName = gradable.exercise().replace("Entrega da prática ", "");

        String finalName = switch (simpleName) {
            case "1. Galões" -> "M1 A2 E1 - Galões";
            case "1. Contar espaços" -> "M1 A3 E1 - Contar espaços";
            case "2. Números Primos" -> "M1 A3 E2 - Números Primos";
            case "3. ASCII" -> "M1 A3 E3 - ASCII";
            case "4. Galões II" -> "M1 A3 E4 - Galões II";
            case "5. Função de Ackermann" -> "M1 A3 E5 - Função de Ackermanno";
            case "6. Collatz" -> "M1 A3 E6 - Collatz";
            case "7. Candy Calculator" -> "M1 A3 E7 - Candy Calculator";
            case "1. Sum" -> "M1 A4 E1 - Sum";
            case "2. Ordenar Vector" -> "M1 A4 E2 - Ordenar Vector";
            case "3. Matrizes" -> "M1 A4 E3 - Matrizes";
            case "4. Polymers" -> "M1 A4 E4 - Polymers";
            case "5. Palíndromos" -> "M1 A4 E5 - Palíndromos";
            case "6. Contar vogais" -> "M1 A4 E6 - Contar vogais";
            case "1. Stack" -> "M1 A5 E1 - Stack";
            case "2. Rational" -> "M1 A5 E2 - Rational";
            case "3. Retângulo" -> "M1 A5 E3 - Retângulo";
            case "4. Bag" -> "M1 A5 E4 - Bag";
            case "5. Complex Numbers" -> "M1 A5 E5 - Complex Numbers";
            case "1. Circle" -> "M1 A6 E1 - Circle";
            case "2. Universidade" -> "M1 A6 E2 - Universidade";
            case "3. Mercado" -> "M1 A6 E3 - Mercado";
            case "4. Cartas" -> "M1 A6 E4 - Cartas";
            case "5. Instrumentos" -> "M1 A6 E5 - Instrumentos";
            case "1. CharSequenceTokio" -> "M1 A7 E1 - CharSequenceTokio";
            case "2. Figuras" -> "M1 A7 E2 - Figuras";
            case "3. Cartas II" -> "M1 A7 E3 - Cartas II";
            case "1. Stack II" -> "M1 A8 E1 - Stack II";
            case "1. Carro" -> "M2 A1 E1 - Carro";
            case "1. Baralho" -> "M2 A2 E1 - Baralho";
            case "2. Jogo de Xadrez" -> "M2 A2 E2 - Jogo de Xadrez";
            case "1. GenericStack" -> "M2 A4 E1 - GenericStack";
            case "2. Bag genérica" -> "M2 A4 E2 - Bag genérica";
            case "3. Bag com iteradores" -> "M2 A4 E3 - Bag com iteradores";
            case "1. Lambdas" -> "M2 A5 E1 - Lambdas";
            case "2. Funções" -> "M2 A5 E2 - Funções";
            case "1. Animais" -> "M2 A6 E1 - Animais";
            case "1. Cidades" -> "M2 A7 E1 - Cidades";
            case "1. Tick-Tock" -> "M2 A9 E1 - Tick-Tock";
            case "2. Produtor-Consumidor" -> "M2 A9 E2 - Produtor-Consumidor";
            case "1. Aniversário" -> "M2 A11 E1 - Aniversário";
            case "Entrega do Projeto Final" -> "Entrega do Projeto Final";
            case "Defesa do Projeto Final" -> "Defesa do Projeto Final";
            default -> "Could not detect: " + simpleName;
        };

        gradable.setExercise(finalName);
        return gradable;
    }

}
