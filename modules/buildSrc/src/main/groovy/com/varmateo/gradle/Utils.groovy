/**************************************************************************
 *
 * Copyright (c) 2017 Aktoro project contributors.
 *
 **************************************************************************/

package com.varmateo.gradle;

import org.gradle.api.Task


/**
 * Povides utility functions.
 */
final class Utils {


    /**
     * Enables the given task if one of the following conditions is
     * satisfied:
     *
     * Project property "${rootProject.name}.${task.name}" is defined
     * and its value is different from "no".
     *
     * The task name was specified as one of the targets for the
     * "gradle" command.
     */
    static void enableTaskOnlyIfSelected(final Task task) {

        String propNameSufix = task.name
        Closure<Boolean> closure = {
            String propName = rootProject.name + "." + propNameSufix
            boolean isExecutionDesired =
                    project.hasProperty(propName) && (project[propName] != "no")
            boolean isTargetTask =
                    gradle.startParameter.taskNames.contains(task.name)
            boolean isExecutionEnabled =
                    isExecutionDesired || isTargetTask

            isExecutionEnabled
        }
        closure.delegate = task.project

        task.onlyIf closure
    }

}
