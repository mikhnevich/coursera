best <- function(state, outcome) {
  data <- read.csv("outcome-of-care-measures.csv", colClasses = "character") 
  
  if (!(state %in% data[,7])) stop("invalid state")
  
  supportedOutcomes <- c("heart attack", "heart failure", "pneumonia")
  if (!(outcome %in% supportedOutcomes)) stop("invalid outcome")

  supportedOutcomesIndices <- c(11, 17, 23)
  data[, 11] <- as.numeric(data[, 11]) 
  data[, 17] <- as.numeric(data[, 17]) 
  data[, 23] <- as.numeric(data[, 23]) 

  dataIndex <- supportedOutcomesIndices[match(outcome, supportedOutcomes)]
  hospitals <- data[data$State == state & !is.na(data[dataIndex]), c(2,dataIndex)]
  minRate = min(hospitals[,2])
  minHospitals = hospitals[hospitals[2] == minRate]
  ordered <- minHospitals[order(2)]
  head(ordered, 1)[1]
}