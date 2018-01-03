/**************************************************************************
 *
 * Copyright (c) 2017-2018 Aktoro project contributors.
 *
 **************************************************************************/

package com.varmateo.gradle;

import org.gradle.api.Project
import org.gradle.api.Task


/**
 * Utility functions for managing Gradle tasks.
 */
final class Tasks {


    public static final String GROUP_REPORTS = "buildReports"
    public static final String GROUP_DEPLOY = "mavenDeploy"


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
    public static void enableReportTaskOnlyIfSelected(final Task task) {

        List<String> propNames = [ task.name, GROUP_REPORTS ]

        enableTaskOnlyIfSelectedByProp(task, propNames)
    }


    /**
     *
     */
    public static void enableDeployTaskOnlyIfSelected(final Task task) {

        List<String> propNames = [ task.name, GROUP_DEPLOY ]

        enableTaskOnlyIfSelectedByProp(task, propNames)
    }


    /**
     *
     */
    private static void enableTaskOnlyIfSelectedByProp(
            final Task task,
            final List<String> propNames) {

        task.onlyIf {
            isTaskExecutionEnabled(task.project, propNames)
        }
    }


    /**
     * Executes the given closure if a task with the given name is
     * currently selected to be executed.
     */
    public static void onlyIfReportTaskSelected(
            final String taskName,
            final Closure closure) {

        List<String> propNames = [ taskName, GROUP_REPORTS ]

        onlyIfTaskSelectedByProp(propNames, closure)
    }


    /**
     * 
     */
    public static void onlyIfDeployTaskSelected(
            final String taskName,
            final Closure closure) {

        List<String> propNames = [ taskName, GROUP_DEPLOY ]

        onlyIfTaskSelectedByProp(propNames, closure)
    }


    /**
     *
     */
    private static void onlyIfTaskSelectedByProp(
            final List<String> propNames,
            final Closure closure) {

        Project project = closure.delegate.project
        boolean isExecutionEnabled = isTaskExecutionEnabled(project, propNames)

       if ( isExecutionEnabled ) {
           closure.call()
       }
    }


    /**
     *
     */
    private static boolean isTaskExecutionEnabled(
            final Project project,
            final List<String> propNames) {

        boolean isExecutionDesired = propNames.inject(false) {
            boolean result, String propName ->
            result || isPropEnabled(project, propName);
        }

        boolean isTargetTask = propNames.inject(false) {
            boolean result, String propName ->
            result || project.gradle.startParameter.taskNames.contains(propName)
        }

        boolean isExecutionEnabled =
                isExecutionDesired || isTargetTask

       return isExecutionEnabled
    }


    /**
     *
     */
    private static boolean isPropEnabled(
            final Project project,
            final String propNameSuffix) {

        String rootProjectName = project.rootProject.name
        String propName = rootProjectName + "." + propNameSuffix

        if ( project.hasProperty(propName) ) {
            def propValue = project.getProperty(propName)

            return propValue != "no"
        } else {
            return false
        }
    }

}
