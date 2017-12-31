/**************************************************************************
 *
 * Copyright (c) 2017 Aktoro project contributors.
 *
 **************************************************************************/

package com.varmateo.gradle;

import org.gradle.api.Project
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


    /**
     * Executes the given closure if a task with the given name is
     * currently selected to be executed.
     */
    static void onlyIfTaskSelected(
            final String taskName,
            final Closure closure) {

        def context = closure.delegate
        Project project = context.project
        String propName = project.rootProject.name + "." + taskName
        boolean isExecutionDesired =
                project.hasProperty(propName) && (project.getProperty(propName) != "no")
        boolean isTargetTask =
                project.gradle.startParameter.taskNames.contains(taskName)
       boolean isExecutionEnabled =
               isExecutionDesired || isTargetTask

       if ( isExecutionEnabled ) {
           closure.call()
       }
    }

}
