rankall <- function(outcome, num = "best") {
  source("rankhospital.R")
  data <- read.csv("outcome-of-care-measures.csv", colClasses = "character") 
  states <- unique(data[,7])
  states <- states[order(states)]
  ra <- mapply(rankhospital, states, outcome, num)
  df <- data.frame(ra)
  df <- cbind(df, names(ra))
  colnames(df) <- c("hospital", "state")
  df
}