/*
 * Copyright © 2015 Typesafe, Inc. All rights reserved.
 * No information contained herein may be reproduced or transmitted in any form or
 * by any means without the express written permission of Typesafe, Inc.
 */
package play.soap.sbtplugin.tester

import javax.xml.ws.handler.MessageContext
import javax.xml.ws.handler.soap.{SOAPMessageContext, SOAPHandler}

import java.util.{Map => JMap, List => JList, HashMap => JHashMap}
import scala.collection.JavaConversions._

class AuthenticationHandler extends SOAPHandler[SOAPMessageContext] {
  def getHeaders = null

  def handleMessage(context: SOAPMessageContext) = {
    // If this is an outbound message
    if (context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)
      .asInstanceOf[java.lang.Boolean]) {

      // Get the request headers, which may be null, in which case create them
      val headers = Option(context.get(MessageContext.HTTP_REQUEST_HEADERS)
        .asInstanceOf[JMap[String, JList[String]]]
      ).getOrElse(new JHashMap[String, JList[String]])

      // Add the authentication token to the headers
      headers += ("Authentication-Token" -> List("somesecret"))

      // Attach the headers to the context
      context += (MessageContext.HTTP_REQUEST_HEADERS -> headers)

    }
    true
  }

  def close(context: MessageContext) = ()

  def handleFault(context: SOAPMessageContext) = true
}
