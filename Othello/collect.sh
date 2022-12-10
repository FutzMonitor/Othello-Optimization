#!/bin/bash
# data collection for Othello optimization

for i in {1..2000}
do
    java Othello -nw RandomOthelloPlayer ABOthelloPlayer
done

for i in {1..2000}
do
    java Othello -nw ABOthelloPlayer RandomOthelloPlayer
done

echo "done."
