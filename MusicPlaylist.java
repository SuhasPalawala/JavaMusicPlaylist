// Name: Suhas Palawala
// Date: 10/18/2023

import java.util.*;
import java.io.*;
/* 
This class is meant to act as a music playlist, a program that allows you to 
add songs you want to play to a list, play songs you selected, and view your song history. Additionally,
you are able to manage your song history and delete songs if you so choose.
*/
public class MusicPlaylist {
    // TODO: Your Code Here
    public static void main(String[] args) {
        Scanner listenerInput = new Scanner(System.in);
        String userAction = "";
        Queue<String> requestedTracks = new LinkedList<>();
        Stack<String> listeningHistory = new Stack<>();
        
        System.out.println("Welcome to the CSE 122 Music Playlist!");
        while(!userAction.equalsIgnoreCase("Q")) {
            System.out.println("(A) Add song");
            System.out.println("(P) Play song");
            System.out.println("(Pr) Print history");
            System.out.println("(C) Clear history");
            System.out.println("(D) Delete from history");
            System.out.println("(Q) Quit");
            System.out.println();
            System.out.print("Enter your choice: ");
            
            userAction = listenerInput.nextLine();

            if(userAction.equalsIgnoreCase("A")) {
                addATrack(listenerInput, requestedTracks);
            } else if(userAction.equalsIgnoreCase("P")) {
                playTheTrack(listenerInput, requestedTracks, listeningHistory);
            } else if(userAction.equalsIgnoreCase("Pr")) {
                printTrackHistory(listenerInput, listeningHistory);
            } else if(userAction.equalsIgnoreCase("C")) {
                listeningHistory.clear();
            } else if(userAction.equalsIgnoreCase("D")) {
                deleteTrackHistory(listenerInput, listeningHistory);
            } 
        }
    }

/*
    Behavior: This method allows the user to add a song they would like to play to a list. It asks
                the user for a song they would like to add and then adds what the user requested to 
                a list. It functions like the song queue on Spotify in that the most recent song added 
                to the list will be played last.
    Parameters: listenerInput: a scanner that allows the method to take user input
                requestedTracks: a list that keeps track of the songs the user adds
*/
    public static void addATrack(Scanner listenerInput, Queue<String> requestedTracks) {
        String trackSelection = "";

        System.out.print("Enter song name: ");
        trackSelection = listenerInput.nextLine();
        requestedTracks.add(trackSelection);
        System.out.println("Successfully added " + trackSelection);
        System.out.println();
        System.out.println();
    }

/*
    Behavior: This method allows the user to play a song they added to their list. The oldest song
                added to the list will be played first and the most recent will be played last. After
                the song has played, the song will be added to the user's song history.
    Exceptions: IllegalStateException: this exception will be thrown when the user requests to play a song
                                        when there are no songs in their song list
    Parameters: listenerInput: a scanner that allows the method to take user input
                requestedTracks: a list that keeps track of the songs the user adds
                listeningHistory: a list that keeps track of what songs the user has played (song history)
*/
    public static void playTheTrack(Scanner listenerInput, Queue<String> requestedTracks, Stack<String> listeningHistory) {
        String trackSelection = "";

        if(requestedTracks.size() == 0) {
            throw new IllegalStateException("You have no songs currently queued up! Please add a song to start listening.");
        }
        
        trackSelection = requestedTracks.remove();
        System.out.println("Playing song: " + trackSelection);
        System.out.println();
        System.out.println();
        listeningHistory.push(trackSelection);
    }

/*
    Behavior: This method prints out the songs that the user has played (song history).
    Exceptions: IllegalStateException: this exception is thrown when the user requests
                their song history and their song history is empty. 
    Parameters: listenerInput: a scanner that allows the method to take user input
                listeningHistory: a list that keeps track of what songs the user has played (song history)
*/
    public static void printTrackHistory(Scanner listenerInput, Stack<String> listeningHistory) {
        String currentTrack = "";
        Stack<String> temporaryHistory = new Stack<>();
        if(listeningHistory.size() == 0) {
            throw new IllegalStateException("You have not played any songs yet.");
        }

        while(!listeningHistory.isEmpty()) {
            currentTrack = listeningHistory.pop();
            System.out.println("    " + currentTrack);
            temporaryHistory.push(currentTrack);
        }
        System.out.println();
        System.out.println();

        stackToStack(temporaryHistory, listeningHistory);
    }

/*
    Behavior: This method allows the user to delete sections of their song history. They can choose how many 
                songs they want to delete and whether they want to delete from the top of the list or the bottom.
                If the user inputs a positive number, then the program will delete songs from the top of the history.
                If the user inputs a negative number, then the program will delete songs from the bottom of the history.
    Exceptions: IllegalArgumentException: this exception is thrown when the user requests to delete more songs
                                            than the number of songs in their song history
    Parameters: listenerInput: a scanner that allows the method to take user input
                listeningHistory: a list that keeps track of what songs the user has played (song history)
*/
    public static void deleteTrackHistory(Scanner listenerInput, Stack<String> listeningHistory) {
        int numTracks = 0;

        System.out.println("A positive number will delete from recent history.");
        System.out.println("A negative number will delete from the beginning of history.");
        System.out.print("Enter number of songs to delete: ");

        numTracks = Integer.parseInt(listenerInput.nextLine());
        System.out.println();

        if(Math.abs(numTracks) > listeningHistory.size()) {
            throw new IllegalArgumentException("The number of songs you requested to delete is greater than the number of songs in your history.");
        }

        if(numTracks >= 0) {
            for(int a = 0; a < numTracks; a++) {
                listeningHistory.pop();
            }
        } else {
            Stack<String> temporaryList = new Stack<>();
            stackToStack(listeningHistory, temporaryList);
            for(int a = 0; a < Math.abs(numTracks); a++) {
                temporaryList.pop();
            }
            stackToStack(temporaryList, listeningHistory);
        }
    }

    /*
    Behaviour: This method accepts two stacks and is able to move values from 
                one stack to another.
    Parameters: s1: a stack the user inputs and would like values moved out of
                s2: a stack the user inputs and would like values moved into
    */
    public static void stackToStack(Stack<String> s1, Stack<String> s2) {
        while(!s1.isEmpty()) {
            s2.push(s1.pop());
        }
    }
}