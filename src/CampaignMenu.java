import java.util.List;
import java.util.Scanner;

public class CampaignMenu extends MenuTemplate {
    public CampaignMenu (Scanner scanner, User currentUser){
        super(scanner, currentUser);
    }

    @Override
    protected void printMenuHeader(){
        System.out.println("\n ----- GUILDQUEST: Campaign Menu -----\n");
    }

    @Override
    protected void printList(){
        List<Campaign> camps = currentUser.getCampaigns();
        for(int i = 0; i < camps.size(); i++){
            System.out.println((i+1) + ". " + camps.get(i).getName());
        }
    }

    @Override
    protected void printMenuOptions(){
        System.out.println("Open [O] Add[A]  Delete[D] Archive[E] Rename[R] Visibility[V] Back [B]");
    }

    @Override
    protected void handleInput(String input){
        switch(input){
            case "A" -> addCampaign();
            case "D" -> deleteCampaign();
            case "O" -> openCampaign();
            case "E" -> archiveCampaign();
            case "R" -> renameCampaign();
            case "V" -> toggleVisibility();
            default -> {
                System.out.println("Please select a correct option");
                waitSeconds(2);
            }
        }
    }
    
    private void addCampaign(){
        System.out.print("Name: ");
        currentUser.addCampaign(new Campaign(scanner.nextLine()));
    }
    private void deleteCampaign(){
        System.out.print("Campaign to Delete (Camp #): ");
            try {
                int index = Integer.parseInt(scanner.nextLine()) - 1;
                List<Campaign> camps = currentUser.getCampaigns();
                if (index >= 0 && index < camps.size()) {
                    currentUser.removeCampaign(camps.get(index));
                }
            } catch (Exception e) { System.out.println("Invalid number"); }
    }
    private void openCampaign(){
         System.out.print("Campaign to Open (Camp #): ");
            try {
                int index = Integer.parseInt(scanner.nextLine()) - 1;
                List<Campaign> camps = currentUser.getCampaigns();
                if (index >= 0 && index < camps.size()) {
                    new QuestManagerMenu(scanner, currentUser, camps.get(index)).startMenu();
                }
            } catch (Exception e) { System.out.println("Invalid number.");}
    }
    private void archiveCampaign(){
        System.out.print("Campaign to Archive (Camp #): ");
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            List<Campaign> camps = currentUser.getCampaigns();
            if (index >= 0 && index < camps.size()) {
                Campaign arch = camps.get(index);
                currentUser.archiveCampaign(arch);
                System.out.println("Campaign Archived.");
                waitSeconds(2);
            } else {
                System.out.println("Invalid index");
                waitSeconds(2);
            }
    }
    private void renameCampaign(){
        System.out.print("Campaign to Rename (Camp #): ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        List<Campaign> camps = currentUser.getCampaigns();
        if (index >= 0 && index < camps.size()) {
            System.out.print("Enter New Name: ");
            camps.get(index).renameCampaign(scanner.nextLine());
        } else {
            System.out.println("Invalid index");
            waitSeconds(2);
        }
    }
    private void toggleVisibility(){
        System.out.print("Toggle Visibility for (Camp #): ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        List<Campaign> camps = currentUser.getCampaigns();
        if (index >= 0 && index < camps.size()) {
            Campaign c = camps.get(index);
            if (c.getVisibility() == Campaign.Visibility.Public) {
                c.changeCampaignVisibility(Campaign.Visibility.Private);
                System.out.println("Visibility is now Private");
                waitSeconds(2);
            } else {
                c.changeCampaignVisibility(Campaign.Visibility.Public);
                System.out.println("Visibility is now Public");
                   waitSeconds(2);
            }
        }
    }

}