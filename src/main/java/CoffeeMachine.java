import java.util.Scanner;

public class CoffeeMachine {
    final static Scanner SCANNER = new Scanner(System.in);

    final static int WATER_ESPRESSO = 250;
    final static int BEANS_ESPRESSO = 16;
    final static int COST_ESPRESSO = 4;

    final static int WATER_LATTE = 350;
    final static int MILK_LATTE = 75;
    final static int BEANS_LATTE = 20;
    final static int COST_LATTE = 7;

    final static int WATER_CAPPUCCINO = 200;
    final static int MILK_CAPPUCCINO = 100;
    final static int BEANS_CAPPUCCINO = 12;
    final static int COST_CAPPUCCINO = 6;

    static int waterAvailable = 400;
    static int milkAvailable = 540;
    static int beansAvailable = 120;
    static int cupsAvailable = 9;
    static int moneyAvailable = 550;

    public static void main(String[] args) {
        String actionOption;

        do {
            actionOption = selectAction();

            switch (actionOption) {
                case "buy":
                    String coffeeOption = selectCoffee();

                    if (coffeeOption.equals("back")) break;
                    buyCoffee(Integer.parseInt(coffeeOption));
                    break;

                case "fill":
                    fillMachine();
                    break;

                case "take":
                    takeMoney();
                    break;

                case "remaining":
                    printMachineState();
                    break;

                case "exit":
                    break;

                default:
                    System.out.println("Invalid option!");
            }
        } while (!actionOption.equals("exit"));
    }

    public static String selectAction() {
        System.out.println("Write action (buy, fill, take, remaining, exit): ");
        return SCANNER.next();
    }

    public static String selectCoffee() {
        System.out.print("What do you want to buy? ");
        System.out.println("1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
        return SCANNER.next();
    }

    public static boolean hasWater(int coffee) {
        return switch (coffee) {
            case 1 -> waterAvailable >= WATER_ESPRESSO;
            case 2 -> waterAvailable >= WATER_LATTE;
            case 3 -> waterAvailable >= WATER_CAPPUCCINO;
            default -> false;
        };
    }

    public static boolean hasMilk(int coffee) {
        return switch (coffee) {
            case 1 -> true;
            case 2 -> milkAvailable >= MILK_LATTE;
            case 3 -> milkAvailable >= MILK_CAPPUCCINO;
            default -> false;
        };
    }

    public static boolean hasBeans(int coffee) {
        return switch (coffee) {
            case 1 -> beansAvailable >= BEANS_ESPRESSO;
            case 2 -> beansAvailable >= BEANS_LATTE;
            case 3 -> beansAvailable >= BEANS_CAPPUCCINO;
            default -> false;
        };
    }

    public static boolean hasResources(int coffee) {
        return hasWater(coffee) && hasMilk(coffee) && hasBeans(coffee);
    }

    public static void printSuccessMessage() {
        System.out.println("I have enough resources, making you a coffee!");
    }

    public static void updateResources(int coffee) {
        switch (coffee) {
            case 1:
                waterAvailable -= WATER_ESPRESSO;
                beansAvailable -= BEANS_ESPRESSO;
                moneyAvailable += COST_ESPRESSO;
                break;

            case 2:
                waterAvailable -= WATER_LATTE;
                milkAvailable -= MILK_LATTE;
                beansAvailable -= BEANS_LATTE;
                moneyAvailable += COST_LATTE;
                break;

            case 3:
                waterAvailable -= WATER_CAPPUCCINO;
                milkAvailable -= MILK_CAPPUCCINO;
                beansAvailable -= BEANS_CAPPUCCINO;
                moneyAvailable += COST_CAPPUCCINO;
                break;

            default:
                System.out.println("Invalid option!");
        }
        cupsAvailable--;
    }

    public static void printFailMessage(String ingredient) {
        System.out.printf("Sorry, not enough %s!%n", ingredient);
    }

    public static void buyCoffee(int coffee) {
        if (hasResources(coffee)) {
            printSuccessMessage();
            updateResources(coffee);
            return;
        }

        if (!hasWater(coffee))
            printFailMessage("water");

        if (!hasMilk(coffee))
            printFailMessage("milk");

        if (!hasBeans(coffee))
            printFailMessage("beans");
    }

    public static void fillIngredient(String ingredient) {
        switch (ingredient) {
            case "water":
                System.out.println("Write how many ml of water you want to add: ");
                waterAvailable += SCANNER.nextInt();
                break;

            case "milk":
                System.out.println("Write how many ml of milk you want to add: ");
                milkAvailable += SCANNER.nextInt();
                break;

            case "beans":
                System.out.println("Write how many grams of coffee beans you want to add: ");
                beansAvailable += SCANNER.nextInt();
                break;

            case "cups":
                System.out.println("Write how many disposable cups you want to add: ");
                cupsAvailable += SCANNER.nextInt();
                break;

            default:
                System.out.println("Invalid option!");
        }
    }

    public static void fillMachine() {
        String[] ingredients = {"water", "milk", "beans", "cups"};

        for (String ingredient : ingredients)
            fillIngredient(ingredient);
    }

    public static void takeMoney() {
        System.out.printf("I gave you $%d %n", moneyAvailable);
        moneyAvailable = 0;
    }

    public static void printMachineState() {
        System.out.println("The coffee machine has: ");
        System.out.printf("%d ml of water %n", waterAvailable);
        System.out.printf("%d ml of milk %n", milkAvailable);
        System.out.printf("%d g of coffee beans %n", beansAvailable);
        System.out.printf("%d disposable cups %n", cupsAvailable);
        System.out.printf("$%d of money %n", moneyAvailable);
    }
}