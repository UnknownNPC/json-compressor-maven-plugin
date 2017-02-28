json2string-maven-plugin
=====================

## Overview
Plugin removes useless whitespaces and newlines in target json files. 
It helps to reduce file sizes before they get packaged. 

## Content
 
#### Install  
```
mvn clean install
```

#### Usage in pom.xml
```
<plugin>
	<groupId>com.github.unknownnpc.jsontostring</groupId>
	<artifactId>json-to-string-maven-plugin</artifactId>
	<version>1.0</version>
	<executions>
		<execution>
			<goals>
				<goal>minify</goal>
			</goals>
		</execution>
	</executions>
	<configuration>
		<includes>
			<include>${project.build.directory}/classes/large-file.json</include>
			<include>${project.build.directory}/classes/large-files-*.json</include>
		</includes>
	</configuration>
</plugin>
```

#### Run
```
mvn com.github.unknownnpc.jsontostring:json-to-string-maven-plugin:minify
```
## Example

####Before
```
{
    "id": 1,
    "name": "A green door",
    "price": 12.50,
    "tags": ["home", "green"]
}
```
####After
```
{"id":1,"name":"A green door","price":12.5,"tags":["home","green"]}
```
