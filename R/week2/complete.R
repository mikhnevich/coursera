complete <- function(directory, id = 1:332) {
    x <- data.frame(id, rep(0, length(id)))
    colnames(x) <- c("id", "nobs")
    
    i <- 1
    for (f in id) {
        fname <- paste(directory, sprintf("%03d.csv", f), sep = "/")
        data <- read.csv(fname)
        good <- complete.cases(data)
        x[i, 2] <- length(good[good == T])
        i <- i + 1
    }
    x
}