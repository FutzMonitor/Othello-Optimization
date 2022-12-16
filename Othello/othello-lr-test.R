d <- read.csv("/Users/hailiemitchell/Documents/Dickinson/2022 Fall/COMP 364/Othello-Optimization/Othello/data-collection/d-25.txt")
plot(v ~ v_prime, data=d)
d_lm <- lm(v ~ v_prime, data = d)
summary(d_lm)

# We edited and ran this program to run linear regressions for all of our data
# we generated linear regressions for each possible number of pieces on the board,
#   from 4 pieces (starting board) to 56 pieces (after which it cannot go 
#   eight layers deeper)