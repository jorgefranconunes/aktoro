#!/bin/bash
###########################################################################
#
# Copyright (c) 2017 Aktoro project contributors.
#
#
# Utility functions. This script is not meant to be executed by
# itself. Instead it is tipically sourced from within other scripts
# using the Bash "source" operator.
#
#########################################################################





###########################################################################
#
# Displays a message to stderr and exits the process.
#
# Arguments:
#
# 1. The message to be displayed.
#
###########################################################################

function aktError () {

    echo "ERROR" $1 >&2
    exit 1
}





###########################################################################
#
# Displays a message to stdout prefixed with the current time.
#
# Arguments:
#
# 1. The message to be displayed.
#
###########################################################################

function aktLog () {

    echo $(date "+%Y-%m-%d %H:%M:%S.%3N") "$@"
}





###########################################################################
#
# Checks if a set of required tools is available. If any is missing
# then an error message is output and the current process is
# terminated.
#
# Arguments:
#
# 1. Tools to look for in the PATH or by path.
#
###########################################################################

function aktCheckForTools () {

    local toolList="$@"

    for tool in ${toolList} ; do
        if type $tool > /dev/null 2>&1 ; then
            : # We found the tool. All is ok
        else
            aktError "Missing \"${tool}\" tool. Please install this tool."
        fi
    done
}





###########################################################################
#
# In a Cygwin environment, converts the given path to a Windows
# path. This will be needed when passing paths to Java, because Java
# is a native Windows app (so we assume).
#
###########################################################################

function aktNormalizePath () {

    case "$OSTYPE" in
    cygwin )
        cygpath --mixed --absolute "$1"
        ;;
    * )
        echo $1
    esac
}

