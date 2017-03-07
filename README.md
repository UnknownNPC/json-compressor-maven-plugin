[![Build Status](https://travis-ci.org/UnknownNPC/json-to-string-maven-plugin.svg?branch=master)](https://travis-ci.org/UnknownNPC/json-to-string-maven-plugin)
[![codecov](https://codecov.io/gh/UnknownNPC/json-to-string-maven-plugin/branch/master/graph/badge.svg)](https://codecov.io/gh/UnknownNPC/json-to-string-maven-plugin)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.unknownnpc.plugins/json-to-string-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.unknownnpc.plugins/json-to-string-maven-plugin)

json-compressor-maven-plugin
=====================

## Overview
Plugin removes useless whitespaces and newlines in target json files. 
It helps to reduce file sizes before they get packaged.

## Content
 
#### Install  
Requires Java 1.7+ and Maven 3.x+
```
mvn clean install
```

#### Usage in pom.xml
```
<plugin>
	<groupId>com.github.unknownnpc.plugins</groupId>
	<artifactId>json-compressor</artifactId>
	<version>1.3</version>
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
		<excludes>
			<exclude>${project.build.directory}/classes/large-files-3.json</exclude>
		</excludes>
	</configuration>
</plugin>
```

Plugin is also support `skip` configuration parameter, eg. `<configuration><skip>true</skip></configuration>`. 

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
