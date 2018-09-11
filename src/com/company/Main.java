package com.company;

import java.util.Scanner;

public class Main {
    static final int DICE_FACES = 6;
    static int[] highScore = {0,0,0};

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Välkommen till tärningsspelet.");
        do {
            System.out.print("Välj antal spelare: ");
            boolean useComputer = true;
            int numberOfPlayers = scan.nextInt();
            if(numberOfPlayers > 1) {
                System.out.print("Vill du att datorn ska spela också?(\"j\" för ja) ");
                useComputer = scan.next().equals("j");
            }
            System.out.print("Välj antal kast: ");
            int rolls = scan.nextInt();
            scan.nextLine();
            numberOfPlayers = (useComputer) ? numberOfPlayers+1 : numberOfPlayers;
            int[] scores = new int[numberOfPlayers];

            //Gå igenom alla spelarna och kasta tärningarna
            for (int i = 0; i < scores.length; i++) {
                boolean isComputer = useComputer && i == scores.length-1;
                System.out.println(getNewPlayerText(isComputer, i));
                for(int j = 0; j < rolls; j++) {
                    scores[i] += roll(isComputer);
                    System.out.println("Ny summa: " + scores[i]);
                }
                //Uppdatera highscore för spelare
                if(!isComputer) {
                    updateHighScore(scores[i]);
                }

                System.out.print(endOfRollsText(isComputer, scores[i]));
                scan.nextLine();
            }

            //Skriv ut vem som vann
            System.out.println(getWinnerText(getWinner(scores) == scores.length-1 && useComputer, getWinner(scores)));

            //Kolla om spelaren vill fortsätta
            System.out.print("Skriv \"j\" för att fortsätta spela. Annars avslutas spelet: ");
        } while (scan.next().equals("j"));

    }

    static int roll(boolean isComputer) {
        int roll = (int)(Math.random()*DICE_FACES)+1;
        if(isComputer){
            System.out.println("Datorn fick: " + roll);
        }
        else{
            System.out.println("Du fick: " + roll);
        }
        return roll;
    }

    static void updateHighScore(int score){
        for(int i = 0; i < highScore.length; i++){
            if(score > highScore[i])
            {
                //Flytta ned listan
                for(int j = highScore.length-1; j > 0; j--){
                    if(highScore[j] < highScore[j-1]){
                    highScore[j] = highScore[j-1];
                    }
                }

                //Uppdatera poäng
                highScore[i] = score;

                System.out.println("Nytt highscore! Du är på plats " + (i+1) + " av " + highScore.length + ".");
                printHighScore();
                return;
            }
        }
    }

    static void printHighScore(){
        System.out.println("HIGHSCORE: ");
        for(int i = 0; i < highScore.length; i++) {
            System.out.println("\t" + (i+1) + " - " + highScore[i]);
        }
    }

    static String endOfRollsText(boolean isComputer, int score) {
        String s;
        if(isComputer) {
            s = "Datorn fick " + score;
        }
        else {
            s = "Du fick " + score;
        }
        s += ". Tryck enter för att fortsätta.";
        return  s;
    }

    static String getNewPlayerText(boolean isComputer, int player) {
        if(isComputer) {
            return "Datorns tur.";
        }
        else {
            return "Spelare " + (player + 1) + " tur.";
        }
    }

    static int getWinner(int[] scores) {
        int highestScore = 0;
        int winner = 0;
        for (int i = 0; i < scores.length; i++)
        {
            if(scores[i] > highestScore)
            {
                winner = i;
                highestScore = scores[i];
            }
        }
        return winner;
    }

    static String getWinnerText(boolean isComputer, int player) {
        if(!isComputer) {
            return "Spelare " + (player+1) + " vann!";
        }
        else {
            return "Datorn vann.";
        }
    }
}
