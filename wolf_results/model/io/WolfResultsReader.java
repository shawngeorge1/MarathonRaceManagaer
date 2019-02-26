package edu.ncsu.csc216.wolf_results.model.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

import edu.ncsu.csc216.wolf_results.race_results.IndividualResult;
import edu.ncsu.csc216.wolf_results.race_results.Race;
import edu.ncsu.csc216.wolf_results.race_results.RaceList;
import edu.ncsu.csc216.wolf_results.util.RaceTime;

/**
 * Responsible for reading information from a file
 * 
 * @author Shawn George
 * @author Justin Easow
 *
 */
public class WolfResultsReader {

	/**
	 * Reads a file
	 * 
	 * @param fileName
	 *            file name to read
	 * @return Race list from file
	 */
	@SuppressWarnings("resource")
	public static RaceList readRaceListFile(String fileName) {
		try {
			RaceList list = new RaceList();
			Race race = null;

			// keep track of previous char so it may pass # in a name
			String previous = "";

			// check if file is valid
			if (!fileName.endsWith(".md")) {
				throw new IllegalArgumentException("Error opening file");
			}
			// Reader file name

			Scanner reader = null;
			try {
				reader = new Scanner(new File(fileName));
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("Error opening file");
			}

			while (reader.hasNextLine()) {
				String line = reader.nextLine();

				// start reading result lines
				if (line.startsWith("*")) {

					@SuppressWarnings("resource")
					Scanner raceResult = new Scanner(line);

					// seperate result information
					raceResult.useDelimiter(",");
					String name = raceResult.next().substring(2);
					// name
					String stringAge = raceResult.next();
					// age
					int age = Integer.parseInt(stringAge);
					// date
					String dateString = raceResult.next();

					// scan date
					Scanner scanTime = new Scanner(dateString);

					// scan date
					scanTime.useDelimiter(":");
					String hourString = scanTime.next();
					int raceHours = Integer.parseInt(hourString);
					String minuteString = scanTime.next();
					int raceMinutes = Integer.parseInt(minuteString);
					String secondsString = scanTime.next();
					int raceSeconds = Integer.parseInt(secondsString);

					RaceTime raceTime = new RaceTime(raceHours, raceMinutes, raceSeconds);
					IndividualResult result;
					try {
						result = new IndividualResult(race, name, age, raceTime);
					} catch (NullPointerException e) {
						scanTime.close();
						raceResult.close();
						throw new IllegalArgumentException("Error opening file");
					}
					race.addIndividualResult(result);

					// sets previous to *
					previous = "*";

					// checks to see if race starts with #
				} else if (line.startsWith("#")) {

					if (previous.equals("*") || previous.equals("#")) {
						throw new IllegalArgumentException("Error opening file");
					}

					String name = line.substring(2);
					// grabs information for race
					String distanceString = reader.nextLine();
					String dateString = reader.nextLine();
					String location = reader.nextLine();
					double distance = Double.parseDouble(distanceString);
					LocalDate date = LocalDate.parse(dateString);
					if (location.equals("")) {
						throw new IllegalArgumentException("Error opening file");
					}

					race = new Race(name, distance, date, location);
					// add race
					list.addRace(race);
					previous = "#";
				} else {
					previous = "";
				}

			}

			reader.close();
			return list;

		} catch (Exception e) {
			throw new IllegalArgumentException("Error opening file");
		}

	}

}