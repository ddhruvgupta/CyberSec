/**
 * @author Dhruv Gupta
 * @PantherID 900806428
 * @email dgupta3@student.gsu.edu 
 * 
 * Graduate Student - Georgia State University
 * CSC 6222 Assignment 3
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class dgupta3A3 {

	private static Random rand = new Random();

	private static String msg;
	private static String msgE;
	private static String msgD;

	private static String BruteForceResult;
	private static int key;

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO: You can only call methods in main method

		String fileName = "words_alpha.txt";
		File wordList = new File(fileName);
		List<String> words = new ArrayList<>();
		Scanner reader = null;

		try {
			reader = new Scanner(wordList);
		} catch (FileNotFoundException e) {
			System.out.println("file \"" + fileName + "\" not found");
			System.exit(0);
		}

		while (reader.hasNextLine()) {
			String word = reader.nextLine();
			if (word.length() > 6)
				words.add(word);
		}

		key = generateKey();

//	        key = 65535;
		System.out.println("Key : " + key);
		msg = generateMsg(words);
		System.out.println("Word : " + msg);
		msgE = encryption(key, msg);
		System.out.println("Encrypted : " + msgE);

		msgD = decryption(key, msgE);
		System.out.println("decrypted : " + msgD);

		System.out.println("Starting Bruteforce ...");
		System.out.println("...Please wait.. ");
		BruteForceResult = bruteForce(msgE, words);
		System.out.println("Bruteforce : " + BruteForceResult);
	}

	private static int generateKey() {
		// TODO: implement step a (randomly generate 16-bit key)
		return (rand.nextInt(1 + Short.MAX_VALUE - Short.MIN_VALUE)); // Short is 16 bit
	}

	private static String generateMsg(List<String> words) {
		// TODO: implement step b (randonly generate a string with an even number of
		// characters)

		String s = words.get(rand.nextInt(100000));

		System.out.println("Word Length: " + s.length());

		return s;
	}

	private static String encryption(int key, String msg) throws UnsupportedEncodingException {
		// TODO: implement step c (encrypt the message)

		// if String length is odd, add space at end
		if (msg.length() % 2 != 0)
			msg = msg.concat(" ");

		// convert string to Byte string for feeding into XOR engine
		byte[] utf8Bytes = msg.getBytes("UTF-8");
		int k = utf8Bytes.length;

		// prepare key for encryption
		String key2 = new_key(key, k);

		String result = new String(xorWithKey(msg.getBytes(), key2.getBytes()));
		return result;

		// return base64Encode(xorWithKey(msg.getBytes(), key2));

	}

	private static String new_key(int key2, int k) {
		// TODO Auto-generated method stub
		String newkey = Integer.toString(key2);
		String output = new String();
//	    	System.out.println("byte length = "+ k);
		for (int i = 1; i < (k / 2); i++)
			output = output.concat(newkey);

//	    	System.out.println("K || K = "+ output);

		return output;
		// return output;
	}

	private static String decryption(int key, String msgE) throws UnsupportedEncodingException {
		// TODO: implement step d (decryption)

		byte[] utf8Bytes = msgE.getBytes("UTF-8");
		int k = utf8Bytes.length;
		String key2 = new_key(key, k);

		String result = new String(xorWithKey(msgE.getBytes(), key2.getBytes()));
		return result;
	}

	private static String bruteForce(String msgE, List<String> words) {
		// TODO: implement bruteForce algorithm, you may need the above
		// decryption(key,msgE) method

		// 16 bit key generator
		int key = 65535, found = 0;
		String msg = new String();

		while (key >= 0 && found != 1) {
			try {
				msg = decryption(key, msgE);
				
				//remove extra space from decrypted message to check against dictionary
				msg = msg.replaceAll("\\s+", "");
				
				// handle case where decrypted word is an actual word.
				// Re-encrypt message and check if it matches the original encrypted message
				if (words.contains(msg) && msgE.equals(encryption(key, msg))) {
					found++;
				} else
					key--;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Key Found: " + key);
		return msg;

	}

	private static byte[] xorWithKey(byte[] msg, byte[] key) {
		// TODO: Run XOR msg using the key
		byte[] out = new byte[msg.length];

		for (int i = 0; i < msg.length; i++) {
			out[i] = (byte) (msg[i] ^ key[i % key.length]);
		}

		return out;
	}

}
