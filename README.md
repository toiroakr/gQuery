# gQuery
gQuery is able to get json element like jQuery with gson.

## Dependencies
[google/gson][1] (operation check with 2.3.1)

## Download
You can download jar files from [bintray.com][2].

Or use Gradle:
* build.gradle
```groovy
repositories {
  jcenter()
}

dependencies {
    compile 'com.toiroakr:gquery:0.1.2'
}
```

Or use Maven:
```xml
<dependency>
  <groupId>com.toiroakr</groupId>
  <artifactId>gquery</artifactId>
  <version>0.1.2</version>
  <type>pom</type>
</dependency>
```


## Usage
Simple use cases will look something like this:


* sample json:
```json:sample.json
{
	"results": {
		"sample": {
			"a": true,
			"b": [[1,2,3], [4,5,6]],
			"c": {
          		d:[{ "foo":1, "bar":3 }, { "foo":2, "bar":1 }]
            }
		}
	}
}
```

* java class correspond to json
```java
@GSelect("results sample")
public class SampleObject {
	@GSelect("a")
    boolean a; // -> true
	@GSelect("b")
    List<int[]> b; // -> [[1,2,3], [4,5,6]]
	@GSelect("c d")
    List<Map<String, Integer>> d; // -> [{ "foo":1, "bar":3 }, { "foo":2, "bar":1 }]
	@GSelect("c d foo")
    List<Integer> foo; // -> [1, 2]
}
```
* get java object from json
```java
String json = ...;
SampleObject sample = new GQuery().select(json, SampleObject.class);
boolean a = new GQuery().get(json, "results sample a", String.class); // -> true
```

You can see more sample in [test code][3].

[1]: https://github.com/google/gson
[2]: https://bintray.com/toiroakr/maven/gQuery/view
[3]: https://github.com/toiroakr/gQuery/tree/master/src/test/java/com/toiroakr/gquery

