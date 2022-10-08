# minstyle.io-generator

> Generator / Customizer for minstyle.io CSS framework.

## Informations

minstyle.io-generator is a REST service developed under Spring Boot, allowing to generate the custom CSS file
of [minstyle.io CSS framework](https://minstyle.io).

## Technical Stack

The service has been developed with the following technologies :

JAVA REST Service :

* Spring Boot (`2.6.2`)
    * spring-boot-starter-web
    * spring-boot-starter-test (Tests are developed via `JUNIT5`)
    * spring-boot-starter-validation
    * spring-boot-starter-data-mongodb
    * spring-boot-devtools
* bucket4j (Allowing request throttling control implementation)
* lombok
* commons-io

Data :

* MongoDB Database (on MongoDB Cloud)

Web :

* HTML/CSS, JS
* [minstyle.io](http://minstyle.io) CSS Framework.
* [Coloris](https://github.com/mdbassit/Coloris) (for color picker).

## Use

The REST service exposes two end points. The first one allows to customize
the [four main colors](https://minstyle.io/docs/Layout/colors) of the framework (`.ms-primary`, `.ms-secondary`
, `.ms-action`, `.ms-action2`), and to download the CSS file :

```
https://generator.minstyle.io/download/{primaryColor}/{secondaryColor}/{actionColor}/{action2Color}
```

The second end point allows you to download the CSS file of the framework, with the default colors :

```
https://generator.minstyle.io/download
```

## License

Under MIT License.

## Links

- [minstyle.io](https://minstyle.io/)
- [minstyle.io repository](https://github.com/Airmime/minstyle.io)