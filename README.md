# Scoptions
Scala properties

## Examples

#### Definition example
```scala
case class TestOptions() extends Scoptions {
  val host: Property = Property("host", "localhost")
  val port: Property = Property("port", "7345")
  val mode: Property = Property("mode", "production")
}
```
Defining `Property` in `Scoptions` automaticly wires them.

#### Applying arguments from command line
```scala
val testOptions = TestOptions()

testOptions.applyArguments(
  "host=newhost",
  "mode=test"
)
```
Result:
```
(mode -> test, host -> newhost, port -> 7345)
```

#### Using properties as common variables
```scala
val testOptions = TestOptions()
testOptions.host := "newhost" // Sets value instead of `=`
testOptions.host()            // Get value
testOptions.host              // toString-ed value
testOptions.host == "newhost" // true
testOptions.host == "dsfksdf" // false
testOptions.host != "newhost" // false
testOptions.host != "nzhfuid" // true
```

Result
