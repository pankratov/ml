# Base function and noise
noise <- 0.3; size <- 100
a <- 2; b <- 2.7
func <- function(X) {
  b * X + a
}

# Sampling
set.seed(2804)
X <- runif(size, max=2)
epsilon <- rnorm(size, sd = noise)
y <- func(X) + epsilon


# Drawing scatter and original line
par(mfrow=c(1,2)) 
plot(X, y, cex = .5)
abline(a, b, col = 'blue', lwd = 0.4, lty=2)


# Fitting regression
result <- lsfit(X, y)
print(result$coefficients)


# Drawing all togetger
plot(X, y, cex = .5)
abline(a, b, col = 'blue', lwd = 0.4, lty=2)
abline(result, col='red', lwd = 0.7)