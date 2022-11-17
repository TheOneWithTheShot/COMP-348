"""
@author: Xavier Guertin
Student Number: 40213525
Due Date: 24/11/2022
Class: client.py
"""

import socket
import os
import json
from tabulate import tabulate
import pandas as pd

# initialize boolean var used to check if program is still running
isProgramRunning = True


# start program loop
def startProgramLoop():
    while isProgramRunning:
        optionChoice = showOptionMenu()
        executeOption(optionChoice)


# Clears screen
def clearScreen():
    # Checks if operating system is windows or (linux or mac)
    if os.name == 'posix':
        os.system('clear')
    else:
        os.system('cls')
    return


# function to exit the program
def exitProgram():
    global isProgramRunning
    print('\nTerminating the client. Good bye.\nOnly the client is closing, the server will continue to run.')
    isProgramRunning = False


# Function to show the main menu of the program
def showOptionMenu():
    while True:
        clearScreen()
        print()
        print("Python DB Menu")
        print()
        print("1. Find customer")
        print("2. Add customer")
        print("3. Delete customer")
        print("4. Update customer age")
        print("5. Update customer address")
        print("6. Update customer phone")
        print("7. Print report")
        print("8. Exit")
        print()
        print("Select:")
        optionChoice = input("")

        # checks if option number is really a number
        if optionChoice.isnumeric():
            optionChoice = int(optionChoice)
        else:
            print("\nThe input should only be a number. Try again.")
            continue

        # checks if option number is correct
        if optionChoice < 1 or optionChoice > 8:
            print("\nThe input should only be between 1 and 8 inclusively. Try again.")
        else:
            return optionChoice


# Based on user's input, calls the appropriate method

def executeOption(optionChoice):
    if optionChoice == 1:
        option = findUser()
        submitRequestToDatabase(option)
    elif optionChoice == 2:
        option = addUser()
        submitRequestToDatabase(option)
    elif optionChoice == 3:
        option = deleteUser()
        submitRequestToDatabase(option)
    elif optionChoice == 4:
        option = updateUserAge()
        submitRequestToDatabase(option)
    elif optionChoice == 5:
        option = updateUserAddress()
        submitRequestToDatabase(option)
    elif optionChoice == 6:
        option = updateUserPhoneNb()
        submitRequestToDatabase(option)
    elif optionChoice == 7:
        createTableFromData(retrieveDataFromServer())
    elif optionChoice == 8:
        exitProgram()
    return


# finds user
def findUser():
    print('\n\n--------------------------')
    print('----- Find Customer ------')
    print('--------------------------')

    name = ''
    while name == '':
        name = input('Name: ')
        if name == '':
            print('Please provide a name.')
    return 'find|' + name


# adds a user to the server
def addUser():
    print('\n\n--------------------------')
    print('------ Add Customer ------')
    print('--------------------------')

    name = ''
    while name == '':
        name = input('Name: ')
        if name == '':
            print('Please provide a name.')
    age = ''
    while age == '':
        age = input('Age: ')
        if age == '' or not age.isnumeric:
            print('Please provide an age.')
    address = ''
    while address == '':
        address = input('Address: ')
        if name == '':
            print('Please provide an address.')

    phoneNb = input('Phone Number: ')

    return 'add|' + name + '|' + age + '|' + address + '|' + phoneNb


# deletes a user from the server
def deleteUser():
    print('\n\n--------------------------')
    print('---- Delete Customer -----')
    print('--------------------------')

    name = ''
    while name == '':
        name = input('Name: ')
        if name == '':
            print('Please provide a name.')

    return 'delete|' + name


# Update the age of a user
def updateUserAge():
    print('\n\n--------------------------')
    print('- Update Customer\'s Age -')
    print('--------------------------')

    name = ''
    while name == '':
        name = input('Name: ')
        if name == '':
            print('Please provide a name.')
    age = ''
    while age == '':
        age = input('Age: ')
        if age == '' or not age.isnumeric:
            print('Please provide an age.')

    return 'update|' + name + '|age|' + age


# Update user's address
def updateUserAddress():
    print('\n\n------------------------------')
    print('- Update Customer\'s Address -')
    print('------------------------------')

    name = ''
    while name == '':
        name = input('Name: ')
        if name == '':
            print('Please provide a name.')
    address = ''
    while address == '':
        address = input('Address: ')
        if address == '':
            print('Please provide an address.')

    return 'update|' + name + '|address|' + address


# Update user's phone number
def updateUserPhoneNb():
    print('\n\n-------------------------------')
    print('- Update Customer\'s Phone Nb -')
    print('-------------------------------')

    name = ''
    while name == '':
        name = input('Name: ')
        if name == '':
            print('Please provide a name.')
    phoneNb = input('Phone Number: ')

    return 'update|' + name + '|phone|' + phoneNb


def retrieveDataFromServer():
    # Create a socket object
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((socket.gethostname(), 9999))

    # send option
    s.send('printReport|'.encode('utf-8'))

    # JSON sent from server containing data for report
    receivedString = s.recv(1024).decode('utf-8')

    # deserializes the JSON sent from server to transform it back into an obj again.
    unpackedJSON = json.loads(receivedString)
    data = unpackedJSON["data"]
    # return the object data which is a dictionary containing the data for the report
    return data


# Transforms the dictionary containing the data for the report
# into a table with all the information. Uses pandas and tabulate tools.
def createTableFromData(dataDictionary):
    print("\n\nReport of datas on server")

    datas = transformDictionaryToList(dataDictionary)

    df = pd.DataFrame(datas)
    print(tabulate(df, headers=["Name", "Age", "Address", "Phone Number"], tablefmt='rounded_outline', showindex=False))
    input("\nPress Enter to go back to the main menu...\n")


# function used so that the table appears horizontally.
# Transforms the dictionary into a list but with the name
# of the customer as the first item in each list
# returns the list
def transformDictionaryToList(dataDictionary):
    # create a list from the customer's name
    listOfKeys = list(dataDictionary.keys())
    # create a list only from the values
    listOfValues = list(dataDictionary.values())
    # for loop to add the name of the customer to the first item of every list
    for x in range(len(listOfKeys)):
        listOfValues[x].insert(0, listOfKeys[x])

    return listOfValues


# Send a request to the database(server) which executes it
# Server sends back a response that will be printed on screen
def submitRequestToDatabase(requestedCommand):
    # Create a socket object
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((socket.gethostname(), 9999)) # Connect to port 9999

    # send option/request to server
    s.send(requestedCommand.encode('utf-8'))

    # result received from server
    resultFromServer = (s.recv(1024)).decode('utf-8')
    print(resultFromServer + '\n')
    input("Press Enter to go back to the main menu...")

    # close the connection
    s.close()


startProgramLoop()
