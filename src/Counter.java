public class Counter {
    public int countBulls(String guess, String target) {
        int bulls = 0;
        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) == target.charAt(i)) {
                bulls++;
            }
        }
        return bulls;
    }

    public int countCows(String guess, String target) {
        int cows = 0;
        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) != target.charAt(i) && target.indexOf(guess.charAt(i)) != -1) {
                cows++;
            }
        }
        return cows;
    }
}

