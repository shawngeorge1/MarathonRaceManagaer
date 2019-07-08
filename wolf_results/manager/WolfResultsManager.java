package edu.ncsu.csc216.wolf_results.manager;

import java.util.Observable;
import java.util.Observer;

import edu.ncsu.csc216.wolf_results.model.io.WolfResultsReader;
import edu.ncsu.csc216.wolf_results.model.io.WolfResultsWriter;
import edu.ncsu.csc216.wolf_results.race_results.RaceList;

/**
 * WolfResultsManager Maintains the data structures for the entire application.
 * 
 * @author Justin Easow
 * @author Shawn George
 *
 */
public class WolfResultsManager extends Observable implements Observer {

	/** The name of the file being worked with, as a String */
	private String filename;
	/** A variable indicating whether or the object has been changed */
	private boolean changed;

	private RaceList list;
	private static WolfResultsManager instance;

	/**
	 * A getter method that returns a single instance of WolfResultsManager
	 * 
	 * @return an instance of WolfResultsManager
	 */
	public static WolfResultsManager getInstance() {
		if (instance == null) {
			instance = new WolfResultsManager();
		}
		return instance;
	}

	/**
	 * The constructor for the WolfResultsManger class that makes an empty RaceList,
	 * and registering as an Observer of the list
	 */
	private WolfResultsManager() {
		try {
			list = new RaceList();
			list.addObserver(this);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		setChanged();
		this.notifyObservers(this);

		list = new RaceList();
	}

	/**
	 * A method that creates new empty RaceList and registers the
	 * WolfResultsManager. It also notifies observers if no exception is thrown
	 */
	public void newList() {
		list = new RaceList();
		this.changed = false;
		list.addObserver(this);
		setChanged();
		this.notifyObservers(this);
	}

	/**
	 * Returns the value stored in the private variable 'changed
	 * 
	 * @return the variable changed, as a boolean
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * A getter method that returns the filename
	 * 
	 * @return the current filename, as a String
	 */
	public String getFilename() {
		return this.filename;
	}

	/**
	 * A simple setter method, to assign new contents to the filename variable
	 * 
	 * @param fn
	 *            -The new filename that is to be assigned, as a String
	 */
	public void setFilename(String fn) {
		if (fn == null || fn.equals("") || fn.equals(" "))
			throw new IllegalArgumentException("Invalid filename");
		String stringFilename = fn.trim();
		if (stringFilename == null || stringFilename.equals("")) {
			throw new IllegalArgumentException("Invalid filename");
		}
		this.filename = stringFilename;

		this.filename.equals(stringFilename);
	}

	/**
	 * A method that loads in the file that is to be used, and adds the
	 * WolfResultsManager as an observer to the RaceList
	 * 
	 * @param fn
	 *            -The filename that is to be loaded, as a String
	 */
	public void loadFile(String fn) {

		setFilename(fn);
		RaceList raceList = WolfResultsReader.readRaceListFile(this.filename);
		this.list = raceList;
		list.addObserver(this);
		this.changed = false;
		setChanged();
		this.notifyObservers(this);
	}

	/**
	 * A method that saves the file with the given filename
	 * 
	 * @param fn
	 *            -The filename that is to be saved, as a String
	 */
	public void saveFile(String fn) {
		setFilename(fn);
		WolfResultsWriter.writeRaceFile(this.filename, list);
	}

	/**
	 * A getter method that returns the RaceList
	 * 
	 * @return the current RaceList
	 */
	public RaceList getRaceList() {
		return list;
	}

	/**
	 * Called whenever WolfResultsManager changes, handles the updating
	 * 
	 * @param arg
	 *            -a parameter of type cookie
	 * @param o
	 *            -an instance of the Observable type
	 * 
	 */
	public void update(Observable o, Object arg) {

		this.changed = true;
		setChanged();
		this.notifyObservers(this);

	}
}


import random

def check(answers):
	if not answers['healthy']:
		if (answers['providers'][0]):
			if answers['in_network']:
				if answers['referrals']:
					return 'HMO'
				if answers['wider_network']:
					return 'PPO'
				return 'HMO'
			if answers['priorities'][0] == 'Ability':
				return 'PPO'
			elif answers['priorities'][0] == 'Low':
				return 'HDHP'
			return 'HMO'
		if answers['referrals']:
			return 'HMO'
		if answers['wider_network']:
			return 'PPO'
		return 'HMO'
	if (answers['providers'][0]):
		if answers['in_network']:
			if answers['referrals']:
				if (not (answers['kids'])) and (len(answers['spouse']) == 0 or (not answers['spouse']['tobacco'])):
					return 'HDHP'
				return 'HMO'
			if answers['wider_network']:
				return 'PPO'
			return 'HMO'
		if answers['priorities'][0] == 'Ability':
			return 'PPO'
		if answers['priorities'][0] == 'Low':
			return 'HDHP'
		return 'HMO'
	if answers['referrals']:
		if (not (answers['kids'])) and (len(answers['spouse']) == 0 or (not answers['spouse']['tobacco'])):
			return 'HDHP'
		return 'HMO'

	if answers['wider_network']:
		return 'PPO'
	if (not (answers['kids'])) and (len(answers['spouse']) == 0 or (not answers['spouse']['tobacco'])):
		return 'HDHP'

prio = ['Ability', 'Low']
training_data = []
for i in range(100):
	answers = {
		'healthy' : random.randint(0,1), 'providers':[random.randint(0,1)], 'in_network': random.randint(0,1), 'referrals' : random.randint(0,1), 'wider_network' : random.randint(0,1), 'priorities': [prio[random.randint(0,1)]], 'referrals': random.randint(0,1), 'kids' : random.randint(0,1), 'spouse': {'tobacco' : random.randint(0,1)}
			}
	label = check(answers)
	training_data.append([answers['healthy'], answers['providers'][0], answers['in_network'], answers['referrals'], answers['wider_network'], answers['priorities'][0], answers['referrals'], answers['kids'], answers['spouse']['tobacco'], label])


keys = ['healthy', 'providers', 'in_network', 'referrals', 'wider_network', 'priorities', 'referrals', 'kids', 'spouse smoker', 'label']


def is_numeric(value):
    return isinstance(value, int) or isinstance(value, float)


def class_counts(rows):
    counts = {}  
    for row in rows:
        label = row[-1]
        if label not in counts:
            counts[label] = 0
        counts[label] += 1
    return counts

class Question:
	def __init__(self, key, value):
		self.key = key
		self.value = value

	def match(self, other):
		val = other[self.key]
		if is_numeric(val):
			return val >= self.value
		else:
			return val == self.value
	
def partition(rows, question):
	true_rows, false_rows = [],[]
	for row in rows:
		if question.match(row):
			true_rows.append(row)
		else:
			false_rows.append(row)
	return true_rows,false_rows

def gini(rows):
	counts = class_counts(rows)
	impurity = 1
	for label in counts:
		prob_of_label = counts[label] / float(len(rows))
		impurity -= prob_of_label ** 2
	return impurity

def info_gain(left, right, current_uncertainty):
	p = float(len(left)) / (len(left) + len(right)) 
	return current_uncertainty - p * gini(left) - (1-p) * gini(right)

def find_best_split(rows):
	best_gain = 0
	best_question = None
	current_uncertainty = gini(rows)
	n_features = len(rows[0]) - 1

	for col in range(n_features):
		values = set([row[col]for row in rows])
		for val in values:
			question = Question(col, val)
			true_rows, false_rows = partition(rows, question)
		if len(true_rows) == 0 or len(false_rows) == 0:
			continue

		gain = info_gain(true_rows, false_rows, current_uncertainty)

		if gain >= best_gain:
			best_gain, best_question = gain, question
	return best_gain, best_question

class Leaf:
    def __init__(self, rows):
        self.predictions = class_counts(rows)

class Decision_Node:
    def __init__(self,
                 question,
                 true_branch,
                 false_branch):
        self.question = question
        self.true_branch = true_branch
        self.false_branch = false_branch

def build_tree(rows):
	gain, question = find_best_split(rows)
	if gain == 0:
		return Leaf(rows)

	true_rows, false_rows = partition(rows, question)
	true_branch = build_tree(true_rows)
	false_branch = build_tree(false_rows)

	return Decision_Node(question, true_branch, false_branch)

def print_tree(node, spacing=""):
    """World's most elegant tree printing function."""

    # Base case: we've reached a leaf
    if isinstance(node, Leaf):
        print (spacing + "Predict", node.predictions)
        return

    # Print the question at this node
    print (spacing + str(node.question))

    # Call this function recursively on the true branch
    print (spacing + '--> True:')
    print_tree(node.true_branch, spacing + "  ")

    # Call this function recursively on the false branch
    print (spacing + '--> False:')
    print_tree(node.false_branch, spacing + "  ")

def classify(row, node):
    if isinstance(node, Leaf):
        return node.predictions

    if node.question.match(row):
        return classify(row, node.true_branch)
    else:
        return classify(row, node.false_branch)


my_tree = build_tree(training_data)

def print_leaf(counts):
    total = sum(counts.values()) * 1.0
    probs = {}
    for lbl in counts.keys():
        probs[lbl] = str(int(counts[lbl] / total * 100)) + "%"
    return probs

print(print_leaf(classify([0, 1, 0, 1, 1, 'Low', 1, 0, 1], my_tree)))

		
		
		
		import requests as rq 
from pymongo import MongoClient
import helpers

#example request
# https://api.betterdoctor.com/2016-03-01/doctors?first_name=Renee&last_name=Johnson&location=nc-cary&gender=female&skip=0&limit=1&user_key=2ef422483448d1d77aa8dc1c18aa4f16



# This method pulls together the functionalities of the following to methods, getDoctor and isCovered, 
# providing a single method call to incorporate information on if requested doctors are covered or not 
# by the plans that will be presented
# param personData: This is the json sent with information about the user. Contains list of doctors and their names. 
# param responseInfo: This is a json object with the plans that will be presented to the user. The information about 
# if doctors are covered is added.
# param db: This is the db, which is used to get zipcode information
def getDoctorsAreCovered(personData, responseInfo, db):
    doctors = []
    for provider in personData['providers']:
        doctors.append(getDoctor(provider['first'], provider['last'], personData['zip'], db, 1))
    responseInfo['plans'] = isCovered(doctors, responseInfo['plans'])

    return responseInfo


# This gets a specified amount of doctors by pinging the betterDoctor API
# param fName: This is the first name of the doctor being searched for
# param lName: This is the last name of the doctor being searched for
# param zip: This is an int zipcode of the user using the front end
# param limit: This is the max number of doctors you want returned by the search
# return a list of doctors that fit the specified criteria
def getDoctor(fName, lName, zip, db, limit):

    #the end of the api url regardless of parameters. Has user code, number of wanted results and number to be skipped
    skipAndLimit = '&skip=0&limit=1'
    
    apiKey = '&user_key=2ef422483448d1d77aa8dc1c18aa4f16'

    apiURL = 'https://api.betterdoctor.com/2016-03-01/doctors?'

    first_name = 'first_name=' + fName
    last_name = 'last_name=' + lName

   
    zipInfo = db['zip'].find_one({"zip": zip})
    loc_slug = zipInfo['state_id'] + '-' + zipInfo['city']
    loc_slug = loc_slug.lower()

    location = 'location=' + loc_slug

    requestURL = apiURL + first_name + '&' + last_name + '&' + location + skipAndLimit + str(limit) + apiKey
    response = rq.get(requestURL).json()
    
    #print(response)
    ''' 
    if len(response['data']) == 0 \ 
        or (response['data'][0]['profile']['first_name'] != fName \
            & response['data'][0]['profile']['last_name']) != lName
        response['exists'] = False
        response['first_name'] = fName
        response['last_name'] = lName
    '''

    return response

# take in an array of the wanted doctors and see which plans they are covered by
# Cross checks the plans the doctors takes with the name of the provider and the type of the plans in the plans object
def isCovered(doctors, plans):

    for plan in plans:
        plan['Doctors'] = {}
        for doctor in doctors:
            doctorName = doctor['data'][0]['profile']['first_name'] + ' ' + doctor['data'][0]['profile']['last_name']
            plan['Doctors'][doctorName] = 'Not Covered'

 
 #Checking all the insurances with the plans to see if each doctor is covered
    for doctor in doctors:
        doctorName = doctor['data'][0]['profile']['first_name'] + ' ' + doctor['data'][0]['profile']['last_name']
        for insurance in doctor['data'][0]['insurances']:
            for plan in plans:
                if insurance['insurance_plan']['name'].find(plan['Plan Type']) > -1 \
                & plan['Issuer Name'].find(insurance['insurance_provider']['name']) > -1:
                    plan['Doctors'][doctorName] = 'Covered'
                    print(plan['Issuer Name'])
    return plans

'''
client = MongoClient('localhost', 27017)
db = client['admin']
db.authenticate('admin', 'VDXmqmjt50PCbY0E')
doctors = [getDoctors('Renee', 'Johnson', 27519, db), getDoctors('Bindit', 'Patel', 27519, db)]
plans = helpers.get_plans('HMO', 'Bronze', '27519', client)
result = isCovered(doctors, plans)
print(result)
