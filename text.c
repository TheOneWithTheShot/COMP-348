/**
* @file text.c
* @author Xavier Guertin
* Student ID: 40213525
* @date 8 october 2022
*
*
* the function replace() receives a filename and a target string
* it finds all the ocurrences of the string in the .txt file and
* capitalizes the desired string.
*/

#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>
#include "text.h"


//function that finds the target string in a file and converts it to uppercase
int replace(char *fileName, char *targetString) 
{
	int counter = 0;
	char lineBuffer[4096];


	FILE* fileIn = fopen(fileName, "r");
	FILE* fileOut = fopen(fileName, "r+");
	
	// case-insensitive search	
	char lineLow[4096];
	char targetStringLow[strlen(targetString)+1];
	strcpy(targetStringLow, targetString);
	stringToLowercase(targetStringLow);
	
	//lineBuffer replacement	
	char targetStringUp[strlen(targetString)+1];
	strcpy(targetStringUp, targetString);
	stringToUppercase(targetStringUp);

	//while loop that goes one line at a time
	while(fgets(lineBuffer, sizeof(lineBuffer), fileIn)) {
		char* next_targ;
		
		strcpy(lineLow, lineBuffer);
		stringToLowercase(lineLow);

		next_targ= strstr(lineLow, targetStringLow);
		while((next_targ != NULL))
		{
			int position = (int)(next_targ - lineLow);
		
			if(strncmp(&(lineBuffer[position]), targetStringUp, strlen(targetStringUp))){ //a word that is not entirely capitalized	
				
				memcpy(&(lineBuffer[position]), targetStringUp, strlen(targetStringUp)); //targetString to uppercase
				counter ++;
			}

			char *searchNext = next_targ + strlen(targetStringLow);
			next_targ= strstr(searchNext, targetStringLow);
		}	

		fputs(lineBuffer, fileOut);
	}

	fclose(fileIn);
	fclose(fileOut);
	return counter;
}

//function to convert a string into lowercase
void stringToLowercase(char* String)
{
	for (int i = 0; i < strlen(String); i++) {
		String[i] = tolower(String[i]);
	}
}
//function to convert a string into uppercase
void stringToUppercase(char* String)
{
	for (int i = 0; i < strlen(String); i++) {
		String[i] = toupper(String[i]);
	}
}

