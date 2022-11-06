import java.util.Scanner;

public class Generator {
    Alphabet alphabet;
    public static Scanner keyboard;

    public Generator(Scanner scanner) {
        keyboard = scanner;
    }

    public Generator(boolean IncludeUpper, boolean IncludeLower, boolean IncludeNum, boolean IncludeSym) {
        alphabet = new Alphabet(IncludeUpper, IncludeLower, IncludeNum, IncludeSym);
    }

    public void mainLoop() {
        System.out.println("Welcome to password services");
        printMenu();

        String userOption = "-1";
        userOption = keyboard.next();

        switch (userOption) {
            case "1" -> {
                requestPassword();
                printMenu();
            }
            case "2" -> {
                checkPassword();
                printMenu();
            }
            case "3" -> {
                printUsefulInfo();
                printMenu();
            }
            case "4" -> printQuitMessage();
            default -> {
                System.out.println();
                System.out.println("Please select available commands");
                printMenu();
            }
        }
    }

    private Password GeneratePassword(int length) {
        final StringBuilder pass = new StringBuilder("");

        final int alphabetLength = alphabet.getAlphabet().length();

        int max = alphabetLength - 1;
        int min = 0;
        int range = max - min + 1;

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * range) + min;
            pass.append(alphabet.getAlphabet().charAt(index));
        }
        return new Password(pass.toString());
    }

    private void printUsefulInfo() {
        System.out.println();
        System.out.println("Use a minimum password length of 8 or more characters if permitted");
        System.out.println("Include lowercase and uppercase alphabetic characters, numbers and symbols if permitted");
        System.out.println("Generate passwords randomly where feasible");
        System.out.println("Avoid using the same password twice (e.g., across multiple user accounts and/or" +
                " software systems)");
        System.out.println("Avoid character repetition, keyboard patterns, dictionary words, letter or number sequences," +
                "\nusernames, relative or pet names, romantic links (current or past) " +
                "and biographical information (e.g., ID numbers, ancestors' names or dates).");
        System.out.println("Avoid using information that the user's colleagues and/or " +
                "acquaintances might know to be associated with the user");
        System.out.println("Do not use passwords which consist wholly of any simple combination of the " +
                "aforementioned weak components");
    }

    public void requestPassword() {
        boolean IncludeUpper = false;
        boolean IncludeLower = false;
        boolean IncludeNum = false;
        boolean IncludeSym = false;

        boolean correctParams = false;
        System.out.println();
        System.out.println("Welcome, to the password generator answer the following questions Yes or No \n");

        do {
            System.out.println("Do you want lowercase letters \"abcd...\" to be used?");
            String input = keyboard.nextLine();

            if (isInclude(input)) IncludeLower = true;

            System.out.println("Do you want uppercase letters \"ABCD...\" to be used?");

            if (isInclude(input)) IncludeUpper = true;

            System.out.println("Do you want numbers \"1234...\" to be used?");
            input = keyboard.nextLine();

            if (isInclude(input)) IncludeNum = true;

            System.out.println("Do you want symbols \"!@#$\" to be used?");
            input = keyboard.nextLine();

            if (isInclude(input)) IncludeSym = true;

            // No pool selected
            if (!IncludeUpper && !IncludeLower && !IncludeNum && !IncludeSym) {
                System.out.println("You have selected no characters to generate your " +
                        "password at least one of your answers should be Yes");
                correctParams = true;
            }

            System.out.println("Great! Now enter the length of the password");
            int length = keyboard.nextInt();

            final Generator generator = new Generator(IncludeUpper, IncludeLower, IncludeNum, IncludeSym);
            final Password password = generator.GeneratePassword(length);

            System.err.println("Your generated password -> " + password);

        } while (correctParams);
    }

    private boolean isInclude(String Input) {
        if (Input.equalsIgnoreCase("yes")) {
            return true;
        } else {
            if (!Input.equalsIgnoreCase("no")) {
                PasswordRequestError();
            }
        }
        return false;
    }

    private void PasswordRequestError() {
        System.out.println("Incorrect password. Let's do it again \n");
    }

    private void checkPassword() {
        String input;
        final Scanner in = new Scanner(System.in);

        System.out.print("\nEnter your password:");
        input = in.nextLine();

        final Password p = new Password(input);

        System.out.println(p.calculateScore());

        in.close();
    }

    private void printMenu() {
        System.out.println();
        System.out.println("Enter 1 - Password Generator");
        System.out.println("Enter 2 - Password strength check");
        System.out.println("Enter 3 - Useful information");
        System.out.println("Enter 4 - Quit program");
        System.out.println("Choose: ");


    }

    private void printQuitMessage() {
        System.out.println("Goodbye!");
    }
}
