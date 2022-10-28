/**
* File: report.c
* Name: Xavier Guertin
* Student ID: 40213525
* 
* file that creates the report. There are 3 methods.
* 1. compareFileCount : compares the file_count structs
* 2. generateReport   : prints the report
* 3. freeReport       : free the memory
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "report.h"


//initialize variables
int nbFiles = 0;
int allocatedSize = 0;
struct file_count* fileCountArray = NULL;



//compares the file_count structs
int compareFileCount(const void *a, const void *b) {
	struct file_count *fcount_a = (struct file_count *)a;
	struct file_count *fcount_b = (struct file_count *)b;

	return((fcount_b->nbOfChanges)-(fcount_a->nbOfChanges));	
}

//generates the report
void generateReport(char *targetString) {

	printf("The program will look for instances of the string:  %s\n\n", targetString);

	char cwd[1024];
	if(getcwd(cwd, sizeof(cwd)) != NULL)
		printf("The search will start from the following folder: %s\n\n\n", cwd);
	
	printf("The process has finished.\n\n\n");
	printf("-------------------------- Process Report -------------------------\n\n\n");
 
	qsort(fileCountArray, nbFiles, sizeof(fileCountArray[0]), compareFileCount); //sort

	//Check if at least one file has been modified
	int checkIfNoModification = 0;
	for (int i = 0; i < nbFiles; i++) {
		checkIfNoModification += fileCountArray[i].nbOfChanges;
	}

	if (checkIfNoModification == 0) {
		printf("No files have been changed. The reason could be that there was no occurence\n");
		printf("of the target string or that the word was already capitalized in every.txt file.\n\n\n");
	} else {

		printf("\tNb of updates		File modified (relative path)\n");
		printf("\t-----------------------------------------------------\n");

		//print the results for each txt file found and modified
		for (int i = 0; i < nbFiles; i++) {
			if (fileCountArray[i].nbOfChanges == 0)
				break;
			printf("\t%d		        %s\n", fileCountArray[i].nbOfChanges, fileCountArray[i].fileName + 2);
		}
	}


	printf("\n\nThe program has finished. Have a great day.\n\n");
}	

//free memory
void freeReport() {
	if (fileCountArray != NULL) {
		for (int i = 0; (i < nbFiles); i++) {
			free(fileCountArray[i].fileName);
		}
	free(fileCountArray);
	}
}
