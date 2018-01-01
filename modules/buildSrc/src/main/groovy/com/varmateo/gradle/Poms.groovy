/**************************************************************************
 *
 * Copyright (c) 2018 Aktoro project contributors.
 *
 **************************************************************************/

package com.varmateo.gradle;

import org.gradle.api.artifacts.maven.MavenPom


/**
 * Utility functions for managing POMs for modules of the Aktoro
 * project.
 */
final class Poms {


    /**
     * Creates a closure for configuring the POM for one of the Aktoro
     * project modules.
     *
     * The returned closure expects to receive as single argument a
     * MavenPom instance.
     */
    static Closure buildPomConfiguration(
            final String pomProjectName,
            final String pomProjectDescription) {

        return { MavenPom pom ->
            pom.project {
                name pomProjectName
                description pomProjectDescription
                url "http://aktoro.varmateo.com/"
                inceptionYear "2017"
                licenses {
                    license {
                        name "GNU Lesser General Public License (LGPL) 3.0"
                        url "https://www.gnu.org/licenses/lgpl-3.0.en.html"
                    }
                }
                developers {
                    developer {
                        id "jorgefranconunes"
                        name "Jorge Nunes"
                        email "jorgefranconunes@gmail.com"
                    }
                }
                scm {
                    connection "scm:git:https://github.com/jorgefranconunes/aktoro.git"
                    developerConnection "scm:git:git@github.com:jorgefranconunes/aktoro.git"
                    url "https://github.com/jorgefranconunes/aktoro"
                }
            }
        }
    }

}
