import socket
import os
from time import sleep

# Create socket obj
# socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# socket.bind((socket.getsockname(), 9999)) # Connect to port 9999
# msg = socket.recv(1024)
# print(msg.decode("utf-8"))

# initialize boolean variable used to check if program is still running
isProgramRunning = True

# Clears screen
def clearScreen():
    # Checks if operating system is windows or (linux or mac)
    if os.name == 'posix':
        os.system('clear')
    else:
        os.system('cls')
    return


# Function to show the main menu of the program
def showOptionMenu():
    optionChoice = '' # initialize user's option choice

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
        optionChoice = input()

        # checks if option number is really a number
        if optionChoice.isnumeric():
            optionChoice = int(optionChoice)
        else:
            print("\nThe input should only be a number. Try again.")
            continue

        # checks if option number is correct
        if (optionChoice < 1 or optionChoice > 8):
            print("\nThe input should only be between 1 and 8 inclusively. Try again.")
        else:
            return optionChoice

def exitProgram():
    global isProgramRunning
    print ('\nTerminating the client. Good bye.')
    isProgramRunning = False

def executeOption(optionChoice):
    # command = ''
    # if optionChoice == 1:
    #     command = findCommand()
    #     sendRequest(command)
    # elif optionChoice == 2:
    #     command = addCommand()
    #     sendRequest(command)
    # elif optionChoice == 3:
    #     command = deleteCommand()
    #     sendRequest(command)
    # elif optionChoice == 4:
    #     command = updateAge()
    #     sendRequest(command)
    # elif optionChoice == 5:
    #     command = updateAddress()
    #     sendRequest(command)
    # elif optionChoice == 6:
    #     command = updatePhoneNr()
    #     sendRequest(command)
    # elif optionChoice == 7:
    #     dbase_asDict = getAllData()
    #     print(type(dbase_asDict))
    #     printCommand(dbase_asDict)
    #     command = 'user wants to print a report with all the entries'
    # elif optionChoice == 8:
    exitProgram()
    return

def startProgramLoop():
    while isProgramRunning:
        optionChoice = showOptionMenu()
        executeOption(optionChoice)

startProgramLoop()
