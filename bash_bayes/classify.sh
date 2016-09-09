#!/bin/bash

. commons.sh

if [[ $# -ne 1 ]]; then
    echo "Usage: $0 <cv_file>"
    exit 1
fi

{
    echo "<ACCEPT>"
    cat model_hired.txt
    echo "<REJECT>"
    cat model_rejected.txt
    echo "<INPUT>"
    get_contents $1 | input_cleanup
} | awk -f compute_log.awk
