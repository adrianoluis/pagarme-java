pagarme-java [![Build Status](https://travis-ci.org/adrianoluis/pagarme-java.png?branch=master)](https://travis-ci.org/adrianoluis/pagarme-java)
============

## SDK Não Oficial do Pagar.Me em Java

Esta biblioteca permite a iteração com a api do *Pagar.Me* de forma simples e intuitiva, e segue mesmos moldes já consolidado das bibliotecas oferecidas por eles em outras linguagens. A documentação está disponível em https://adrianoluis.github.io/pagarme-java/.

## Suporte
Se você tiver algum problema ou sugestão, abra um ticket [aqui](https://github.com/adrianoluis/pagarme-java/issues).

## Como Usar

Basta colocar o seguinte trecho de código nas configurações de compilação apropriada:

#### Apache Maven

##### Repositório
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

##### Dependência
```xml
<dependency>
    <groupId>me.pagar</groupId>
    <artifactId>pagarme-java</artifactId>
    <version>1.3.0</version>
</dependency>
```

#### Gradle/Grails

##### Repositório
```groovy
repositories {
    maven { url 'https://raw.githubusercontent.com/adrianoluis/pagarme-java/mvn-repo' }
}
```

##### Dependência
```groovy
compile('me.pagar:pagarme-java:1.3.0') {
    transitive = true
}
```

#### Apache Buildr

##### Dependência
```
'me.pagar:pagarme-java:jar:1.3.0'
```

#### Apache Ivy

##### Dependência
```xml
<dependency org="me.pagar" name="pagarme-java" rev="1.3.0">
    <artifact name="pagarme-java" type="jar" />
</dependency>
```

#### Groovy Grape

##### Dependência
```groovy
@Grapes(
  @Grab(group='me.pagar', module='pagarme-java', version='1.3.0')
)
```

#### Scala SBT

##### Dependência
```scala
libraryDependencies += "me.pagar" % "pagarme-java" % "1.3.0"
```

#### Leiningen

##### Dependência
```clojure
[me.pagar/pagarme-java "1.3.0"]
```

## Licença

Clique [aqui](LICENSE).