/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mainpoe.java;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class MainPOEJava {

    public static void main(String[] args) {
        System.out.println("Hello World!");
{
    static ArrayList<ChatMessage> sentList = new ArrayList<>();
    static ArrayList<ChatMessage> disregardList = new ArrayList<>();
    static ArrayList<ChatMessage> storedList = new ArrayList<>();
    static ArrayList<String> hashList = new ArrayList<>();
    static ArrayList<String> idList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        LoginManager lm = new LoginManager();

        // --- PART 1: Registration ---
        System.out.print("Enter username (must contain _ and <=5 chars): ");
        String u = input.nextLine();
        System.out.print("Enter password (8+ chars, caps, number, special): ");
        String p = input.nextLine();
        System.out.print("Enter cell (+27): ");
        String c = input.nextLine();
        System.out.println(lm.registerUser(u,p,c));

        System.out.print("Login username: ");
        String lu = input.nextLine();
        System.out.print("Login password: ");
        String lp = input.nextLine();
        if (!lm.loginUser(lu, lp)) {
            System.out.println("Login failed.");
            return;
        }
        System.out.println("Welcome to QuickChat.");

        // --- Populate with Test Data for POE (Requirement 3) ---
        populateTestData();

        // --- MAIN MENU (4 options) ---
        boolean running = true;
        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages Menu");
            System.out.print("Choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "1" -> sendNewMessages(input);
                case "2" -> {
                    System.out.println("Sent Messages: " + sentList.size());
                    for (ChatMessage m : sentList) System.out.println(m.getHash() + " -> " + m.getText());
                }
                case "3" -> running = false;
                case "4" -> storedMessagesMenu(input);
            }
        }
        System.out.println("Goodbye!");
    }

    static void populateTestData() {
        ChatMessage m1 = new ChatMessage("+27834557896", "Did you get the cake?", "Sent", 0);
        ChatMessage m2 = new ChatMessage("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored", 1);
        ChatMessage m3 = new ChatMessage("+27834484567", "Yohoooo, I am at your gate.", "Disregard", 2);
        ChatMessage m4 = new ChatMessage("0838884567", "It is dinner time !", "Sent", 3);
        m4.setIdForTest("0838884567"); // for unit test search by ID
        ChatMessage m5 = new ChatMessage("+27838884567", "Ok, I am leaving without you.", "Stored", 4);

        addToArrays(m1); addToArrays(m2); addToArrays(m3); addToArrays(m4); addToArrays(m5);
    }

    static void addToArrays(ChatMessage m) {
        hashList.add(m.getHash());
        idList.add((String) m.getId());
        if (m.getFlag().equals("Sent")) sentList.add(m);
        else if (m.getFlag().equals("Stored")) storedList.add(m);
        else disregardList.add(m);
    }

    static void sendNewMessages(Scanner input) {
        System.out.print("How many messages? ");
        int n = Integer.parseInt(input.nextLine());
        for (int i=0; i<n; i++) {
            System.out.print("Recipient (+): ");
            String rec = input.nextLine();
            System.out.print("Message: ");
            String txt = input.nextLine();
            if (txt.length() > 250) {
                System.out.println("Message exceeds 250 characters by " + (txt.length()-250));
                i--; continue;
            }
            ChatMessage cm = new ChatMessage(rec, txt, "Sent", sentList.size());
            System.out.println(cm.checkRecipientCell());
            System.out.println("1) Send 2) Disregard 3) Store");
            String opt = input.nextLine();
            switch (opt) {
                case "1" -> {
                    cm = new ChatMessage(rec, txt, "Sent", sentList.size());
                    addToArrays(cm);
                    System.out.println("Message successfully sent.");
                }
                case "2" -> {
                    cm = new ChatMessage(rec, txt, "Disregard", 0);
                    addToArrays(cm);
                    System.out.println("Press 0 to delete the message.");
                }
                default -> {
                    cm = new ChatMessage(rec, txt, "Stored", 0);
                    addToArrays(cm);
                    System.out.println("Message successfully stored.");
                }
            }
        }
    }

    // Requirement 2a-f
    static void storedMessagesMenu(Scanner input) {
        System.out.println("\n--- STORED MESSAGES ---");
        System.out.println("a) Display sender and recipient");
        System.out.println("b) Display longest message");
        System.out.println("c) Search by Message ID");
        System.out.println("d) Search by Recipient");
        System.out.println("e) Delete by Message Hash");
        System.out.println("f) Display Report");
        System.out.print("Choose a-f: ");
        String c = input.nextLine();

        if (c.equalsIgnoreCase("a")) {
            for (ChatMessage m : storedList) System.out.println("Recipient: " + m.getRecipient() + " | Message: " + m.getText());
        }
        else if (c.equalsIgnoreCase("b")) {
            ChatMessage longest = storedList.get(0);
            for (var m : storedList) if (m.getText().length() > longest.getText().length()) {
                longest = m;
            } else {
            }
            System.out.println("Longest: " + longest.getText()); // Should be Message 2
        }
        else if (c.equalsIgnoreCase("c")) {
            System.out.print("Enter Message ID: ");
            String id = input.nextLine();
            for (ChatMessage m : storedList) if (m.getId().equals(id)) System.out.println("Recipient: " + m.getRecipient() + " | Message: " + m.getText());
            for (ChatMessage m : sentList) if (m.getId().equals(id)) System.out.println("Recipient: " + m.getRecipient() + " | Message: " + m.getText());
        }
        else if (c.equalsIgnoreCase("d")) {
            System.out.print("Enter Recipient: ");
            String rec = input.nextLine();
            for (ChatMessage m : storedList) if (m.getRecipient().equals(rec)) System.out.println(m.getText());
            for (ChatMessage m : sentList) if (m.getRecipient().equals(rec)) System.out.println(m.getText());
        }
        else if (c.equalsIgnoreCase("e")) {
            System.out.print("Enter Message Hash to delete: ");
            String h = input.nextLine();
            for (int i=0; i<storedList.size(); i++) {
                if (storedList.get(i).getHash().equals(h)) {
                    System.out.println("Message: \"" + storedList.get(i).getText() + "\" successfully deleted.");
                    storedList.remove(i);
                    break;
                }
            }
        }
        else if (c.equalsIgnoreCase("f")) {
            System.out.println("--- FULL REPORT ---");
            for (ChatMessage m : sentList) System.out.println("Hash: " + m.getHash() + " | Recipient: " + m.getRecipient() + " | Message: " + m.getText());
            for (ChatMessage m : storedList) System.out.println("Hash: " + m.getHash() + " | Recipient: " + m.getRecipient() + " | Message: " + m.getText());
        }
    


