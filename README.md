[![codecov](https://codecov.io/gh/UnknownNPC/json-compressor-maven-plugin/branch/master/graph/badge.svg)](https://codecov.io/gh/UnknownNPC/json-compressor-maven-plugin)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.unknownnpc.plugins/json-compressor/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.unknownnpc.plugins/json-compressor)

json-compressor-maven-plugin
=====================

## Overview
Plugin removes useless whitespaces and newlines in target json files. 
It helps to reduce file sizes before they get packaged.

## Content
 
#### Install  
Requires Java 21 and Maven 3.x+

> **Note:** starting from version `2.0.0`, this plugin targets Java 21. Maven runs plugins in its own JVM,
> so this is a breaking change for anyone building with a JDK older than 21 — stay on the `1.x` line if you
> need an older JDK.
```
mvn clean install
```

#### Usage in pom.xml
```
<plugin>
	<groupId>com.github.unknownnpc.plugins</groupId>
	<artifactId>json-compressor</artifactId>
	<version>2.0.0</version>
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
			<include>${project.build.directory}/classes/some-folder</include>
		</includes>
		<excludes>
			<exclude>${project.build.directory}/classes/large-files-3.json</exclude>
		</excludes>
	</configuration>
</plugin>
```

Each `<include>`/`<exclude>` entry accepts either a wildcard file path (matched against files directly
inside that path's parent folder, non-recursive) or a plain folder path, eg. `.../some-folder` &mdash;
every `*.json` file inside that folder and all of its subfolders is matched recursively.

Plugin also supports these configuration parameters:
- `skip` &mdash; skips plugin execution entirely, eg. `<configuration><skip>true</skip></configuration>`.
- `encoding` &mdash; charset used to read and write the target files, eg. `<configuration><encoding>UTF-8</encoding></configuration>`.
  Defaults to `${project.build.sourceEncoding}`.

#### Run
```
mvn com.github.unknownnpc.plugins:json-compressor:minify
```
## Example

##### Before
```
{
    "id": 1,
    "name": "A green door",
    "price": 12.50,
    "tags": ["home", "green"]
}
```
##### After
```
{"id":1,"name":"A green door","price":12.5,"tags":["home","green"]}
```
