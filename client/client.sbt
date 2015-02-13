/*
 * Copyright © 2015 Typesafe, Inc. All rights reserved.
 * No information contained herein may be reproduced or transmitted in any form or
 * by any means without the express written permission of Typesafe, Inc.
 */
name := "play-soap-client"

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

libraryDependencies ++= Seq(
  typesafeLibrary(TypesafeLibrary.playOrganization, "play").value % "provided",
  "org.apache.cxf" % "cxf-rt-frontend-jaxws" % CxfVersion,
  "org.apache.cxf" % "cxf-rt-transports-http-hc" % CxfVersion,

  "org.apache.cxf" % "cxf-rt-transports-http" % CxfVersion % "test",
  "org.apache.cxf" % "cxf-rt-transports-http-jetty" % CxfVersion % "test",
  typesafeLibrary(TypesafeLibrary.playOrganization, "play-test").value % "test"
)

fork in Test := true