/*
Kurt Kaiser
CTIM 168
07.28.2018
Homework: C10PP10
RFID Race Data Text File Log
 */

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.util.Scanner;

public class Main {

    // Demonstrating the program
    public static void main(String[] args) throws IOException {
        String fileName =
                "raceTextData.txt";
        int[][] runnerTimes = new int[3][];
        String[] runners; // = new String[3];
        int[][] splits = new int[3][2];
        System.out.println(" ---  Cheating Data ---");
        try {
            // Create object
            RaceDataLog recent = new RaceDataLog(fileName);
            // Calling methods
            // Checking for skipped sensors
            runners = recent.skippedSensor(fileName);
            if (runners[0].equals("cheated")) return;
            // Getting runners' times to check for split pace faster than 4:30
            runnerTimes[0] = recent.getTimes(runners[0]);
            runnerTimes[1] = recent.getTimes(runners[1]);
            runnerTimes[2] = recent.getTimes(runners[2]);
            for (int i = 0; i < 3; i++)
            {
                splits[i][0] = recent.getSplits(runnerTimes[i], 0);
                splits[i][1] = recent.getSplits(runnerTimes[i], 1);
                //System.out.println("Racer " + runners[i] + " Split1: " + splits[i][0] + " Splits 2: " + splits[i][1]);
                if (splits[i][0] <= 270  || splits[i][1] <=270){
                    System.out.println("Racer " + runners[i] + " has a split time faster " +
                            " than 4:30 a mile, runner possibly cheated.");
                }
            }
            System.out.println("-- Cheaters list above, unless data appeared legitimate --");
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file " + fileName);
        }
    }
}

