#!/bin/bash

get_counts() {
    cat $@ | sort | uniq -c
}

input_cleanup() {
        # Drop diacritics and other useless chars
        iconv -f UTF-8 -t ASCII//TRANSLIT | \
        # Replace capital letters with lowercase ones
        tr '[:upper:]' '[:lower:]' | \
        # Replace punctuation/whitespaces with space
        tr '[:punct:][:blank:]' ' ' | \
        # Have a single word per line
        sed -re 's/\s+/\n/g' | \
        # Drop empty lines
        grep -v '^\s*$'
}

get_contents() {
    if [[ "$@" =~ .pdf$ ]]; then
        pdf2txt "$@"
    else
        cat "$@"
    fi
}
