package setup;

import java.util.Random;

public class RandomHello {
    /**
     * Prints a random greeting to the console.
     *
     * @param args command-line arguments (ignored)
     */
    String[] greetings = new String[]{"Hello World", "Hola Mundo", "Bonjour Monde", "Hallo Welt", "Cia Mondo"};

    public static void main(String[] args) {
        RandomHello randomHello = new RandomHello();
        System.out.println(randomHello.getGreeting());
    }

    /**
     * @return a greeting, randomly chosen from five possibilities
     */
    public String getGreeting() {
        // YOUR CODE GOES HERE
        Random randomGenerator = new Random();
        int i = randomGenerator.nextInt(greetings.length);
        return greetings[i];
    }
}

