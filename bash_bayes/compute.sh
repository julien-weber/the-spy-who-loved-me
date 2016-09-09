#!/bin/bash

get_counts() {
    cat $@ | sort | uniq -c
}

get_counts *_hired_clean.txt > accepted.txt
get_counts *_rejected_clean.txt > rejected.txt
