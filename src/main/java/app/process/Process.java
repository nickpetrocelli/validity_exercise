package app.process;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import app.util.Entry;

/**
 * Created by Nick on 10/30/18.
 */
public class Process {
  private static final String FILENAME = "Resources/Validity_take_home_exercise/normal.csv";
  private static final int COMP_THRESHOLD = 10;

  /**
   * Parses the csv defined by FILENAME and returns the string outlined in output.png.
   * @return The string showing which lines are likely duplicates and which are not.
   */
  public static String parse() throws IOException{
    ArrayList<Entry> entryList = new ArrayList<>();
    ArrayList<Entry> duplicates = new ArrayList<>();
    ArrayList<Entry> nonDuplicates = new ArrayList<>();

    //Read lines
    try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
      String line;
      boolean first = true;
      while ((line = br.readLine()) != null) {
        if(first){
          first = false;
          continue;
        }
        Entry en = new Entry(line);
        entryList.add(en);
      }
    }
    catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
      return null;
    }
    //Iterate through entry list and look for duplicates
    for (int i = 0; i<entryList.size(); i++) {
      //check if item was duplicated
      boolean duped = false;
      //Start secondary loop ahead of current item to prevent double-counting
      for (int j=i + 1; j<entryList.size(); j++) {
        int comp = entryList.get(i).compare(entryList.get(j));
        if (comp < COMP_THRESHOLD) {
          duplicates.add(entryList.get(j));
          duped = true;
        }
      }
      if(!duped) {
        nonDuplicates.add(entryList.get(i));
      }
    }
    //format string
    StringBuilder sb = new StringBuilder();
    sb.append("Potential Duplicates:\n\n");
    for(int i = 0; i<duplicates.size(); i++) {
      sb.append(duplicates.get(i).getFull_line());
      sb.append("\n");
    }
    sb.append("\n");
    sb.append("Non Duplicates:\n\n");
    for(int i=0; i<nonDuplicates.size(); i++){
      sb.append(nonDuplicates.get(i).getFull_line());
      sb.append("\n");
    }
    return sb.toString();
  }
}
