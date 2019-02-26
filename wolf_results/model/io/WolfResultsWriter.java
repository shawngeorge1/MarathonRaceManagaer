package edu.ncsu.csc216.wolf_results.model.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import edu.ncsu.csc216.wolf_results.race_results.Race;
import edu.ncsu.csc216.wolf_results.race_results.RaceList;
import edu.ncsu.csc216.wolf_results.race_results.RaceResultList;

/**
 * Class responsible for Writing to a file
 * 
 * @author Shawn George
 * @author Justin Easow
 *
 */
public class WolfResultsWriter {

	/**
	 * Writes to a file
	 * 
	 * @param fileName
	 *            file name to write to
	 * @param list
	 *            list being written to
	 */
	public static void writeRaceFile(String fileName, RaceList list) {
		if (!fileName.endsWith(".md") || fileName == null || fileName.equals("")  || list == null) {
			throw new IllegalArgumentException("Error opening file.");
		}
		PrintStream writer = null;
		try {
			writer = new PrintStream(new File(fileName));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File not Saved");
		}
		try {
			for (int i = 0; i < list.size(); i++) {
				Race race = list.getRace(i);
				writer.println("# " + race.getName());
				writer.println(String.valueOf(race.getDistance()));
				writer.println(race.getDate().toString());
				writer.println(race.getLocation());
				writer.println();

				RaceResultList resultList = race.getResults();
				for (int j = 0; j < resultList.size(); j++) {
					writer.println("*" + " " + resultList.getResult(j).getName() + ","
							+ resultList.getResult(j).getAge() + "," + resultList.getResult(j).getTime());
				}
				writer.println();
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("File not saved");
		}
	}

}
