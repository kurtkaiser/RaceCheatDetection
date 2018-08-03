/*
Kurt Kaiser
CTIM 168
07.28.2018
Homework: C10PP10
RFID Race Data Text File Log
 */
import java.io.*;
import java.util.Scanner;

public class RaceDataLog {

    private String[][] data = new String[9][];

    public String[][] getData() {
        return data;
    }

    // Constructor
    public RaceDataLog(String fileName) throws FileNotFoundException {
        Scanner inputStream = new Scanner(new File(fileName));
        String startTime = inputStream.nextLine();
        int count = 0;
        String breakUp = "[, ]";
        // Read the rest of the file line by line
        while (inputStream.hasNextLine()) {
            // Use split to create two dimensional array
            data[count] = inputStream.nextLine().split(breakUp);
            count++;
        }
        inputStream.close();
    }

    // Returns run time in seconds of requested number
    public int[] getTimes(String input) {
        int[] runnerTimes = new int[3];
        String[][] data = getData();
        int count = 0;
        int intTime;
        int place = 1;
        for (int i = 0; i < data.length; i++) {
            if (input.contentEquals(data[i][1])) {
                intTime = 3600 * Integer.parseInt(data[i][2]);
                intTime += 60 * Integer.parseInt(data[i][3]);
                intTime += Integer.parseInt(data[i][4]);
                runnerTimes[count] = intTime;
                count++;
            }
        }
        return runnerTimes;
    }

    // Calculate runners pace between markers, output to console
    public int getSplits(int[] runnerTimes, int startSplit) {
        int dist = 1;
        if (startSplit == 0) {
            dist = 7;
        } else if (startSplit == 1) {
            dist = 6;
        } else {
            return 0;
        }
        int split = (runnerTimes[startSplit + 1] - runnerTimes[startSplit]) / dist;
        return split;
    }

    public String getFormattedTime(int total){
        int[] timeArray = new int[3];
        String strTime;
        String result = "";
        timeArray[0] = total / 3600;
        timeArray[1] = (total - (timeArray[0] * 3600)) / 60;
        timeArray[2] = total - timeArray[0] * 3600 - timeArray[1] * 60;
        for (int i = 0; i < 3; i++) {
            strTime = String.valueOf(timeArray[i]);
            if (strTime.length() == 1) strTime = "0" + strTime;
            result += strTime;
            if (i < 2) result += ":";
        }
        return result;
    }

    // Checks if a runner skipped a sensor
    public String[] skippedSensor(String fileName) throws IOException {
        String[] racers = {"na", "na", "na"};
        int[] sensorCount = {0, 0, 0};
        String line;
        Scanner inputStream = new Scanner(new File(fileName));
        // First line race start time, skip over it
        inputStream.nextLine();
        // Read the rest of the file line by line
        while (inputStream.hasNextLine()) {
            // Use split to create two dimensional array
            line = inputStream.nextLine();
            line = line.substring(2, 5);
            for (int i = 0; i < 3; i++) {
                if (racers[i].equals(line)) {
                    sensorCount[i]++;
                    break;
                } else if(racers[i].equals("na")){
                    racers[i] = line;
                    sensorCount[i]++;
                    break;
                }
            }
        }
        // Check for cheaters and print numbers to console
        for (int j = 0; j < 3; j++){
            //System.out.println("Runner: " + racers[j] + " Sensors: " + sensorCount[j]);
            if (sensorCount[j] < 3 && sensorCount[j] > 0) {
                System.out.println("Runner " + racers[j] +
                        " missed a sensor, runner possibly cheated.");
                System.out.println("Data incomplete. Correct data and run again to check for cheating during splits.");
                racers[0] = "cheated";
            }
        }
        return racers;
    }
}
