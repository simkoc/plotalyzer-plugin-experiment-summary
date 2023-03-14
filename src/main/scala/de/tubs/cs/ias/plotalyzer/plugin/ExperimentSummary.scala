package de.tubs.cs.ias.plotalyzer.plugin

import de.tubs.cs.ias.plotalyzer.plugins.{
  AnalysisContext,
  AnalysisPlugin,
  AnalysisReturn,
  JSONReturn
}
import spray.json.{JsObject, JsString}

class ExperimentSummary extends AnalysisPlugin {

  override def analyze(
      context: AnalysisContext): Either[Exception, AnalysisReturn] = {
    try {
      Right(JSONReturn(JsObject("test" -> JsString("version 0.0.2"))))
    } catch {
      case e: Exception => Left(e)
    }
  }
}
