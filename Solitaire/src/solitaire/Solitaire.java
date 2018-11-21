package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
	}
	
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {
		printList(deckRear);
		CardNode ptr;
		for (ptr = deckRear;ptr.next.cardValue != 27;ptr = ptr.next); //locating the card before the joker
		swapCards(1, ptr); //swaps the joker with the card in front of it
		printList(deckRear);
	}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {
		//printList(deckRear);
	    CardNode ptr;
	    for (ptr = deckRear; ptr.next.cardValue != 28;ptr = ptr.next); //locating the card before the joker
	    swapCards(2, ptr); //swaps the joker with the 2 cards in front of it
	    //printList(deckRear);
	}
	
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {
		//printList(deckRear);
		if (deckRear.cardValue == 27 || deckRear.cardValue == 28) { //if the rear is a joker
			
			CardNode ptr;
			for(ptr = deckRear; ptr.next.cardValue != 27 && ptr.next.cardValue != 28; ptr = ptr.next); //finding the card before the first joker
			deckRear = ptr; //setting said card as the rear, pushing everything between the front and the back joker to the front
			
		} else if (deckRear.next.cardValue == 27 || deckRear.next.cardValue == 28) { //if the front is a joker
			
			CardNode ptr;
			for (ptr = deckRear.next.next; ptr.cardValue != 27 && ptr.cardValue != 28; ptr = ptr.next); //finding the last joker
			deckRear = ptr; //setting it as the rear, pushing everything after it back to the front
			
		} else { //all other cases
			
			CardNode ptr, front, back;
			for (ptr = deckRear.next; ptr.next.cardValue != 27 && ptr.next.cardValue != 28; ptr = ptr.next); //card before the first joker
			front = ptr.next; //the first joker
			for (back = front.next; back.cardValue != 27 && back.cardValue != 28; back = back.next); //the second joker
			
			ptr.next = back.next; //linking ptr to the card after back, since ptr will be the new rear, and said card will be the new front
			back.next = deckRear.next; //pointing back.next to the front, since this card will be in front of back
			deckRear.next = front; //pointing the current rear towards the front, putting all the cards into the right order
			deckRear = ptr; //setting the rear of the deck to ptr, pushing all the cards into the right spot and finalizing the swap
		}
		//printList(deckRear);
	}
	
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() {		
		//printList(deckRear);
		int count = deckRear.cardValue; //if card value is 27 or 28 it does nothing, since there would be no change
		if (count != 28 && count != 27) {
		
			CardNode back = deckRear.next;
			for (int i = 1; i < count; i++) { //finds the last card to be brought to the back
				back = back.next;
			}
			CardNode ptr1 = back.next; //the card that will be the new front of the deck
			CardNode ptr2;
			for (ptr2 = ptr1; ptr2.next != deckRear; ptr2 = ptr2.next){}; //the card second to the rear
		
			ptr2.next = deckRear.next;
			deckRear.next = ptr1;       //puts the section into the rear
			back.next = deckRear;
		}
		//printList(deckRear);
	}
	
	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
	 * counts down based on the value of the first card and extracts the next card value 
	 * as key. But if that value is 27 or 28, repeats the whole process (Joker A through Count Cut)
	 * on the latest (current) deck, until a value less than or equal to 26 is found, which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey() {
		int key;
		do {
			jokerA();
			jokerB(); 
			tripleCut(); //calling the methods
			countCut();
			int count = deckRear.next.cardValue; //first cards value
			if (count == 28)
				count = 27;
			CardNode ptr = deckRear;
			for (int i = 0; i < count; i++) { //finds said card
				ptr = ptr.next;
			}
			key = ptr.next.cardValue;
		} while (key == 28 || key == 27);
		//printList(deckRear);
		return key;
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}
	
	private void swapCards(int swaps, CardNode ptr) { //swaps the card in front of ptr with some number of cards in front of it
		
		CardNode card = ptr.next; //the card to be swapped with the ones in front of it
		
		for (int i = 0; i < swaps; i++) { //swaps the card with the one in front of it one at a time
			
			if (card.next == deckRear) //this handles the correct organizations should the swap be from the rear to the front
				deckRear = card;
			
	    	ptr.next = card.next;
	    	card.next = ptr.next.next; //swap card with the card in front of it
	    	ptr.next.next = card;
	    	
	    	ptr = ptr.next;   //preparation for next swap
	    	card = ptr.next;
	    }
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {
		char[] newmessage = message.toCharArray();
		String cleanmessage = new String();
		for (int i = 0; i < newmessage.length; i++) {
			if (Character.isLetter(newmessage[i])) {
				cleanmessage += newmessage[i];
			}
		}
		newmessage = cleanmessage.toUpperCase().toCharArray();
		String encrypted = new String();
		for (int i = 0; i < newmessage.length; i++) {
			int key = getKey();
			//System.out.println(key);
			int chr = (newmessage[i] - 'A') + 1 + key;
			if (chr > 26)
				chr -= 26;
			encrypted += (char) (chr - 1 + 'A');
		}
	    return encrypted;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {	
		char[] newmessage = message.toCharArray();
		String decrypted = new String();
		for (int i = 0; i < newmessage.length; i++) {
			int key = getKey();
			int chr = newmessage[i]-'A'+1;
			if (chr <= key)
				chr += 26;
			chr -= key;
			decrypted += (char) (chr-1+'A');
		}
		return decrypted;
	}
}
