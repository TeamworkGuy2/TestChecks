# Change Log
All notable changes to this project will be documented in this file.
This project does its best to adhere to [Semantic Versioning](http://semver.org/).


--------
### [0.3.1](N/A) - 2019-03-25
#### Changed
* Renamed `ObjectMethodTests` -> `ObjectEquality` and setup public static testEquality() method to test `equals()` and `hashCode()` object contracts


--------
### [0.3.0](https://github.com/TeamworkGuy2/TestChecks/commit/12fa4dd54bc0dbbdc8530ca4d1eb02683218f7bc) - 2018-03-25
#### Added
* `TestData.fromInputsAndExpected(Iterable, Iterable, BiFunction)` and overloads for converting sequences of test values into TestData objects

#### Changed
* Renamed package `twg2.testAssist.checks` -> `twg2.junitassist.checks`
* CheckTask `assertTests(Collection, Function)` -> `assertTests(Iterable, Function)`
* CheckCollections `assertLooseEqualsDepth2(Collection, Collection)` -> `assertLooseEqualsNested(Iterable, Iterable)`

#### Removed
* `CheckCollections.toList()`


--------
### [0.2.0](N/A) - 2016-09-18
#### Changed
* Renamed TestDataObj -> TestDataImpl
* Moved some of the Check methods into a new CheckArrays class

#### Fixed
* A array comparison bug in CheckTask.assertTests()


--------
### [0.1.0](N/A) - 2016-06-23
#### Added
* Initial version tracking, includes Check, CheckCollections, CheckTask, ObjectMethodTests, TestData, and TestDataObj
