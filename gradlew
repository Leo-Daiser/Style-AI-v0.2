#!/bin/sh

################################################################################
##
##  Gradle start up script for UN*X
##
################################################################################

# Attempt to set APP_BASE_NAME and APP_HOME
# Resolve links: $0 may be a link
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`/"$link"
    fi
done

SAVED="`pwd`"
CDPATH=""
APP_HOME="`cd \`dirname \"$PRG\"\` >/dev/null && pwd`"
cd "$SAVED"

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Use the maximum available, or set custom VM options.
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Find java project settings
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Locate the Gradle Wrapper JAR
WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

if [ -f "$WRAPPER_JAR" ] && [ -s "$WRAPPER_JAR" ]; then
    # Standard Gradle Wrapper execution
    exec "$JAVACMD" $DEFAULT_JVM_OPTS -classpath "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
else
    # Fallback to local system gradle installation if wrapper jar is placeholder or missing
    if command -v gradle >/dev/null 2>&1; then
        exec gradle "$@"
    else
        echo "Error: Gradle wrapper JAR not available and system 'gradle' is not installed." >&2
        exit 1
    fi
fi
