/*
 * Copyright © 2015 Typesafe, Inc. All rights reserved.
 * No information contained herein may be reproduced or transmitted in any form or
 * by any means without the express written permission of Typesafe, Inc.
 */
package play.soap

import java.lang.reflect.{Method, ParameterizedType, Type}

import org.apache.cxf.wsdl.service.factory.AbstractServiceConfiguration
import play.libs.F.Promise

import scala.concurrent.Future

import java.lang.Boolean.FALSE

private[soap] class PlayServiceConfiguration extends AbstractServiceConfiguration {

  /**
   * We say future/promise is a holder type so that JAXB will create bindings for the inner type, not the outer.
   */
  override def isHolder(cls: Class[_], `type`: Type) =
    if (classOf[Future[_]] == cls || classOf[Promise[_]] == cls) java.lang.Boolean.TRUE else null

  override def getHolderType(cls: Class[_], `type`: Type) = {
    if (classOf[Future[_]] == cls || classOf[Promise[_]] == cls) {
      `type` match {
        case p: ParameterizedType => p.getActualTypeArguments()(0)
      }
    } else null
  }

  /**
   * Neither scala.Unit or java.lang.Void are output types
   */
  override def hasOutMessage(m: Method) = {
    m.getGenericReturnType match {
      case future: ParameterizedType
        if future.getRawType == classOf[Future[_]] ||
          future.getRawType == classOf[Promise[_]] =>
        future.getActualTypeArguments.headOption match {
          case Some(unit) if unit == classOf[Unit] || unit == classOf[Void] => FALSE
          case _ => null
        }
      case _ => null
    }
  }
}