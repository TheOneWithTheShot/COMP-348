/**
* File: traversal.c
* Name: Xavier Guertin
* Student ID: 40213525
*
* file that contains the function traverseDirectory()
* It recurses through a directory and calls the replace function
* (in text.c) when it finds a .txt file
*/


#include <dirent.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "traversal.h"
#include "text.h"
#include "report.h"

void traverseDirectory(char *current, char *targetString) 
{
	DIR* d; 
	struct dirent *dp;

	//initiliaze d and make sure that there is no error
	if(!(d = opendir(current))) {
		fprintf(stderr, "invalid/null directory\n");
		return; 
	}

	// iterate through every files in the current directory
	do {
		if ((dp = readdir(d)) != NULL) {
			if((int)dp->d_type == DT_DIR) {
				// Ignores dot folders and executable
				if(dp->d_name[0] != '.'){
					char recurseIn[strlen(current)+strlen(dp->d_name)+2];
					sprintf(recurseIn, "%s/%s", current, dp->d_name);
					// calls the method recursively
					traverseDirectory(recurseIn, targetString);
				}

			} else if ((int)dp->d_type == DT_REG) {
				// .txt found and is added to fileCountArray
				if(!(strcmp(&(dp->d_name[strlen(dp->d_name) - 4]), ".txt"))) {
					char *filePath = malloc(strlen(current)+strlen(dp->d_name)+2);
					if(filePath == NULL) {
						fprintf(stderr, "Malloc error\n");
					}
					sprintf(filePath, "%s/%s", current, dp->d_name);
					
					//counter of replaced target string
					int counter = replace(filePath, targetString);
					addFileNameToFileCountArray(filePath, counter);	
				}
			} else {
				printf("ignoring unrecognized filetype for file %s", dp->d_name);
			}
		}
	} while (dp != NULL);
	
	closedir(d);
	return;
}

void addFileNameToFileCountArray(char* filePath, int counter) {
	if (nbFiles == allocatedSize) {
		struct file_count* tmp_fcount_array = realloc(fileCountArray, 2 * allocatedSize * sizeof(struct file_count));

		if (tmp_fcount_array == NULL) {
			fprintf(stderr, "Too many file counter structs, skipping %s", filePath);
			return;
		}

		fileCountArray = tmp_fcount_array;
		allocatedSize *= 2;
	}

	fileCountArray[nbFiles].fileName = filePath;
	fileCountArray[nbFiles].nbOfChanges = counter;
	nbFiles++;
}