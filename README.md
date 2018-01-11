pagarme-java
=========

Pagar.me's Java API

[![Build Status](https://travis-ci.org/adrianoluis/pagarme-java.png?branch=master)](https://travis-ci.org/adrianoluis/pagarme-java)

## Support
If you have any problem or suggestion please open an issue [here](https://github.com/adrianoluis/pagarme-java/issues).

## Usage

Simply put the following snippet into your proper build config:

#### Apache Maven

##### Repository
```xml
<repositories>
    <repository>
        <id>pagarme-java-mvn-repo</id>
        <url>https://raw.githubusercontent.com/adrianoluis/pagarme-java/mvn-repo/</url>
        <releases>
            <enabled>true</enabled>
            <updatePolicy>never</updatePolicy>
        </releases>
    </repository>
</repositories>
```

##### Dependency
```xml
<dependency>
    <groupId>me.pagar</groupId>
    <artifactId>pagarme-java</artifactId>
    <version>1.3.0</version>
</dependency>
```

#### Gradle/Grails

##### Repository
```groovy
repositories {
    maven { url 'https://raw.githubusercontent.com/adrianoluis/pagarme-java/mvn-repo' }
}
```

##### Dependency
```groovy
compile('me.pagar:pagarme-java:1.3.0') {
    transitive = true
}
```

#### Apache Buildr

##### Dependency
```
'me.pagar:pagarme-java:jar:1.3.0'
```

#### Apache Ivy

##### Dependency
```xml
<dependency org="me.pagar" name="pagarme-java" rev="1.3.0">
    <artifact name="pagarme-java" type="jar" />
</dependency>
```

#### Groovy Grape

##### Dependency
```groovy
@Grapes(
  @Grab(group='me.pagar', module='pagarme-java', version='1.3.0')
)
```

#### Scala SBT

##### Dependency
```scala
libraryDependencies += "me.pagar" % "pagarme-java" % "1.3.0"
```

#### Leiningen

##### Dependency
```clojure
[me.pagar/pagarme-java "1.3.0"]
```

## License

Check [here](LICENSE).