// Chapter 10: Generic Programming and Collection Classes
// Stream API as well

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class Chapter10Exercises {
    public static void main(String[] args) {
        Chapter10Exercises MainClass = new Chapter10Exercises();

        Chapter10Exercises.PhoneDirectory phoneDirectory = MainClass.new PhoneDirectory();
        out.println("Exercise 1 - Create a phone directory using a TreeMap.");
        phoneDirectory.putNumber("Igor", "123-445-6789");
        phoneDirectory.putNumber("Lori", "987-654-3210");
        out.println("The current number of numbers in the directory is " + phoneDirectory.dataCount);
        out.println("Finding the phone number for Igor: " + phoneDirectory.getNumber("Igor"));
        out.println("Determining if the name 'Lori' is in the directory: " + phoneDirectory.find("Lori"));
        out.println("Person called 'Dave' not in directory, so return is: " + phoneDirectory.getNumber("Dave"));
        out.println();

        out.println("Exercise 3 - Create a hash map data structure without built-in generic types");
        out.println("Implement the methods: get(key), put(key,value), remove(key), containsKey(key), and size()");
        out.println();
        Chapter10Exercises.HashTable hashTable = MainClass.new HashTable();
        hashTable.put("0", "val0");
        hashTable.put("1", "val1");
        hashTable.put("2", "val2");
        hashTable.put("3", "val3");
        hashTable.put("4", "val4");
        hashTable.put("5", "val5");
        hashTable.put("6", "val6");
        hashTable.put("7", "val7");
        hashTable.put("8", "val8");
        hashTable.put("9", "val9");
        hashTable.put("11", "val11");
        hashTable.put("12", "val12");
        hashTable.put("13", "val13");
        hashTable.put("14", "val14");
        hashTable.put("15", "val15");
        hashTable.put("16", "val16");
        hashTable.put("17", "val17");
        hashTable.put("18", "val18");
        hashTable.put("19", "val19");
        hashTable.put("200", "val200");
        out.println("Testing a hashmap with 20 key/value pairs. Items are located at:");
        hashTable.printTable();
        out.println();
        out.println("The number of items in the hashmap is " + hashTable.size());
        out.println("Testing .containsKey('1') which returns true: " + hashTable.containsKey("1"));
        out.println("Testing .containsKey('60') which returns false: " + hashTable.containsKey("06"));
        out.println("Testing .get('5') which returns: " + hashTable.get("5"));
        out.println();
        out.println("Testing .remove('2') which removes the node with value: " + hashTable.remove("2").value);
        out.println("The updated number of items in the hashmap is " + hashTable.size());
        out.println();
        out.println("The updated table looks like this: ");
        hashTable.printTable();
        out.println();
        out.println("Testing replacing the value of an existing key, 1 with the updated value, val1Updated");
        hashTable.put("1", "val1Updated");
        out.println("The new value of the existing key, 1, is: " + hashTable.get("1"));
        out.println("Testing replacing the value of an existing key, 200 with the updated value, val200Updated");
        hashTable.put("200", "val200Updated");
        out.println("The new value of the existing key, 200, is: " + hashTable.get("200"));
        out.println();
        out.println("The updated table looks like this: ");
        hashTable.printTable();
        out.println();

        out.println("Exercise 5 - Trying out the Stream API with a list of students");
        out.println("Info for each student includes: first name, last name, score");
        out.println();
        StreamTest streamTest = MainClass.new StreamTest();
        long numOfStudents = Arrays.stream(streamTest.scoreData)
                .count();
        out.println("Total num of students is: " + numOfStudents);
        out.println();

        double sumOfScores = Arrays.stream(streamTest.scoreData)
                .mapToInt(s -> s.score)
                .sum();
        out.println("Average class score is: " + sumOfScores/numOfStudents);
        out.println();

        long studentsGotA = Arrays.stream(streamTest.scoreData)
                .mapToInt(s -> s.score)
                .filter(x -> x > 90)
                .count();
        out.println("Num of students who got A: " + studentsGotA);
        out.println();

        List<String> failingStudents = Arrays.stream(streamTest.scoreData)
                .filter(s -> (s.score < 70))
                .map(s -> (s.firstName) + " " + (s.lastName))
                .collect(Collectors.toList());
        out.println("The failing students are: ");
        failingStudents.forEach(out::println);
        out.println();

        out.println("The list of all students, ordered by last name:");
        Arrays.stream(streamTest.scoreData)
                .sorted( (s1, s2) -> s1.lastName.compareTo(s2.lastName))
                .forEach(s -> out.println(s.lastName + ", " + s.firstName));
        out.println();

        out.println("The list of all students, ordered by score:");
        Arrays.stream(streamTest.scoreData)
                .sorted((s1, s2) -> s2.score - s1.score)
                .forEach(s -> out.println(s.firstName + " " + s.lastName + ": " + s.score));
    }


    /*
        Exercise 1: Rewrite the PhoneDirectory class from Subsection 7.4.2
        so that it uses a TreeMap to store (solution) directory entries, instead of an array.
     */

    /*
     * A PhoneDirectory holds a list of names with a phone number for
     * each name. It is possible to find the number associated with
     * a given name, and to specify the phone number for a given name.
     */
    public class PhoneDirectory {
        /*
         * An object of type PhoneEntry holds one name/number pair.
         */
        private class PhoneEntry {
            String name; // The name.
            String number; // The associated phone number.
        }
        private final Map<String, String> data; // TreeMap that holds the name/number pairs.
        private int dataCount; // The number of pairs stored in the array.
        /*
         * Constructor creates an initially empty directory of type TreeMap.
         */
        public PhoneDirectory() {
            data = new TreeMap<>();
            dataCount = 0;
        }
        /*
         * Looks for a name/number pair with a given name. If found, the method
         * returns a boolean value of true. If no pair contains the
         * given name, then the return value is false. This private method is
         * used internally in getNumber() and putNumber().
         */
        private boolean find( String name ) {
            return data.containsKey(name); // Checks if the name is in the map.
        }
        /*
         * Finds the phone number, if any, for a given name.
         * @return The phone number associated with the name; if the name does
         * not occur in the phone directory, then the return value is null.
         */
        public String getNumber( String name ) {
            boolean foundName = find(name);
            if (!foundName)
                return null; // There is no phone entry for the given name.
            else
                return data.get(name);
        }
        /*
         * Associates a given name with a given phone number. If the name
         * already exists in the phone directory, then the new number replaces
         * the old one. Otherwise, a new name/number pair is added. The
         * name and number should both be non-null. An IllegalArgumentException
         * is thrown if this is not the case.
         */
        public void putNumber( String name, String number ) {
            if (name == null || number == null)
                throw new IllegalArgumentException("name and number cannot be null");
            boolean foundName = find(name);
            if (foundName) {
                // The name already exists in the Map.
                // Just replace the old number at that position with the new.
                data.put(name, number);
            }
            else {
                // Add a new name/number pair to the Map.
                PhoneEntry newEntry = new PhoneEntry(); // Create a new pair.
                newEntry.name = name;
                newEntry.number = number;
                data.put(name, number); // Add the new pair to the Map.
                dataCount++;
            }
        }
    } // end class PhoneDirectory


    /*
        Exercise 3: Write a hashmap implementation from scratch.
        The hash table includes  keys and values of type String.
        Define the following methods: get(key), put(key,value), remove(key), containsKey(key), and size()
        Use obj.hashCode() to compute a hash code
        Do not use any of Java’s built-in generic types; create your own linked lists using nodes as covered in
    */

    public class HashTable {
        private final SinglyLinkedList[] table;

        // Constructor
        public HashTable() {
            /* Instantiate array: each index
                a) is a singly linked list
                b) represents a hash code shared by all keys which are stored in that singly linked list
                b) each node in the list stores key,value pairs
             */
            table = new SinglyLinkedList[10];

            // Create a new singly linked list at each table index
            for(var i = 0; i < table.length; i++) {
                table[i] = new SinglyLinkedList();
            }

        }

        // Method to identify whether a key/value pair is in the hash map or not. Returns boolean.
        public boolean containsKey( String k) {
            int index = getHash(k);
            SinglyLinkedList.ListNode pointer = table[index].head; // Start at the head

            // Traverse list to look for key
            while(pointer != null) {
                if(pointer.key.equals(k)) return true;
                pointer = pointer.next;
            }
            return false;
        }

        // Method to insert key,value pair in hashmap
        public void put( String key, String value) {
            // Throws error if either key or value arguments are empty
            if (key == null || value == null)
                throw new IllegalArgumentException("key and value inputs cannot be null");

            int index = getHash(key); // Get key hashcode, which is the index in the array table
            SinglyLinkedList list = table[index]; // identify the starting node in the right list
            list.insertIntoLinkedList(key, value); // insert the key/value pair in the list
        }

        // Removes a key,value pair and returns the node containing that pair, or null if nothing is found
        public SinglyLinkedList.ListNode remove(String key) {
            int index = getHash(key); // Get hash code

//            // Error handling in case the key,value pair is not in the hashmap
//            if(!containsKey(key, table[index])){
//                throw new IllegalArgumentException("key/value pair is not found in the hashmap");
//            }

            // Create two pointers, a current and previous node
            SinglyLinkedList.ListNode node = table[index].head; // start at the head
            SinglyLinkedList.ListNode previousNode = node;

            // Traverse the list, if you find the node
            while(node != null) {
                // If you find the node, let the previous node point to the current node's next.
                if(node.key.equals(key)) {
                    previousNode.next = node.next;
                    table[index].size --; // Decrement linked list size
                    return node;
                }
                else {
                    node = node.next;
                    previousNode = node;
                }
            }
            return null; // Node was not found.
        }

        // Get a node in the hashmap. Returns the key's associated value, or null if the key is not found
        public String get(String key) {
            int index = getHash(key); // Get the index of the table where we will search

            SinglyLinkedList.ListNode node = table[index].head; // start at the head
            SinglyLinkedList.ListNode foundNode;

            // Traverse the list
            while(node != null) {
                if(node.key.equals(key)) {
                    foundNode = node; // identify found node
                    return foundNode.value;
                }
                node = node.next;
            }
            return null; // the node is not in the list
        }

        // Method that returns the size of the hashmap
        public int size() {
            int sum = 0; // Sum of size of all linked lists in hash table
            for (SinglyLinkedList list : table) {
                sum += list.size;
            }
            return sum;
        }

        // Utility method to get the hash of the key
        public int getHash (String key) {
            return Math.abs(key.hashCode()%table.length);
        }

        public void printTable() {
            int counter = 0;
            for(SinglyLinkedList t : table) {
                if(t.size > 0){
                    out.println("Hash table row " + counter + " key/value pairs");
                    t.printList();
                }
                counter++;
            }
        }

        // Inner class for singly linked list operations


    } // end class HashTable

    class SinglyLinkedList {
        private ListNode head;
        private int size;

        // Constructor to initialize list
        public SinglyLinkedList() {
            head = null;
            size = 0;
        }

        // Class to create node
        public class ListNode {
            String key; // An key in the list.
            String value; // An associated value in the list
            ListNode next; // Pointer to the next node in the list.
        }

        // ListNode constructor
        public void ListNode(String k, String v) {
            ListNode newNode = new ListNode();
            newNode.key = k;
            newNode.value = v;
        }


        public void insertIntoLinkedList (String k, String v) {
            // Create the linked list node
            ListNode newNode;
            newNode = new ListNode();
            newNode.key = k;
            newNode.value = v;

            if (head == null){
                head = newNode;
            }
            else {
                ListNode pointer = head;
                int counter = 0;
                while(counter < size) {
                    if(pointer.key.equals(k)) { // If the key exists in the linked list, replace the associated value with the new v.
                        pointer.value = v;
                        return;
                    }
                    else if(pointer.next == null){
                        // The key does not exist in list. Add it to list.
                        pointer.next = newNode;
                    }
                    pointer = pointer.next;
                    counter++;
                }
            }
            size++; // increment list size
        } // end insertIntoLinkedList();

        public void printList() {
            assert head != null : "You cannot print an empty list";
            SinglyLinkedList.ListNode pointer = head;
            while(pointer != null){
                out.println("{ " + pointer.key + ": " + pointer.value + " }");
                pointer = pointer.next;
            }
        } // end printList();

    } // end SinglyLinkedList class


    /*
        Exercise 4: Define generic static methods for working with predicate objects
     */

    class TestPredicates {

        /*
            Return the index of the first item in list for which the predicate is true, if any.
            If there is no such item, return -1.
         */
        public <T> int find(ArrayList<T> list, Predicate<T> pred) {
            // Return the index of the first item in list
            // for which the predicate is true, if any.
            for(int i = 0; i < list.size(); i++) {
                T item = list.get(i);
                if(pred.test(item)){
                    return i;
                }
                // If there is no such item, return -1.
            }
            return -1;
        }

        /*
            Remove every object, obj, from coll for which pred.test(obj) is false.
            (That is, retain the objects for which the predicate is true.)
         */
        public <T> void retain(Collection<T> coll, Predicate<T> pred) {
            // Remove every object, obj, from coll for which
            // pred.test(obj) is false. (That is, retain the
            // objects for which the predicate is true.)
//            Iterator<T> iter = coll.iterator();
//            while(iter.hasNext()) {
//                T item = iter.next();
//                if(!pred.test(item)) {
//                    iter.remove();
//                }
//            }

            // Another way
            coll.removeIf(item -> !pred.test(item));
        }

        public <T> List<T> collect(Collection<T> coll, Predicate<T> pred) {
            // Return a List that contains all the objects, obj,
            // from the collection, coll, such that pred.test(obj)
            // is true.
            List<T> list = new ArrayList<>();
//        Iterator approach
//        Iterator<T> iter = coll.iterator();
//        while(iter.hasNext()) {
//            T item = iter.next();
//            if(pred.test(item)) {
//                list.add(item);
//            }
//        }

//        For-each loop approach
            for( T item : coll) {
                if(pred.test(item)) {
                    list.add(item);
                }
            }

            return list;
        }

    } // end class TestPredicates


    /*
        Exercise 5: Short exercise using Stream API
        Create a report card list of students, where info for each student includes: first name, last name, score
        Stream operations occur in the main routine
     */
    class StreamTest {
        class ScoreInfo {
            String firstName;
            String lastName;
            int score;

            ScoreInfo( String lName, String fName, int s ) {
                firstName = fName;
                lastName = lName;
                score = s;
            }

        }
        // Create an array of students to play with
        private final ScoreInfo[] scoreData = new ScoreInfo[] {
                new ScoreInfo("Smith","John",70),
                new ScoreInfo("Doe","Mary",85),
                new ScoreInfo("Page","Alice",82),
                new ScoreInfo("Cooper","Jill",97),
                new ScoreInfo("Flintstone","Fred",66),
                new ScoreInfo("Rubble","Barney",80),
                new ScoreInfo("Smith","Judy",48),
                new ScoreInfo("Dean","James",90),
                new ScoreInfo("Russ","Joe",55),
                new ScoreInfo("Wolfe","Bill",73),
                new ScoreInfo("Dart","Mary",54),
                new ScoreInfo("Rogers","Chris",78),
                new ScoreInfo("Toole","Pat",51),
                new ScoreInfo("Khan","Omar",93),
                new ScoreInfo("Smith","Ann",95)
        };
    } // end class StreamTest


} // end class Chapter10Exercises
