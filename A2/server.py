"""
@author: Xavier Guertin
Student Number: 40213525
Due Date: 24/11/2022
Class: server.py
"""


import json
import socket
import extractTextFile


# Start server loop
def startServerLoop():
    # Create socket obj
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((socket.gethostname(), 9999))  # Connect to port 9999
    s.listen(5)

    # Infinite Loop to always have the server open
    while True:
        clientSocket, address = s.accept()
        msg = clientSocket.recv(1024)
        parsedCommand = (msg.decode('utf-8')).split('|')

        response = executeOption(parsedCommand)
        clientSocket.send(response.encode('utf-8'))
        clientSocket.close()


# Function that will call other functions to extract
# the data from the data.txt file and to convert it
# into a dictionary
def importDataOnDatabase():
    dataList = extractTextFile.importFile("data.txt")
    dataDict = convertListToDictionary(dataList)
    return dataDict


# Function that converts the data list into a dictionary
def convertListToDictionary(dataList):
    dataDict = {}
    for x in dataList:
        key = x[0]
        value = [x[1], x[2], x[3]]
        dataDict[key] = value
    return dataDict


# Based on the request from the client, the server will execute the request
# and send back a result to the client.
def executeOption(newRequest):
    if newRequest[0] == 'find':
        print('Finding the following user: ' + newRequest[1])
        result = findUser(newRequest[1])

    elif newRequest[0] == 'add':
        print('Executed the import of a new user: ' + newRequest[1])
        result = addUser(newRequest[1], newRequest[2], newRequest[3], newRequest[4])

    elif newRequest[0] == 'delete':
        print('Executed deletion of: ' + newRequest[1])
        result = deleteUser(newRequest[1])

    elif newRequest[0] == 'update':
        if newRequest[2] == 'age':
            print('Executed age update of: ' + newRequest[1])
            result = updateUserAge(newRequest[1], newRequest[3])
        elif newRequest[2] == 'address':
            print('Executed address update of: ' + newRequest[1])
            result = updateUserAddress(newRequest[1], newRequest[3])
        elif newRequest[2] == 'phone':
            print('Executed phone update of: ' + newRequest[1])
            result = updateUserPhone(newRequest[1], newRequest[3])
        else:
            print('Unknown option')
            result = 'Unknown Option'

    elif newRequest[0] == 'printReport':
        print('Executed printReport')
        # serializes data dictionary obj into a formatted str and sorts it
        result = json.dumps({'data': dataDictionary}, sort_keys=True)

    else:
        print('Unknown option')
        result = 'Unknown Option'

    return result


# find user
def findUser(name):
    if name in dataDictionary:
        query = dataDictionary[name]
        result = 'The user was found with the following information: \n' + name + ', ' + query[0] + ', ' + query[1] + \
                 ', ' + query[2]
    else:
        result = "The user was not found, try again."
    return result


# add user
def addUser(name, age, address, phoneNb):
    if name in dataDictionary:
        result = "The user already exists, try again."
    else:
        dataDictionary[name] = [age, address, phoneNb]
        result = name + ' has been added.'
    return result


# delete user
def deleteUser(name):
    global dataDictionary
    if name in dataDictionary:
        dataDictionary.pop(name, None)
        result = "User was deleted."
    else:
        result = "The user was not found, try again."
    return result


# update the user's age
def updateUserAge(name, age):
    global dataDictionary
    if name in dataDictionary:
        origEntry = dataDictionary[name]
        newEntry = [age, origEntry[1], origEntry[2]]
        dataDictionary[name] = newEntry
        result = name + "'s age was updated."
    else:
        result = "The user was not found, try again."
    return result


# update the user's address
def updateUserAddress(name, address):
    global dataDictionary
    if name in dataDictionary:
        origEntry = dataDictionary[name]
        newEntry = [origEntry[0], address, origEntry[2]]
        dataDictionary[name] = newEntry
        result = name + "'s address was updated."
    else:
        result = "The user was not found, try again."
    return result


# update the user's phone number
def updateUserPhone(name, phoneNb):
    global dataDictionary
    if name in dataDictionary:
        origEntry = dataDictionary[name]
        newEntry = [origEntry[0], origEntry[1], phoneNb]
        dataDictionary[name] = newEntry
        result = name + "'s phone number was updated."
    else:
        result = "The user was not found, try again."
    return result


dataDictionary = importDataOnDatabase()
startServerLoop()
