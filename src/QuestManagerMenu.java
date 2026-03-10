import java.util.List;
import java.util.Scanner;

public class QuestManagerMenu extends MenuTemplate {
    private Campaign campaign;

    public QuestManagerMenu(Scanner scanner, User currentUser, Campaign campaign){
        super(scanner, currentUser);
        this.campaign = campaign;
    }

    @Override
    protected void printMenuHeader(){
        System.out.println("\n\n\n\n\n\n\n\n --- QUEST MANAGER: " + campaign.getName() + " ---");

    }

    @Override
    protected void printList(){
        List<QuestEvent> events = campaign.getQuestEvents();
            for (int i = 0; i < events.size(); i++) {
                QuestEvent q = events.get(i);
                String timeStr = currentUser.getSettings().getFormattedTime(
                    (WorldClockTime)q.getEventTime(), q.getRealm());
                System.out.println((i + 1) + ". [" + timeStr + "] " + q.getName());
            }
    }

    @Override
    protected void printMenuOptions(){
        System.out.println("\nOpen[O] Add[A] Delete[D] Back[B]");

    }

    @Override
    protected void handleInput(String input){
        WorldClockTime globalClock = WorldClockTime.getInstance();
        List<QuestEvent> events = campaign.getQuestEvents();

         if (input.equals("A")) {
                System.out.print("Quest Title: ");
                String title = scanner.nextLine();
                Realm defaultRealm = new Realm("Default Realm", "Default", 1); 
                globalClock.syncWithRealTime();
                campaign.addQuestEvent(new QuestEvent(title, globalClock, defaultRealm, campaign));
            } else if (input.equals("O")) {
                System.out.print("Quest # to open: ");
                try {
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx >= 0 && idx < events.size()) {
                        new QuestDetailMenu(scanner, currentUser, events.get(idx)).startMenu();
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input.");
                    waitSeconds(1);
                }
            } else if (input.equals("D")) {
                System.out.print("Quest # to delete: ");
                try {
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx >= 0 && idx < events.size()) campaign.removeQuestEvent(events.get(idx));
                } catch (Exception e) {
                    System.out.println("Invalid input.");
                    waitSeconds(1);
                }
            }
    }
}