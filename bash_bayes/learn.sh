#!/bin/bash

. commons.sh

path=./learn

echo "Cleaning up learning dataset..."
for txt in $path/*_raw.txt; do
    output="$path/$(basename $txt _raw.txt)_clean.txt"
    if [[ -f "$output" ]]; then
        continue
    fi

    cat $txt | input_cleanup | tee "$output" > /dev/null
done

echo "Generating model..."
get_counts $path/*_hired_clean.txt > model_hired.txt
get_counts $path/*_rejected_clean.txt > model_rejected.txt

echo "Done"
