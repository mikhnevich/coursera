corr <- function(directory, threshold = 0) {
    x <- numeric()
    for (f in list.files(directory)) {
        fname <- paste(directory, f, sep = "/")
        data <- read.csv(fname)
        good <- data[complete.cases(data), ]
        if (nrow(good) > threshold) {
            y <- cor(good["sulfate"], good["nitrate"])
            x <- append(x, y)
        }
    }
    x
}