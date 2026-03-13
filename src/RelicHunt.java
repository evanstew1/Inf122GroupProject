import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RelicHunt implements MiniAdventure {
    private User p1;
    private User p2;

    private static final int GRID_SIZE = 100;
    private static final int ARTIFACT_COUNT = 10;
    private static final int ARTIFACTS_TO_WIN = 5;

    private boolean[][] hasArtifact;
    private boolean[][] accessed;
    private int p1Artifacts;
    private int p2Artifacts;

    @Override
    public String getName() {
        return "Relic Hunt (1v1)";
    }

    @Override
    public void initializePlayers(User player1, User player2) {
        this.p1 = player1;
        this.p2 = player2;
    }

    @Override
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean player1Turn = true;

        Character character1 = selectCharacter(p1, scanner);
        Character character2 = selectCharacter(p2, scanner);

        hasArtifact = new boolean[GRID_SIZE][GRID_SIZE];
        accessed = new boolean[GRID_SIZE][GRID_SIZE];
        p1Artifacts = 0;
        p2Artifacts = 0;

        scatterArtifacts();

        int totalSquares = GRID_SIZE * GRID_SIZE;
        int unaccessedCount = totalSquares;

        System.out.println("\n--- Relic Hunt: First to " + ARTIFACTS_TO_WIN + " artifacts wins! ---");
        System.out.println("Grid: " + GRID_SIZE + "x" + GRID_SIZE + " | " + ARTIFACT_COUNT + " artifacts hidden");
        System.out.println("Good luck " + character1.getName() + " and " + character2.getName() + "!");

        while (p1Artifacts < ARTIFACTS_TO_WIN && p2Artifacts < ARTIFACTS_TO_WIN) {
            Character currentCharacter = player1Turn ? character1 : character2;

            System.out.println("\n----- " + currentCharacter.getName() + "'s Turn -----");
            System.out.println("Score: " + character1.getName() + " " + p1Artifacts + " - " + p2Artifacts + " " + character2.getName());
            System.out.println("Squares not yet accessed: " + unaccessedCount);
            System.out.print("Enter row (1-" + GRID_SIZE + ") and column (1-" + GRID_SIZE + "), e.g. 50 50: ");

            try {
                String input = scanner.nextLine().trim();
                String[] parts = input.split("\\s+");
                if (parts.length < 2) {
                    System.out.println("Invalid input. Enter row and column separated by space.");
                    continue;
                }

                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);

                if (row < 1 || row > GRID_SIZE || col < 1 || col > GRID_SIZE) {
                    System.out.println("Coordinates must be between 1 and " + GRID_SIZE + ".");
                    continue;
                }

                int rowIdx = row - 1;
                int colIdx = col - 1;

                if (accessed[rowIdx][colIdx]) {
                    System.out.println("That square has already been explored!");
                    continue;
                }

                accessed[rowIdx][colIdx] = true;
                unaccessedCount--;

                if (hasArtifact[rowIdx][colIdx]) {
                    if (player1Turn) {
                        p1Artifacts++;
                        System.out.println("Artifact found! " + character1.getName() + " has " + p1Artifacts + "/" + ARTIFACTS_TO_WIN);
                    } else {
                        p2Artifacts++;
                        System.out.println("Artifact found! " + character2.getName() + " has " + p2Artifacts + "/" + ARTIFACTS_TO_WIN);
                    }
                } else {
                    System.out.println("Nothing here. Keep searching!");
                }

                player1Turn = !player1Turn;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter two numbers for row and column.");
            }
        }

        Character winner = p1Artifacts >= ARTIFACTS_TO_WIN ? character1 : character2;
        User winnerUser = p1Artifacts >= ARTIFACTS_TO_WIN ? p1 : p2;
        System.out.println("\n----- " + winner.getName() + " wins Relic Hunt! -----");
        winnerUser.recordMatch("Relic Hunt: Win");
        User loserUser = p1Artifacts >= ARTIFACTS_TO_WIN ? p2 : p1;
        loserUser.recordMatch("Relic Hunt: Loss");
    }

    private void scatterArtifacts() {
        Random rand = new Random();
        int placed = 0;

        while (placed < ARTIFACT_COUNT) {
            int row = rand.nextInt(GRID_SIZE);
            int col = rand.nextInt(GRID_SIZE);

            if (!hasArtifact[row][col]) {
                hasArtifact[row][col] = true;
                placed++;
            }
        }
    }

    private Character selectCharacter(User u, Scanner scanner) {
        List<Character> chars = u.getCharacters();

        while (true) {
            System.out.println("\n----- User " + u.getUserID() + " Character Selection -----");

            if (chars.isEmpty()) {
                System.out.println("Create a New Character");
            } else {
                for (int i = 0; i < chars.size(); i++) {
                    System.out.println((i + 1) + ". " + chars.get(i).getName() + " (Class: " + chars.get(i).getCharacterClass() + ")");
                }
            }

            int createOption = chars.size() + 1;
            System.out.println(createOption + ". Create a New Character");
            System.out.print("Select an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                if (choice > 0 && choice <= chars.size()) {
                    return chars.get(choice - 1);
                } else if (choice == createOption) {
                    System.out.print("Enter New Character Name: ");
                    String newCharacterName = scanner.nextLine().trim();
                    System.out.print("Enter Character Class (e.g., Warrior, Mage, Rogue): ");
                    String newClass = scanner.nextLine().trim();

                    Character newCharacter = new Character(newCharacterName, newClass, 1);
                    u.addCharacter(newCharacter);
                    System.out.println("Your Character " + newCharacterName + " was created!");
                    return newCharacter;
                } else {
                    System.out.println("Invalid Input");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input");
            }
        }
    }
}
