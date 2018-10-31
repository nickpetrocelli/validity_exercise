package app.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.text.similarity.LevenshteinDistance;

/**
 * Class representing an entry in the csv file.
 * @Author Nick Petrocelli
 */
public class Entry {
  //Fields meant to represent each value of the entry.
  private final String id;
  private final String first_name;
  private final String last_name;
  private final String company;
  private final String email;
  private final String address1;
  private final String address2;
  private final String zip;
  private final String city;
  private final String state_long;
  private final String state;
  private final String phone;
  private final String full_line;

  /**
   * Constructor that pulls the values out from one line of the csv file.
   * @param line
   */
  public Entry(String line) throws IllegalArgumentException{
    full_line = line;
    //Deliminate by string
    List<String> list1 = Arrays.asList(line.split(","));
    ArrayList<String> list = new ArrayList<>(list1);
    ArrayList<String> remove = new ArrayList<>();

    //Need to handle case where comma appears in string
    //Do this by combining elements bounded by " characters
    boolean collecting = false;
    String buf = "";
    int pos = 0;
    for (int i = 0; i<list.size(); i++){
      if(list.get(i).contains("\"") && !collecting){
        //Combine items contained by "
        buf = buf + list.get(i);
        pos = i;
        collecting = true;
      }
      else if (list.get(i).contains("\"") && collecting){
        remove.add(list.get(i));
        buf = buf + list.get(i);
        list.set(pos, buf);
        buf = "";
        collecting = false;
      }
      else if (collecting){
        remove.add(list.get(i));
        buf = buf + list.get(i);
      }
    }

    list.removeAll(remove);
    //Check input
    if (list.size() != 12) {
      throw new IllegalArgumentException("Error: input string not valid csv entry");
    }

    //push into fields
    id = list.get(0);
    first_name = list.get(1);
    last_name = list.get(2);
    company = list.get(3);
    email = list.get(4);
    address1 = list.get(5);
    address2 = list.get(6);
    zip = list.get(7);
    city = list.get(8);
    state_long = list.get(9);
    state = list.get(10);
    phone = list.get(11);

  }

  /**
   * Returns an integer that represents the difference between two entries.
   * The algorithm is as follows:
   * <ul>
   *   <li>Start a running distance total.</li>
   *   <li>Convert name values to Metaphone representation.</li>
   *   <li>Find levenshtein distance between metaphone representations, add to total.</li>
   *   <li>Find levenshtein distance between remaining fields and add to total.</li>
   *   <li>Return total.</li>
   * </ul>
   * The idea is that whatever is calling for a comparison can determine what
   * total is considered a match.
   * @param other The entry to be compared to.
   * @return
   */
  public int compare(Entry other){
    int compTotal = 0;
    LevenshteinDistance ld = new LevenshteinDistance();
    compTotal = compTotal + this.compareNames(this.first_name, other.getFirst_name());
    compTotal = compTotal + this.compareNames(this.last_name, other.getLast_name());
    compTotal = compTotal + ld.apply(this.company, other.getCompany());
    compTotal = compTotal + ld.apply(this.email, other.getEmail());
    compTotal = compTotal + ld.apply(this.address1, other.getAddress1());
    compTotal = compTotal + ld.apply(this.address2, other.getAddress2());
    compTotal = compTotal + ld.apply(this.zip, other.getZip());
    compTotal = compTotal + ld.apply(this.city, other.getCity());
    compTotal = compTotal + ld.apply(this.state_long, other.getState_long());
    compTotal = compTotal + ld.apply(this.state, other.getState());
    compTotal = compTotal + ld.apply(this.phone, other.getPhone());
    return compTotal;
  }

  //Compare names according to the algorithm given by compare().
  private int compareNames(String n1, String n2){
    Metaphone mp = new Metaphone();
    String m1 = mp.metaphone(n1);
    String m2 = mp.metaphone(n2);
    LevenshteinDistance ld = new LevenshteinDistance();
    return ld.apply(m1, m2);
  }

  public String getFirst_name() {
    return first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public String getCompany() {
    return company;
  }

  public String getEmail() {
    return email;
  }

  public String getAddress1() {
    return address1;
  }

  public String getAddress2() {
    return address2;
  }

  public String getZip() {
    return zip;
  }

  public String getCity() {
    return city;
  }

  public String getState_long() {
    return state_long;
  }

  public String getState() {
    return state;
  }

  public String getPhone() {
    return phone;
  }

  public String getFull_line() {
    return full_line;
  }
}
