/**
* @file replace.h
* @author Xavier Guertin
* Student ID: 40213525
* @date 8 october 2022
*
*
* replace.c's header file which is used to declare the file_count struct in replace.c
* file_count is used to record the nb of change made to each file 
* and to save the filenames in the traversal.
*/


#ifndef REPLACE_H_ 
#define REPLACE_H_

// A struct to hold a filename (with relative location)
// and a count of nbOfChanges made by replace() in text.c to each file
struct file_count
{
    int nbOfChanges;
    char *fileName;
};


extern struct file_count *fileCountArray;

extern int nbFiles; 
extern int allocatedSize;


#endif /* RESULTS_H_ */
