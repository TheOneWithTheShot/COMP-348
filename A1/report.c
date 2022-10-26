/**
* @file report.c
* @author Xavier Guertin
* Student ID: 40213525
* @date 8 october 2022
*
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
	
	printf("The modifications have been succesfully done.\n\n\n");
	printf("-------------------------- Process Report -------------------------\n\n\n");

	printf("\tNb of updates		File modified (relative path)\n");
	printf("\t-----------------------------------------------------\n");
 
	qsort(fileCountArray, nbFiles, sizeof(fileCountArray[0]), compareFileCount); //sort

	//print the results for each txt file found and modified
	for (int i = 0; i < nbFiles; i++ ) {
		if(fileCountArray[i].nbOfChanges == 0) 
			break;
                printf("\t%d		        %s\n", fileCountArray[i].nbOfChanges, fileCountArray[i].fileName + 2);
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
