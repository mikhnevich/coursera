rankhospital <- function(state, outcome, num = "best") {
  data <- read.csv("outcome-of-care-measures.csv", colClasses = "character") 
  
  if (!(state %in% data[,7])) stop("invalid state")
  
  supportedOutcomes <- c("heart attack", "heart failure", "pneumonia")
  if (!(outcome %in% supportedOutcomes)) stop("invalid outcome")
  rankToFind = num

  supportedOutcomesIndices <- c(11, 17, 23)
  data[, 11] <- as.numeric(data[, 11]) 
  data[, 17] <- as.numeric(data[, 17]) 
  data[, 23] <- as.numeric(data[, 23]) 

  dataIndex <- supportedOutcomesIndices[match(outcome, supportedOutcomes)]
  hospitals <- data[data$State == state & !is.na(data[dataIndex]), c(2,dataIndex)]
  ord <- order(hospitals[2], hospitals[1])
  ordered <- hospitals[ord, ]
  if (num =="best") {
    ordered[1,][[1]]
  } else if (num =="worst") {
    ordered[nrow(ordered),][[1]]
  } else if (num > nrow(ordered)) {
    NA
  } else {
    ordered[num,][[1]]
  }
  
}