import java.util.Random;

public class Generator {
    public  String generateTargetNumber() {
        Random random = new Random();
        int[] digits = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(10 - i);
            sb.append(digits[index]);
            digits[index] = digits[9 - i];
        }

        return sb.toString();
    }

}
