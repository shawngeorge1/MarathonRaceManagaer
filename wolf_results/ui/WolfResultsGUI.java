package edu.ncsu.csc216.wolf_results.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import edu.ncsu.csc216.wolf_results.manager.WolfResultsManager;
import edu.ncsu.csc216.wolf_results.race_results.IndividualResult;
import edu.ncsu.csc216.wolf_results.race_results.Race;
import edu.ncsu.csc216.wolf_results.race_results.RaceList;
import edu.ncsu.csc216.wolf_results.race_results.RaceResultList;
import edu.ncsu.csc216.wolf_results.util.RaceTime;

/**
 * WolfResults GUI
 * 
 * @author Shawn George
 * @author Justin Easow
 */
public class WolfResultsGUI extends JFrame implements ActionListener, Observer {

	/** ID number used for object serialization. */
	private static final long serialVersionUID = 1L;
	/** Title for top of GUI. */
	private static final String APP_TITLE = "Wolf Results";
	/** Text for the File Menu. */
	private static final String FILE_MENU_TITLE = "File";
	/** Text for the New Issue XML menu item. */
	private static final String NEW_FILE_TITLE = "New";
	/** Text for the Load Issue XML menu item. */
	private static final String LOAD_FILE_TITLE = "Load";
	/** Text for the Save menu item. */
	private static final String SAVE_FILE_TITLE = "Save";
	/** Text for the Quit menu item. */
	private static final String QUIT_TITLE = "Quit";
	/** Menu bar for the GUI that contains Menus. */
	private JMenuBar menuBar;
	/** Menu for the GUI. */
	private JMenu menu;
	/** Menu item for creating a new list of Races. */
	private JMenuItem itemNewFile;
	/** Menu item for loading a file. */
	private JMenuItem itemLoadFile;
	/** Menu item for saving the list to a file. */
	private JMenuItem itemSaveFile;
	/** Menu item for quitting the program. */
	private JMenuItem itemQuit;
	/** Add Race button */
	private JButton addRace;
	/** Race Name Input */
	private JTextField raceNameInput;
	/** Race Distance Input */
	private JTextField raceDisanceInput;
	/** Race Date Input */
	private JTextField raceDateInput;
	/** Race Location Input */
	private JTextField raceLocationInput;
	/** List Input */
	private JList<String> list;
	/** Model for results */
	private DefaultListModel<String> model;
	/** Remove Race button */
	private JButton removeRace;
	/** Edit Race Button */
	private JButton editRace;
	/** UnSelect Race Button */
	private JButton unselectRace;
	/** Filter Button */
	private JButton filterButton;
	/** runner Name text field */
	private JTextField runnerName;
	/** Runner Age text field */
	private JTextField runnerAge;
	/** Runner Time text field */
	private JTextField runnerTime;
	/** Model Result */
	private DefaultTableModel modelResult;
	/** add Result button */
	private JButton addResult;
	/** age min jlabel */
	private JLabel ageMin;
	/** age max jlabel */
	private JLabel ageMax;
	/** pace min jlabel */
	private JLabel paceMin;
	/** pace max jlabel */
	private JLabel paceMax;
	/** age max input text field */
	private JTextField ageMaxInput;
	/** age min input text field */
	private JTextField ageMinInput;
	/** pace min input text field */
	private JTextField paceMinInput;
	/** pace max input text field */
	private JTextField paceMaxInput;

	/**
	 * Constructs the GUI. Sets size of GUI location and title. Initializes rest of
	 * the GUI also.
	 */
	public WolfResultsGUI() {
		super();

		// Observe Manager
		// WolfResultsManager.getInstance().addObserver(this);

		// Set up general GUI info
		setSize(1500, 500);
		setLocation(50, 50);
		setTitle(APP_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUpMenuBar();

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				doExit();
			}

		});

		initializeGUI();

		// Set the GUI visible
		setVisible(true);
	}

	/**
	 * Initializes GUI by creating a JPanel and adding the Race panel and Result
	 * Panel to it. Additionally adds a container to it.
	 */
	private void initializeGUI() {

		JPanel panel = new JPanel();
		panel.setSize(new Dimension(400, 40));

		RacePanel racePanel = new RacePanel();
		ResultPanel resultPanel = new ResultPanel();

		panel.setLayout(new GridLayout(0, 2));
		panel.add(racePanel);
		panel.add(resultPanel);

		Container container = this.getContentPane();
		container.add(panel);

	}

	/**
	 * 
	 * Race Panel responsible for drawing the left side of the GUI. Action Performed
	 * additionally controls what happens with the races.
	 * 
	 * @author Shawn George
	 * @author Justin Easow
	 *
	 */
	private class RacePanel extends JPanel implements ActionListener {

		/**
		 * SerialVersion UID for this class
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor for Race Panel. Creates multiple panels for Left side. Race Panel
		 * buttons, Race Panel scrollPane, Filter panel, and Filter buttons.
		 */
		public RacePanel() {

			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(0, 1));
			mainPanel.setPreferredSize(new Dimension(641, 450));

			// Races
			JPanel racePanel = new JPanel(new GridLayout(0, 2));
			// added race panel to main panel
			mainPanel.add(racePanel);
			// adds border to race panel
			Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder boarder = BorderFactory.createTitledBorder(lowerEtched, "Race Information");
			racePanel.setBorder(boarder);
			racePanel.setToolTipText("Directory Buttons");

			// Race List
			mainPanel.setLayout(new GridLayout(0, 1));
			model = new DefaultListModel<>();
			list = new JList<String>(model);
			list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

			racePanel.add(new JScrollPane(list));

			// race buttons
			JPanel raceButtonPanel = new JPanel(new GridLayout(0, 1));
			raceButtonPanel.setPreferredSize(new Dimension(300, 100));
			addRace = new JButton("Add Race");
			addRace.addActionListener(this);
			removeRace = new JButton("Remove Race");
			removeRace.addActionListener(this);
			editRace = new JButton("Edit Race");
			editRace.addActionListener(this);
			unselectRace = new JButton("Unselect Race");
			unselectRace.addActionListener(this);

			// adds buttons to race panel
			racePanel.add(raceButtonPanel);

			raceButtonPanel.add(addRace);
			raceButtonPanel.add(removeRace);
			raceButtonPanel.add(editRace);
			raceButtonPanel.add(unselectRace);

			// Race Details
			JPanel raceDetails = new JPanel();
			raceDetails.setLayout(new GridLayout(0, 2));

			// Race Details Border
			Border lowE = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder board = BorderFactory.createTitledBorder(lowE, "Race Details");
			raceDetails.setBorder(board);
			raceDetails.setToolTipText("Race Details");
			mainPanel.add(raceDetails);

			// Race Labels
			JPanel raceLabels = new JPanel(new GridLayout(0, 1));
			JLabel raceName = new JLabel("Race Name");
			JLabel raceDistance = new JLabel("Race Distance");
			JLabel raceDate = new JLabel("Race Date");
			JLabel raceLocation = new JLabel("Race Location");
			raceLabels.add(raceName);
			raceLabels.add(raceDistance);
			raceLabels.add(raceDate);
			raceLabels.add(raceLocation);
			raceDetails.add(raceLabels);

			// Race Input
			JPanel raceInputs = new JPanel(new GridLayout(0, 1));
			raceNameInput = new JTextField();
			raceNameInput.addActionListener(this);
			raceDisanceInput = new JTextField();
			raceDisanceInput.addActionListener(this);
			raceDateInput = new JTextField();
			raceDateInput.addActionListener(this);
			raceLocationInput = new JTextField();
			raceLocationInput.addActionListener(this);
			raceInputs.add(raceNameInput);
			raceInputs.add(raceDisanceInput);
			raceInputs.add(raceDateInput);
			raceInputs.add(raceLocationInput);

			// add race inputs to race details
			raceDetails.add(raceInputs);
			mainPanel.add(raceDetails);

			// filter results panel
			JPanel filterResult = new JPanel(new GridLayout(0, 2));
			Border lowEd = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder boardd = BorderFactory.createTitledBorder(lowEd, "Filter Race Results");
			filterResult.setBorder(boardd);
			filterResult.setToolTipText("Filter Race Results");

			// Filter Labels
			JPanel filterLables = new JPanel(new GridLayout(0, 1));
			ageMin = new JLabel("Age Min");
			ageMax = new JLabel("Age Max");
			paceMin = new JLabel("Pace Min");
			paceMax = new JLabel("Pace Max");
			filterLables.add(ageMin);
			filterLables.add(ageMax);
			filterLables.add(paceMin);
			filterLables.add(paceMax);
			filterResult.add(filterLables);

			// Filter Labels input
			// Race Input
			JPanel filterInputs = new JPanel(new GridLayout(0, 1));
			ageMaxInput = new JTextField();
			ageMaxInput.addActionListener(this);
			ageMinInput = new JTextField();
			ageMinInput.addActionListener(this);
			paceMinInput = new JTextField();
			paceMinInput.addActionListener(this);
			paceMaxInput = new JTextField();
			paceMaxInput.addActionListener(this);

			filterInputs.add(ageMaxInput);
			filterInputs.add(ageMinInput);
			filterInputs.add(paceMinInput);
			filterInputs.add(paceMaxInput);

			// add filter label input to filter panel
			filterResult.add(filterInputs);

			// add a filter button to panel
			filterButton = new JButton("Filter");
			filterButton.addActionListener(this);
			filterButton.setPreferredSize(new Dimension(100, 100));

			filterResult.add(filterButton);

			// add filterReslults to main frame
			mainPanel.add(filterResult);

			// race lister

			add(mainPanel);

		}

		/**
		 * Controls the actions performed on the panel. Throws Exceptions to the GUI if
		 * Races have issues.
		 * 
		 * @param e
		 *            action controller for panel
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			WolfResultsManager manager = WolfResultsManager.getInstance();
			RaceList raceList = manager.getRaceList();
			String name = "";

			Double distance = 0.0;
			LocalDate date = null;
			String location = "";

			// add race button
			if (e.getSource() == addRace) {
				Race race = null;
				try {
					name = raceNameInput.getText();
					if (name.isEmpty()) {
						JOptionPane.showMessageDialog(this, "Cannot add Race with empty name");
						return;
					}
					distance = Double.parseDouble(raceDisanceInput.getText());
					date = LocalDate.parse(raceDateInput.getText());
					location = raceLocationInput.getText();
					race = new Race(name, distance, date, location);

					raceList.addRace(race);
					model.addElement(race.toString());
				} catch (Exception m) {
					try {
						race = new Race(name, distance, date, location);
					} catch (Exception d) {
						JOptionPane.showMessageDialog(this, d.getMessage());
					}
				}
			}
			// remove race button
			if (e.getSource() == removeRace) {
				if (list.getSelectedIndex() < 0) {
					JOptionPane.showMessageDialog(this, "No race selected");
				}
				model.removeElementAt(list.getSelectedIndex());
				raceList.removeRace(list.getSelectedIndex() + 1);
			}
			// edit race button
			if (e.getSource() == editRace) {
				if (list.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(this, "No race selected");
				} else {
					Race race = raceList.getRace(list.getSelectedIndex());
					try {
						race.setDistance(Double.parseDouble(raceDisanceInput.getText()));
					} catch (Exception m) {
						JOptionPane.showMessageDialog(this, m.getMessage());
					}
					if (race != null) {
						model.setElementAt(race.toString(), list.getSelectedIndex());

					}
				}
			}

			// unselect race button
			if (e.getSource() == unselectRace) {
				list.clearSelection();
			}

			// filter button
			if (e.getSource() == filterButton) {
				if (!list.isSelectionEmpty()) {
					String minAge = ageMinInput.getText();
					String maxAge = ageMaxInput.getText();
					String minPace = paceMinInput.getText();
					String maxPace = paceMaxInput.getText();

					try {
						Race race = raceList.getRace(list.getSelectedIndex());
						RaceResultList resultList = race.filter(Integer.parseInt(minAge), Integer.parseInt(maxAge),
								minPace, maxPace);

						for (int i = 0; i < modelResult.getRowCount(); i++) {
							modelResult.removeRow(i);
						}
						String[][] array = resultList.getResultsAsArray();
						for (int i = 0; i < array.length; i++) {
							modelResult.addRow(array[i]);
						}
					} catch (Exception m) {
						JOptionPane.showMessageDialog(this, "Invalid input");
					}
				} else {
					JOptionPane.showMessageDialog(this, "No race selected");
				}
			}

		}

	}

	/**
	 * Result Panel responsible for right hand side of the panel which controls race
	 * results and manipulation of them.
	 * 
	 * @author shawngeorge
	 *
	 */
	private class ResultPanel extends JPanel implements ActionListener {

		/**
		 * Default Serialization for Version UID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructs a ResultPanel which creates multiple panels on the right side of
		 * program.
		 */
		public ResultPanel() {
			JPanel mainPanel = new JPanel(new GridLayout(3, 0));
			mainPanel.setPreferredSize(new Dimension(621, 450));
			Border lw = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder b = BorderFactory.createTitledBorder(lw, "Race Results");
			mainPanel.setBorder(b);
			mainPanel.setToolTipText("Race Results");

			// result table
			JTable table = new JTable();
			Object[] columns = { "Runner", "Age", "Time", "Pace" };
			modelResult = new DefaultTableModel();
			modelResult.setColumnIdentifiers(columns);

			table.setModel(modelResult);
			mainPanel.add(new JScrollPane(table));

			addResult = new JButton("Add");
			addResult.addActionListener(this);
			addResult.setPreferredSize(new Dimension(50, 10));

			mainPanel.add(addResult);

			// add result
			JPanel resultAdder = new JPanel(new GridLayout(0, 2));
			JPanel resultLabels = new JPanel(new GridLayout(0, 1));
			JPanel resultsInput = new JPanel(new GridLayout(0, 1));

			JLabel runnerNameLabel = new JLabel("Runner Name");
			JLabel runnerAgeLabel = new JLabel("Runner Age");
			JLabel runnerTimeLabel = new JLabel("Runner Time");
			resultLabels.add(runnerNameLabel);
			resultLabels.add(runnerAgeLabel);
			resultLabels.add(runnerTimeLabel);

			resultAdder.add(resultLabels);

			runnerName = new JTextField();
			runnerName.addActionListener(this);
			runnerAge = new JTextField();
			runnerAge.addActionListener(this);
			runnerTime = new JTextField();
			runnerTime.addActionListener(this);

			resultsInput.add(runnerName);
			resultsInput.add(runnerAge);
			resultsInput.add(runnerTime);

			resultAdder.add(resultsInput);

			mainPanel.add(resultAdder);

			add(mainPanel);

		}

		/**
		 * Responsible for the actions performed by the ResultPanel.
		 * 
		 * @param e
		 *            action event for result panel
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			WolfResultsManager manager = WolfResultsManager.getInstance();
			RaceList raceList = manager.getRaceList();

			list.addListSelectionListener(new ListSelectionListener() {
				/**
				 * To see if the user unclicks from a race, to a different race
				 * 
				 * @param eventSelect
				 *            A parameter representing a single ListSectionEvent
				 */
				@Override
				public void valueChanged(ListSelectionEvent eventSelect) {
					if (!eventSelect.getValueIsAdjusting()) {
						for (int i = 0; i < modelResult.getRowCount(); i++) {
							modelResult.removeRow(i);
						}
					}

					Race race = raceList.getRace(list.getSelectedIndex());
					RaceResultList resultList = race.getResults();
					String[][] array = resultList.getResultsAsArray();
					for (int j = 0; j < (array.length); j++) {
						modelResult.addRow(array[j]);
					}

				}
			});

			if (e.getSource() == addResult) {
				if (!list.isSelectionEmpty()) {
					Race race = raceList.getRace(list.getSelectedIndex());
					String runName = runnerName.getText();
					String runAge = runnerAge.getText();
					String runTime = runnerTime.getText();
					try {
						IndividualResult result = new IndividualResult(race, runName, Integer.parseInt(runAge),
								new RaceTime(runTime));
						race.addIndividualResult(result);
					} catch (Exception m) {
						JOptionPane.showMessageDialog(this, "Invalid input for result.");
					}

					for (int i = 0; i < modelResult.getRowCount(); i++) {
						modelResult.removeRow(i);
					}

					RaceResultList resultList = race.getResults();
					String[][] array = resultList.getResultsAsArray();
					for (int i = 0; i < array.length; i++) {
						modelResult.addRow(array[i]);
					}

				} else {
					JOptionPane.showMessageDialog(this, "No race selected");
				}

			}

		}

	}

	/**
	 * Makes the GUI Menu bar that contains options for loading a file containing
	 * issues or for quitting the application.
	 */
	private void setUpMenuBar() {
		// Construct Menu items
		menuBar = new JMenuBar();
		menu = new JMenu(FILE_MENU_TITLE);
		itemNewFile = new JMenuItem(NEW_FILE_TITLE);
		itemLoadFile = new JMenuItem(LOAD_FILE_TITLE);
		itemSaveFile = new JMenuItem(SAVE_FILE_TITLE);
		itemQuit = new JMenuItem(QUIT_TITLE);
		itemNewFile.addActionListener(this);
		itemLoadFile.addActionListener(this);
		itemSaveFile.addActionListener(this);
		itemQuit.addActionListener(this);

		// Start with save button disabled
		itemSaveFile.setEnabled(false);

		// Build Menu and add to GUI
		menu.add(itemNewFile);
		menu.add(itemLoadFile);
		menu.add(itemSaveFile);
		menu.add(itemQuit);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}

	/**
	 * Exits the GUI
	 */
	private void doExit() {
		if (WolfResultsManager.getInstance().isChanged()) {
			doSaveFile();
		}

		if (!WolfResultsManager.getInstance().isChanged()) {
			System.exit(NORMAL);
		} else { // Did NOT save when prompted to save
			JOptionPane.showMessageDialog(this,
					"Race Results changes have not been saved. " + "Your changes will not be saved.", "Saving Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Saves GUI to file
	 */
	private void doSaveFile() {
		try {
			WolfResultsManager instance = WolfResultsManager.getInstance();
			JFileChooser chooser = new JFileChooser("./");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Race Results files (md)", "md");
			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(false);
			if (instance.getFilename() != null) {
				chooser.setSelectedFile(new File(instance.getFilename()));
			}
			int returnVal = chooser.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile().getAbsolutePath();
				if (chooser.getSelectedFile().getName().trim().equals("")
						|| !chooser.getSelectedFile().getName().endsWith(".md")) {
					throw new IllegalArgumentException();
				}
				instance.setFilename(filename);
				instance.saveFile(filename);
			}
			itemLoadFile.setEnabled(true);
			itemNewFile.setEnabled(true);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, "File not saved.", "Saving Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Loads race results from file
	 */
	private void doLoadFile() {
		try {
			WolfResultsManager instance = WolfResultsManager.getInstance();
			JFileChooser chooser = new JFileChooser("./");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Race Results files (md)", "md");
			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(false);
			int returnVal = chooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				instance.loadFile(chooser.getSelectedFile().getAbsolutePath());
			}

			for (int i = 0; i < instance.getRaceList().size(); i++) {
				model.addElement(instance.getRaceList().getRace(i).toString());
			}
			list = new JList<String>(model);
			itemLoadFile.setEnabled(false);
			itemNewFile.setEnabled(false);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, "Error opening file.", "Opening Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof WolfResultsManager) {
			itemSaveFile.setEnabled(true);

			repaint();
			validate();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		WolfResultsManager instance = WolfResultsManager.getInstance();

		if (e.getSource() == itemNewFile) {
			doSaveFile();
			instance.newList();
		} else if (e.getSource() == itemLoadFile) {
			doLoadFile();
		} else if (e.getSource() == itemSaveFile) {
			doSaveFile();
		} else if (e.getSource() == itemQuit) {
			doExit();
		}
	}

	/**
	 * Starts the application
	 * 
	 * @param args
	 *            command line args
	 */
	public static void main(String[] args) {
		new WolfResultsGUI();
	}

}