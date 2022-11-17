"""
@author: Xavier Guertin
Student Number: 40213525
Due Date: 24/11/2022
Class: extractTextFile.py
"""


# Function that receives a filename.
# Opens the file and converts all the text into a list
def importFile(filename):
    dataList = []
    with open(filename, "r") as file:
        print(filename + ' is currently opened.')
        print('Reading process has begun.')

        everyLinesInsideFile = file.readlines()

        # for loop that goes through every line to check if it is correct
        for oneLine in everyLinesInsideFile:

            # remove all newlines at the end of the string
            oneLine = oneLine.rstrip('\n')

            # separates values separated by |
            tokenized = oneLine.split('|')

            # remove exceeding spaces between items
            for item in tokenized:
                item.strip()

            # checks if the name is valid or is there
            if tokenized[0] == '' or tokenized[0].isnumeric():
                print("The name of the following entry is not valid or is missing. Ignoring it.")
                print("Invalid entry:" + oneLine)
                continue

            # exception handler that checks if the age is numeric.
            try:
                age = int(tokenized[1])
            except ValueError:
                print("The age of the following entry is not a number. Ignoring it.")
                print("Invalid entry:" + oneLine)
                continue

            # checks if the age is valid
            if age < 0:
                print("The age of the following entry is not a valid number. Ignoring it.")
                print("Invalid entry:" + oneLine)
                continue

            # checks if there are too many entries
            if len(tokenized) > 4:
                print("T following entry has too many items. Ignoring it.")
                print("Invalid entry:" + oneLine)
                continue

            # If no errors were found, appends the items to the list.
            dataList.append(tokenized)
    return dataList
