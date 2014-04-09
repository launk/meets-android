![Meets logo](http://meets.io/assets/logo_android_meets_magento-d83f8010b106647d251e489a8758047f.png "Meets")

# Overview

Meets Android is a native SDK designed to ease the communications between Android mobile apps and Magento stores.
It allows you to access Magento's data as if it were local data.

You don't have to worry about learning Magento's SOAP and REST APIs or dealing with low level details.
Instead, you can only focus on building rich native mobile shopping experiences.

# Main features

- Native library that works with your project out of the box.
- Allows you to forget complexities and focus on what really matters.
- Access to Magento users, categories and products as if they were local data.
- Implement easily a fully native shopping experience.
- Continuously updated [API documentation](http://meets.io/docs) with examples.
- Direct contact with Meets programmers to resolve issues.

# Learn more

You can learn more about Meets in the official web page, <http://meets.io/>, and in the [API documentation](http://meets.io/docs)

# How to install

> **WARNING:**
>
>_Note that this is a beta version of Meets so it's not intended to use in production environments.
>We are working hardly to have a stable version as soon as possible._
>
> _Also note that all data sent and retrieved to/from the server goes in plain text. If you want to use Meets to work
> with sensitive information be sure to be under a secure connection._

We are working hardly to make the installation process as easy as possible. Future releases will be published in Maven central repository.
Right now you have to retrieve Meets from the snapshots maven repository:

1. Add the repository to your `build.gradle`

        repositories {
            maven {
                url 'https://oss.sonatype.org/content/repositories/snapshots'
            }
        }
1. Add the library dependency inside the `dependencies` section:

        dependencies {
            // ...
            compile 'com.theagilemonkeys.meets:meets:0.0.1-SNAPSHOT'
            // ...
        }

# Meets at Meet Magento Spain conference

![Meet Magento Spain logo](http://es.meet-magento.com/wp-content/uploads/2012/10/blogi4mm14es.jpg "Meet Magento Spain")

Meets have been announced at [Meet Magento Spain](http://es.meet-magento.com/), a Magento eCommerce conference
where merchants, Magento agencies, Magento service providers and the Magento community exchange knowledge and
experiences with enthusiastic decision maker and experts according the topics Magento and eCommerce.

[Meets at Meet Magento Spain.](http://es.meet-magento.com/meets/)

# Open source projects that have helped Meets to become real

Special thanks to people involved in the following projects. They have made easier to achieve the goals of Meets:

- [Robospice](https://github.com/octo-online/robospice)
- [Jackson](http://jackson.codehaus.org/)
- [KSoap 2](https://code.google.com/p/ksoap2-android/)
- [jDeferred](https://github.com/jdeferred/jdeferred)

# License

This project uses the MIT license. See LICENSE.

# Next steps

1. Upload project to Maven central repository.
1. Add javadoc.
1. Keep on adding more Magento API use cases.
1. Support for Magento CE 1.7 and 1.8.
1. Full test coverage.
