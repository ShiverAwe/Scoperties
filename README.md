# Scoptions
Scala properties

## Examples

#### Definition example
```scala
case class TestOptions() extends Scoptions {
  val host: Property = PropertyS("host", "localhost")
  val port: Property = PropertyS("port", "7345")
  val mode: Property = PropertyS("mode", "production")
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
mode=test host=newhost port=7345 
```

#### Using properties as common variables
```scala
val testOptions = TestOptions()
testOptions.host.set("newhost")// Sets value instead of `=`
testOptions.host()             // Get value
```
