#!/bin/bash
# clean data for linear regression

for i in {10..63}
do
     grep -v "2.147483647E9" d-$i.txt > tmpfile && mv tmpfile d-$i.txt
done

echo "done."
