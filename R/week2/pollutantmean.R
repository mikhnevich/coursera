pollutantmean <- function(directory, pollutant, id = 1:332) {
    values <- numeric()
    pname <- pollutant[[1]]
    for (f in id) {
        fname <- paste(directory, sprintf("%03d.csv", f), sep = "/")
        data <- read.csv(fname)
        values <- append(values, data[[pname]])
    }
    mean(values, na.rm = T)
}
