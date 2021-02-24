package com.trgr.quality.maf.commonutils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**Class to house all methods that deal with generating (pseudo)random content
 *
 */
public class RandomUtils
{
	/**Generates a random string of characters
	 * @param rng
	 * @param length
	 * @return
	 */
	public static String generateRandomString(int length)
	{
		Random rng = new Random();
		String characters = "test";
		
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}
	
	/*
	 * Returns a 12 digit number based on the current time. (to get unique number).
	 */
	public static String getUniqueNumber(){
		
		try
		{
			Date dNow = new Date();
	        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmss");
	        String datetime = ft.format(dNow);
	        
	       return datetime;		
		}
		catch(Exception ex)
		{
			System.out.println(ex);
			return new Date().toString();
		}
	}
	
	/**Generates a random Name
	 * 
	 */
	public static String generateRandomName()
	{
		return generateRandomString(5) + " " + generateRandomString(10);
	}

	/**Generates a random Email
	 * @return
	 */
	public static String generateRandomEmail()
	{
		return generateRandomString(4) + "@" + generateRandomString(6) + ".test";
	}

	/**Generates a random Company Name
	 * @return
	 */
	public static String generateRandomCompanyName()
	{		
		return new Date().toString();
	}

	/**Generates a random name, email, and company name. Returns a String[]
	 * @return
	 */
	public static String[] generateRandomProfileInfo()
	{
		String[] info = {generateRandomName(), generateRandomEmail(), generateRandomCompanyName()};
		return info;
	}
	
	/**Generates an int[] of pseudorandom numbers of length {count} between {min} and {max} inclusive
	 * Does not allow for duplicates
	 * @param count - number of random numbers to generate
	 * @param min - minimum number to generate
	 * @param max - maximum number to generate
	 */
	public static int[] generatePseudoRandomNumberArray(int count, int min, int max)
	{
		List<Integer> randomNumberList = new ArrayList<Integer>();
		int[] randomNumberArray = new int[count];
		
		for(int i = min; i <= max; i++)
		{
			randomNumberList.add(i);
		}
		
		for(int j = 0; j < count; j++)
		{
			int randInt = RandomUtils.generatePsuedoRandomNumber(0, randomNumberList.size()-1);
			randomNumberArray[j] = randomNumberList.get(randInt);					
			randomNumberList.remove(randInt);
		}
	
		return randomNumberArray;
	}

	/**Generates a random integer between {min} and {max} inclusive
	 * @param min
	 * @param max
	 * @return
	 */
	public static int generatePsuedoRandomNumber(int min, int max)
	{
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(max-min+1) + min;
		
		return randomInt;
	}

	/**Generate random watchlist name
	 * @return
	 */
	public static String generateRandomWatchlistName()
	{		
		return new Date().toString();
	}

}
