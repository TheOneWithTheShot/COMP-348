/**
* @file traversal.c
* @author Xavier Guertin
* Student ID: 40213525
* @date 8 october 2022
*
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


void add_fname_to_fcount_array(char *s, int c) {
        if(nbFiles == allocatedSize) {
                struct file_count *tmp_fcount_array = realloc(fileCountArray, 2*allocatedSize*sizeof(struct file_count));

                if(tmp_fcount_array == NULL) {
                        fprintf(stderr, "Too many file counter structs, skipping %s", s);
                        return;
                }

                fileCountArray = tmp_fcount_array;
                allocatedSize *= 2;
        }

        fileCountArray[nbFiles].fileName = s;
        fileCountArray[nbFiles].nbOfChanges = c;
        nbFiles++;
}

void traverseDirectory(char *current, char *target) 
{
	DIR *dirp;
	struct dirent *dp;

	//Error checking and initializing dirp
	if(!(dirp = opendir(current))) {
		fprintf(stderr, "null directory error\n");
		return; 
	}

	// Iterating through the current directory, one file at a time
	do {
		if ((dp = readdir(dirp)) != NULL) {
			if((int)dp->d_type == DT_DIR) {
				// Ignoring hidden directories.
				if(dp->d_name[0] != '.'){
					char recurse_into[strlen(current)+strlen(dp->d_name)+2];
					sprintf(recurse_into, "%s/%s", current, dp->d_name);
					// recursive call
					traverseDirectory(recurse_into, target);
				}

			} else if ((int)dp->d_type == DT_REG) {
				if(!(strcmp(&(dp->d_name[strlen(dp->d_name) - 4]), ".txt"))) {
					//Found a .txt file, add to fcount array.
					char *file_loc = malloc(strlen(current)+strlen(dp->d_name)+2);
					if(file_loc == NULL) {
						fprintf(stderr, "Malloc error\n");
					}
					sprintf(file_loc, "%s/%s", current, dp->d_name);
					
					//Call to replace() which returns count of replacements.
					int count = replace(file_loc, target);
					add_fname_to_fcount_array(file_loc, count);	
				}
			} else {
				printf("ignoring unrecognized filetype for file %s", dp->d_name);
			}
		}
	} while (dp != NULL);
	
	closedir(dirp);
	return;
}
