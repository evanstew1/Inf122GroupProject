import java.util.Scanner;

public abstract class MenuTemplate {
    protected Scanner scanner;
    protected User currentUser;

    public MenuTemplate(Scanner scanner, User currentUser){
        this.scanner = scanner;
        this.currentUser = currentUser;
    }

    public final void startMenu(){
        while(true){
            displayBanner();
            printMenuHeader();
            printList();
            printMenuOptions();

            String input = scanner.nextLine().toUpperCase();
            if(input.equals("B")) return;

            handleInput(input);
        }
    }

    private void displayBanner() {
        WorldClockTime globalClock = WorldClockTime.getInstance();
        globalClock.syncWithRealTime();
        System.out.println("\n\n\n\n\n\n\n----- GUILDQUEST -----");
        String formattedTime = globalClock.getFormattedDisplay(currentUser.getSettings(), currentUser.getSettings().getCurrentRealm());
        System.out.println(formattedTime);
        System.out.println("User: " + currentUser.getUserID());
        System.out.println("\n---------------------");
    }

    protected void waitSeconds(int s) {
        try {
            Thread.sleep(s * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected abstract void printMenuHeader();
    protected abstract void printList();
    protected abstract void printMenuOptions();
    protected abstract void handleInput(String input);
}