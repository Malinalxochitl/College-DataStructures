package HashTable;

import java.util.HashMap;

class Person {
	String firstName, lastName;
	String email; // key
	
	public Person(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	public boolean equals (Object o) {
		if (o == null || !(o instanceof Person)) {
			return false;
		}
		Person other = (Person)o;
		if (other.email.equals(this.email)) {
			return true;
		} else {
			return false;
		}
	}
	public String toString() {
		return "("+firstName+" "+lastName+", "+email+")";
	}
}

class Point {
	int x,y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int hashCode() {
		return (""+x+y).hashCode();
	}
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Point)) {
			return false;
		}
		Point other = (Point)o;
		return this.x == other.x && this.y == other.y;
	}
	public String toString() {
		return "("+x+","+y+")";
	}
}

public class HashUse {

	public static void main (String[] args) {
		
		// HashMap is Java's hash table (key -> value) : (String -> Person)
		HashMap<String,Person> people = new HashMap<String,Person>(500, 2.0f);
		
		Person p = new Person("Ana Paula", "Centeno", "anapaula@cs.rutgers.edu");		
		people.put(p.email, p); // insert into the HT
		
		p = new Person("Sesh","Venugopal","venugopal@cs.rutgers.edu");
		people.put(p.email, p); // insert into HT
		
		System.out.println(people.get("anapaula@cs.rutgers.edu")); // retrieve
		
		//iterate over all of the keys of the HT
		for (String e : people.keySet()) {
			System.out.println(e);
			Person ppp = people.get(e);
			System.out.println(ppp);
		}
		// iterate over all the values of the HT
		for (Person pp : people.values()) {
			System.out.println(pp);
		}	
		
		// hash table for a line
		HashMap<Point, Point> lines = new HashMap<Point,Point>(10, 2.0f);
		Point a = new Point(3,5); // a-b
		Point b = new Point(1,6);
		Point c = new Point(13,7); // c-d
		Point d = new Point(223, 1);
		
		lines.put(a, b);
		lines.put(c, d);
		
		for (Point v : lines.values()) {
			System.out.println("Value: " + v);
		}
		for (Point k : lines.keySet()) {
			System.out.println("Key: " + k + " Value: " + lines.get(k));
		}
	}
}
