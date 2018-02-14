# Scoptions
Scala application options

## Fast start

#### Introducing scoptions
Value properties 
```scala
case class TestOptions() extends Scoptions {
  val host: = PropertyS("host", "localhost")
  val port: = PropertyI("port", 7345)
  val length: = PropertyF("mode", 45.56)
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
