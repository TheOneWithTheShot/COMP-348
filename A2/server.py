import socket
import readDataFile

def startServerLoop():
    # Create socket obj
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((socket.getsockname(), 9999)) # Connect to port 9999
    s.listen(5)

    # Infinite Loop to always have the server open
    while True:
        clientSocket, address = s.accept()
        print(f"connection from {address} has been established!")
        clientSocket.send(bytes("Welcome to the server!", "utf-8"))

        msg = clientSocket.recv(1024)
        parsedCommand = (msg.decode('utf-8')).split('|')

        response = processCommand(parsedCommand)
        clientSocket.send(response.encode('utf-8'))
        clientSocket.close()

def importDataOnDatabase():
    dbList = readDataFile.loadFile("data.txt")
    dbDictionary = readDataFile.processListToDict(dbList)
    return dbDictionary


def processCommand(newCommand):
    UNRECONGIZED_COMMAND = 'Unrecognized Command'
    response = ''

    if len(newCommand) == 2:  # it's either find, delete or getAllData
        if newCommand[0] == 'find':
            print(str(time.ctime()) + ' - ' + 'Find command')
            foundEntry = find(newCommand[1])
            if foundEntry == None:
                response = 'Server response: ' + newCommand[1] + ' not found in database'
            else:
                response = 'Server response: ' + foundEntry[0] + '|' + foundEntry[1] + '|' + foundEntry[2] + '|' + \
                           foundEntry[3]

        elif newCommand[0] == 'delete':
            print(str(time.ctime()) + ' - ' + 'Delete command')
            response = delete(newCommand[1])

        elif newCommand[0] == 'getAllData':
            print(str(time.ctime()) + ' - ' + 'GetAllData command')
            response = json.dumps({'data': dbase_asDict})
            # response = jsonPackage.encode('utf-8')

        else:
            print(str(time.ctime()) + ' - ' + 'Unknown command')
            response = UNRECONGIZED_COMMAND

    elif len(newCommand) == 3:
        print(str(time.ctime()) + ' - ' + 'Unknown command')
        response = UNRECONGIZED_COMMAND

    elif len(newCommand) == 4:

        if newCommand[0] == 'update':
            if (newCommand[2] == 'age'):
                print(str(time.ctime()) + ' - ' + 'Update age command')
                response = updateAge(newCommand[1], newCommand[3])
            elif (newCommand[2] == 'address'):
                print(str(time.ctime()) + ' - ' + 'Update address command')
                response = updateAddress(newCommand[1], newCommand[3])
            elif (newCommand[2] == 'phone'):
                print(str(time.ctime()) + ' - ' + 'Update phone number command')
                response = updatePhone(newCommand[1], newCommand[3])
            else:
                print(str(time.ctime()) + ' - ' + 'Unknown command')
                response = UNRECONGIZED_COMMAND

        else:
            print(str(time.ctime()) + ' - ' + 'Unknown command')
            response = UNRECONGIZED_COMMAND

    elif len(newCommand) == 5:

        if newCommand[0] == 'add':
            print(str(time.ctime()) + ' - ' + 'Add new customer command')
            response = add(newCommand[1], newCommand[2], newCommand[3], newCommand[4])

        else:
            print(str(time.ctime()) + ' - ' + 'Unknown command')
            response = UNRECONGIZED_COMMAND

    else:
        print(str(time.ctime()) + ' - ' + 'Unknown command')
        response = UNRECONGIZED_COMMAND

    return response


def find(name):
    if name in dbase_asDict:
        queryDbase = dbase_asDict[name]
        result = [name, queryDbase[0], queryDbase[1], queryDbase[2]]
    else:
        result = None
    return result


def add(name, age, address, phoneNumber):
    global dbase_asDict
    if name in dbase_asDict:
        result = 'Customer already exists.'
    else:
        dbase_asDict[name] = [age, address, phoneNumber]
        result = 'Customer has been added!'
    return result


def delete(name):
    global dbase_asDict
    if name in dbase_asDict:
        dbase_asDict.pop(name, None)
        result = "Customer was removed!"
    else:
        result = "Customer not found."
    return result


def updateAge(name, age):
    global dbase_asDict
    if name in dbase_asDict:
        origEntry = dbase_asDict[name]
        newEntry = [age, origEntry[1], origEntry[2]]
        dbase_asDict[name] = newEntry
        result = "Customer age was updated!"
    else:
        result = "Customer not found."
    return result


def updateAddress(name, address):
    global dbase_asDict
    if name in dbase_asDict:
        origEntry = dbase_asDict[name]
        newEntry = [origEntry[0], address, origEntry[2]]
        dbase_asDict[name] = newEntry
        result = "Customer\'s address was updated!"
    else:
        result = "Customer not found."
    return result


def updatePhone(name, phone):
    global dbase_asDict
    if name in dbase_asDict:
        origEntry = dbase_asDict[name]
        newEntry = [origEntry[0], origEntry[1], phone]
        dbase_asDict[name] = newEntry
        result = "Customer\'s phone number was updated!"
    else:
        result = "Customer not found."
    return result


dbDictionary = importDataOnDatabase()
startServerLoop()