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

        task.onlyIf {
            isTaskExecutionEnabled(task.project, task.name)
        }
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
        boolean isExecutionEnabled = isTaskExecutionEnabled(project, taskName)

       if ( isExecutionEnabled ) {
           closure.call()
       }
    }


    /**
     *
     */
    private static boolean isTaskExecutionEnabled(
            final Project project,
            final String taskName) {

        String rootProjectName = project.rootProject.name
        boolean isExecutionDesired =
                isPropEnabled(project, rootProjectName + "." + taskName)
        boolean isTargetTask =
                project.gradle.startParameter.taskNames.contains(taskName)
        boolean isBuildReportsDesired =
                isPropEnabled(project, rootProjectName + ".buildReports")
        boolean isExecutionEnabled =
                isExecutionDesired || isTargetTask || isBuildReportsDesired

       return isExecutionEnabled
    }


    /**
     *
     */
    private static boolean isPropEnabled(
            final Project project,
            final String propName) {

        if ( project.hasProperty(propName) ) {
            def propValue = project.getProperty(propName)

            return propValue != "no"
        } else {
            return false
        }
    }

}
