[![Build Status](https://travis-ci.org/UnknownNPC/json-to-string-maven-plugin.svg?branch=master)](https://travis-ci.org/UnknownNPC/json-to-string-maven-plugin)
[![codecov](https://codecov.io/gh/UnknownNPC/json-to-string-maven-plugin/branch/master/graph/badge.svg)](https://codecov.io/gh/UnknownNPC/json-to-string-maven-plugin)

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
	<groupId>com.github.unknownnpc.plugins</groupId>
	<artifactId>json-to-string-maven-plugin</artifactId>
	<version>1.1</version>
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

Plugin is also supports `skip` configuration parameter, eg. `<skip>true</skip>`. 

#### Run
```
mvn com.github.unknownnpc.plugins:json-to-string-maven-plugin:minify
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
