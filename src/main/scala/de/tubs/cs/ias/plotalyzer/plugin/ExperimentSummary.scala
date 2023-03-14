package de.tubs.cs.ias.plotalyzer.plugin

import de.tubs.cs.ias.plotalyzer.database.entities.Experiment
import de.tubs.cs.ias.plotalyzer.plugins.AnalysisPlugin
import spray.json.{JsObject, JsString, JsValue}

class ExperimentSummary extends AnalysisPlugin {

  override def analyze(experiment: Experiment): Either[Exception, JsValue] = {
    try {
      Right(JsObject("test" -> JsString("success")))
    } catch {
      case e : Exception => Left(e)
    }
  }


}
