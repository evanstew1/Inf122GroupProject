import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TimedRaidWindow implements MiniAdventure {
    private User p1;
    private User p2;

    private String[] monsterNames = {"Hadeon", "The Cursed Archon (Boss)"};
    private int[] monsterHealths = {500, 1000}; 
    private int currentMonsterIndex = 0;
    private int timeLimitMinutes = 4;

    @Override
    public String getName() {
        return "Timed Raid Window (Co-op)";
    }

    @Override
    public void initializePlayers(User player1, User player2) {
        this.p1 = player1;
        this.p2 = player2;
    }

    @Override
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean Player1Turn = true;

        Character character1 = selectCharacter(p1, scanner);
        Character character2 = selectCharacter(p2, scanner);

        int p1Health = 200;
        int p2Health = 200;

        Realm raidRealm = p1.getSettings().getCurrentRealm();
        WorldClockTime globalClock = new WorldClockTime(0, 0, 0);

        System.out.println("\n--- RAID START: Clear the dungeon in " + raidRealm.getName() + " in " + timeLimitMinutes + " minutes! ---");
        System.out.println("Good luck " + character1.getName() + " and " + character2.getName() + "!");

        while (currentMonsterIndex < monsterNames.length) {
            if (p1Health <= 0 && p2Health <= 0) {
                System.out.println("\nBoth Characters are dead. You Lose!");
                return;
            }

            if (Player1Turn && p1Health <= 0) {
                System.out.println("\n" + character1.getName() + " is dead. Player 2's turn.");
                Player1Turn = false;
                continue;
            } else if (!Player1Turn && p2Health <= 0) {
                System.out.println("\n" + character2.getName() + " is dead.");
                Player1Turn = true;
                continue;
            }

            globalClock.syncWithRealTime();
            RealmLocalTime currentLocalTime = new RealmLocalTime(0, 0, 0, raidRealm.getLocalTimeRule());
            currentLocalTime.updateTime(globalClock);

            if (currentLocalTime.minutes >= timeLimitMinutes) {
                System.out.println("\nTime's Up! You Lose");
                return;
            }

            String currentTarget = monsterNames[currentMonsterIndex];
            int currentMonsterHealth = monsterHealths[currentMonsterIndex];

            System.out.println("\nTarget: " + currentTarget + " | Health: " + currentMonsterHealth + " | Time Elapsed: " + currentLocalTime.minutes + "/" + timeLimitMinutes);

            Character currentCharacter = Player1Turn ? character1 : character2;
            System.out.print("[" + currentCharacter.getCharacterClass() + "] " + currentCharacter.getName() + ", type 'A' to attack ");

            String action = scanner.nextLine().trim().toUpperCase();

            globalClock.syncWithRealTime();
            currentLocalTime.updateTime(globalClock);
            if (currentLocalTime.minutes >= timeLimitMinutes) {
                System.out.println("Time's Up! You Lose");
                return;
            }

            // If User Types Attack ('A')
            if (action.equals("A")) {
                Random rand = new Random();
                int damage = rand.nextInt(100) + 1;
                monsterHealths[currentMonsterIndex] -= damage;
                System.out.println("Nice! " + currentCharacter.getName() + " did " + damage + " damage to " + currentTarget + ".");

                if (monsterHealths[currentMonsterIndex] <= 0) {
                    System.out.println("----- " + currentTarget + " has been eliminated! -----");
                    currentMonsterIndex++;
                } else {
                    int monsterDamage = rand.nextInt(30) + 21;
                    System.out.println(currentTarget + " hits " + currentCharacter.getName() + " for " + monsterDamage + " damage!");
                    
                    if (Player1Turn) {
                        p1Health -= monsterDamage;
                        System.out.println(currentCharacter.getName() + " HP: " + p1Health + "/200");
                    } else {
                        p2Health -= monsterDamage;
                        System.out.println(currentCharacter.getName() + " HP: " + p2Health + "/200");
                    }
                }

            } else {
                System.out.println("Invalid command! " + currentCharacter.getName() + " missed their chance to attack.");
            }

            Player1Turn = !Player1Turn;
        }

        System.out.println("\n You Won!");

        String bossLoot = "Tibault's Crown";
        character1.addItem(bossLoot);
        character2.addItem(bossLoot);

        System.out.println("The raid boss dropped " + bossLoot + " both characters acquire item!");
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

