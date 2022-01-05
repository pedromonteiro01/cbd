package cbd.ex5;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        RedisSet set = new RedisSet();
        RedisList list = new RedisList();
        RedisHash hash = new RedisHash();

        String USERS = "usersSet"; // Key set for users' name
        String USERS_HASH = "usersHash"; // Key hash for users' name and nmec
        String FOLLOWING = "following"; // key set for user's followers
        String MESSAGES = "messages"; // key list for messages

        Scanner sc = new Scanner(System.in);

        while (true) {
                System.out.println("\n0 - Quit");
                System.out.println("1 - Create User");
                System.out.println("2 - List Users");
                System.out.println("3 - Subscribe User");
                System.out.println("4 - Send Message");
                System.out.println("5 - Check User Messages");

                System.out.print(">>> ");
                int input = sc.nextInt();

                if (input < 0 || input > 5) { // input must be in [0,5]
                    System.err.println("Invalid option.");
                    System.exit(-1);
                }

            switch (input) {
                case 0: // exiting app
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;

                case 1: // create user
                    Scanner createSc = new Scanner(System.in);
                    System.out.print("Username: ");
                    String username = createSc.nextLine();
                    //Set<String> allUsers = set.getUser(username);
                    //if (allUsers.contains())

                    if (set.checkUser(USERS, username)){
                        System.err.println("Username already registered!");
                    } else{
                        Map<String, String> map = hash.getUser();
                        System.out.print("Name: ");
                        String name = createSc.nextLine();
                        map.put("name", name);
                        System.out.print("Nmec: ");
                        String nmec = createSc.nextLine();
                        map.put("nmec", nmec);
                        //map.forEach((key, value) -> System.out.println(key + "->" + value));
                        //users.forEach((key, value) -> System.out.println(key + "users->" + value));

                        set.saveUser(USERS, username);
                        hash.saveUser(USERS_HASH+username, map);
                    }
                    break;

                case 2: // list users
                    Scanner fscan = new Scanner(System.in);
                    Set<String> usersSet = set.getUsers(USERS);
                    List<String> usersList = new ArrayList<String>();
                    usersList.addAll(usersSet);

                    if (usersSet.size() == 0)
                        System.err.println("No users available!");
                    else{
                        System.out.println("Printing all users...");
                        for(String s : usersList)
                            System.out.println(usersList.indexOf(s) + 1 + "-" + s);
                            System.out.print("User to see info (number): ");
                            Integer number = fscan.nextInt();

                            if (number == 0) {
                                return;
                            } else {
                                String user = usersList.get(number-1);
                                Map<String, String> info = hash.getUserMap(USERS_HASH+user);
                                //USERS_HASH+user -> usersHashpedro
                                System.out.println("Username: "+user);
                                System.out.println("Name: "+info.get("name"));
                                System.out.println("Nmec: "+info.get("nmec"));
                            }

                        System.out.print("--------\n");
                    }
                    break;

                case 3: // subscribe user
                    Scanner userInput = new Scanner(System.in);
                    System.out.println("Your username: ");
                    String currentUser = userInput.nextLine();

                    if (!set.checkUser(USERS, currentUser)){
                        System.err.println("Username not in database!");
                    } else{
                        Set<String> allUsers = set.setDiff(USERS, FOLLOWING+currentUser);
                        List<String> users = new ArrayList<String>();
                        users.addAll(allUsers);
                        users.remove(currentUser); // remove own user from list

                        if (users.size() == 0) {
                            System.err.println("No users available!");
                        } else{
                            for (String s : users)
                                System.out.println((users.indexOf(s)+1)+" - "+s);

                            System.out.print("User to follow (number): ");
                            int follow = userInput.nextInt();
                            set.saveUser(FOLLOWING+currentUser, users.get(follow - 1)); //users.get(follow - 1) -> user
                            System.out.println("You are now following "+users.get(follow - 1));
                        }
                    }

                    break;

                case 4: // send message
                    Scanner mScan = new Scanner(System.in);
                    System.out.print("Your username: ");
                    String name = mScan.nextLine();
                    System.out.print("Message: ");
                    String message = mScan.nextLine();

                    list.saveMessage(MESSAGES+name, message);
                    System.out.println("Your message has been sent!");

                    break;

                case 5: // check user messages
                    Scanner sc2 = new Scanner(System.in);
                    System.out.print("Your username: ");
                    String user = sc2.nextLine();
                    List<String> messagesList = list.getMessage(MESSAGES + user);

                    if (messagesList.size() == 0) {
                        System.err.println("No messages available!");
                    } else {
                        for (String m : messagesList) {
                            System.out.println("\n> " + m);
                    }

                    break;
                }
            }
        }
    }
}
