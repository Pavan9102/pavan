#!/usr/bin/env bash
set -euo pipefail

DERBY_VERSION=10.16.1.1

echo "Starting Derby Network Server (if not running)..."
java -jar "$HOME/.m2/repository/org/apache/derby/derbynet/$DERBY_VERSION/derbynet-$DERBY_VERSION.jar" start -p 1527 || true

echo "Building backend..."
mvn -q -e -DskipTests clean package

echo "Starting Jetty on http://localhost:8080 ..."
mvn -q org.eclipse.jetty:jetty-maven-plugin:run

