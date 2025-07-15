package com.roomelephant.moopper.services.paths;

import com.roomelephant.moopper.model.Gradable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class PathsService {

    private static final Logger log = LoggerFactory.getLogger(PathsService.class);

    public void createPaths(List<Gradable> gradables) {
        final String BASE_PATH = "/Users/jpedroborges/workspace/ideas/review/review/src/main/java/org/example/v2";
        gradables.forEach(gradable -> {
            String ep = getExePath(gradable);


            String tentative = getTentative(gradable, BASE_PATH, ep);

            String gradablePath = "/" + ep + "/" + gradable.name() + "/" + tentative;

            log.debug("gradable path: {}", gradablePath);

            Path path = Paths.get(BASE_PATH + gradablePath);


            try {
                Files.createDirectories(path);
            } catch (IOException ex) {
                System.out.println("Error creating directory: " + ex.getMessage());
            }

        });
    }

    private static String getTentative(Gradable gradable, String basePath, String ep) {
        Path path = Paths.get(basePath + "/" + ep + "/" + gradable.name());

        if (!Files.exists(path)) {
            return "T1";
        }

        try (Stream<Path> stream = Files.list(path)) {
            List<Path> sorted = stream.filter(Files::isDirectory).filter(cp -> !cp.endsWith("TE")).sorted().toList();
            Path last = sorted.getLast();
            int count = Integer.parseInt(last.getName(last.getNameCount() - 1).toString().substring(1));

            Path pathT = Paths.get(basePath + "/" + ep + "/" + gradable.name() + "/" + "T" + count);
            try (Stream<Path> stream2 = Files.list(pathT)) {
                boolean hasFiles = stream2.anyMatch(Files::isRegularFile);
                if (hasFiles) {
                    return "T" + ++count;
                } else {
                    return "T" + count;
                }
            } catch (IOException e) {
                return "T" + ++count;
            }

        } catch (IOException e1) {
            return "TE";
        }
    }

    private static String getExePath(Gradable gradable) {
        String ep;
        if (gradable.exercise().contains("-")) {
            String[] split = gradable.exercise().split("-");
            String[] exercice = split[0].split(" ");
            String m = exercice[0];
            String a = exercice[1];
            String e = exercice[2] + "_" + split[1].replace(" ", "_");

            String aula = m.equals("M1") ? switch (a) {
                case "A2" -> "A2_Tipos_de_dados_e_operadores";
                case "A3" -> "A3_Instrucoes_de_controlo";
                case "A4" -> "A4_Vetores_e_strings";
                case "A5" -> "A5_Introducao_a_programacao_orientada_para_objectos";
                case "A6" -> "A6_Heranca";
                case "A7" -> "A7_Utilizacao_de_interfaces";
                case "A8" -> "A8_Excepcoes";
                default -> null;
            } : switch (a) {
                case "A1" -> "A1_Classes_internas";
                case "A2" -> "A2_Enumeracoes";
                case "A4" -> "A4_Colecoes_e_genericos";
                case "A5" -> "A5_Interfaces_funcionais_e_expressoes_lambda";
                case "A6" -> "A6_Expressoes_lambda_pre_definidas";
                case "A7" -> "A7_Streams_operacoes_lambra_e_referias_a_metodos";
                case "A9" -> "A9_Concorrencia";
                case "A11" -> "A11_API_para_datas";
                default -> null;
            };

            if (aula == null) System.out.println();
            ep = m + "/" + aula + "/" + e;
        } else {
            ep = gradable.exercise().replace(" ", "_");
        }
        return ep;
    }
}
