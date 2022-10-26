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



int compareFileCount(const void *a, const void *b) {
	struct file_count *fcount_a = (struct file_count *)a;
	struct file_count *fcount_b = (struct file_count *)b;

	return((fcount_b->nbOfChanges)-(fcount_a->nbOfChanges));	
}


void generateReport(char *target) {

	allocatedSize = 32;

	fileCountArray = (struct file_count*)malloc(allocatedSize * sizeof(struct file_count));
	if (fileCountArray == NULL) {
		fprintf(stderr, "Malloc error for fileCountArray");
	}


	printf("The program will look for instances of the string:  %s\n\n", target);

	char cwd[1024];
	if(getcwd(cwd, sizeof(cwd)) != NULL)
		printf("Search begins in current folder: %s\n\n\n", cwd);
	
	printf("** Search Report **\n\n");

	printf("Updates		File Name\n");
 
	qsort(fileCountArray, nbFiles, sizeof(fileCountArray[0]), compareFileCount);	

	for (int i = 0; i < nbFiles; i++ ) {
		if(fileCountArray[i].nbOfChanges == 0) 
			break;
                printf("%d		%s\n", fileCountArray[ix].nbOfChanges, fileCountArray[ix].fileName + 2);
        }
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
