import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Moo {
    public static void main(String[] args) {
        Counter counter = new Counter();
        Generator generator = new Generator();
        String targetNumber = generator.generateTargetNumber();
        int attempts = 0;
        System.out.println(targetNumber);
        System.out.println("Игра \"Быки-Коровы\"");
        System.out.println("Правила: программа задумывает строку из четырех разных цифр.");
        System.out.println("Ваша задача - угадать это число путем ввода строки из четырех цифр.");
        System.out.println("Если цифра есть в угадываемой строке и стоит на своем месте, то это \"бык\",");
        System.out.println("если цифра есть в угадываемой строке, но не на своем месте, то это \"корова\".");
        System.out.println("Удачи!");

        Scanner scanner = new Scanner(System.in);
        BufferedWriter writer = null;
        Integer gameNumber = 0;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("Game.txt"))) {
            Optional<Integer> gameNumberOptional = reader.lines()
                    .filter(line -> !Character.isWhitespace(line.charAt(0)))
                    .map(line -> line.replaceAll("\\D+", ""))
                    .map(Integer::parseInt)
                    .reduce(Integer::max);
            gameNumber = gameNumberOptional.orElse(0);


        } catch (IOException e) {
            gameNumber = 0;
        }
        try {
            // writer.write("Game №1:\n" ++gameNumber);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            String timestamp = dateFormat.format(new Date());
            writer = new BufferedWriter(new FileWriter("Game.txt", true));
            writer.write(String.format("Game №%d:\n", ++gameNumber));
            writer.write(" Date: " + timestamp + "\n");
            writer.write(" Загаданная строка: " + targetNumber + "\n");
            writer.write(" Результаты игры:\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            System.out.print("Введите число: ");
            String guess = scanner.nextLine();

            if (guess.length() != 4) {
                System.out.println("Введите строку из четырех цифр.");
                continue;
            }

            attempts++;
            int bulls = counter.countBulls(guess, targetNumber);
            int cows = counter.countCows(guess, targetNumber);

            try {
                writer.write(" Запрос: " + guess + " Ответ: " + cows + " " + (cows == 1 ? "корова" : "коровы") + " " +
                        bulls + " " + (bulls == 1 ? "бык" : "быка") + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.printf("%d бык%s, %d коров%s\n",
                    bulls, (bulls == 1 ? "" : "а"),
                    cows, (cows == 1 ? "а" : "ы"));

            if (bulls == 4) {
                System.out.println("Поздравляю! Вы угадали число!");
                break;
            }
        }

        System.out.printf("Вы угадали число за %d попыт%s.\n",
                attempts, (attempts == 1 ? "ку" : "ки"));

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


