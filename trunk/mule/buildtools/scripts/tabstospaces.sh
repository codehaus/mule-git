#!/bin/sh

# Usage: tabstospaces.sh directory"

if [ $# -lt 1 ]
then
    echo "Usage: $0 directory"
    exit 1
fi

for file in `find $1 | grep -v \.svn`
do
    if [ -f $file ]
    then
        echo "Editing file $file"
        sed -e 's/\t/    /g' $file > $file.tmp
        mv -f $file.tmp $file
    fi
done
