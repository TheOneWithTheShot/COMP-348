/**
* @file replace.c
* @author Xavier Guertin
* Student ID: 40213525
* @date 8 october 2022
*
* 
* file that contains the main method aswell as the 
* methods to handle the command line arguments
*/ 

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "replace.h"
#include "traversal.h"
#include "text.h"
#include "report.h"

/**
*	Point entry of program - Main
*	@param argc argument count, value should be 2 for normal exec.
*	@param argv Program arguments, the second argument is the target string
*/
int main(int argc, char* argv[])
{
	//checks number of argument, if doesn't satisfies, it exits
	if (argc != 2) {
		printf("You must provide only ONE search argument.\nThe process is exiting now. Please try again.");
		return EXIT_FAILURE;
	}
	
	char* target = argv[1]; //target string
	
	printf("------------- Welcome to the Word Capitalizer Program -------------\n\n");
	printf("This program will convert the provided word in capital letter\n of every .txt file in the directory and subfolders of the program\n\n");

	
	traverseDirectory(".", target); //calls traverseDirectory

	generateReport(target); //generates report
	
	freeReport();  //free memory

	return 0;
}


