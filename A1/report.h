/**
* File: report.h
* Name: Xavier Guertin
* Student ID: 40213525
* 
* report.c's header file
*/


#ifndef REPORT_H_ 
#define REPORT_H_

//struct that holds 2 variables, nbOfChanges & fileName
struct file_count
{
    int nbOfChanges; //counter of nbOfChanges made by replace() from text.c to each file
    char* fileName; //filename with relative location
};


extern struct file_count* fileCountArray;

extern int nbFiles;
extern int allocatedSize;


void generateReport(char *targetString);

void freeReport();

#endif /* REPORT_H_ */
