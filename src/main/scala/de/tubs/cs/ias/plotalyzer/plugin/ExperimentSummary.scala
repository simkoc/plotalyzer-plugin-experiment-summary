package de.tubs.cs.ias.plotalyzer.plugin

import de.tubs.cs.ias.plotalyzer.database.entities.InterfaceAnalysis
import de.tubs.cs.ias.plotalyzer.plugins.{AnalysisContext, AnalysisPlugin, AnalysisReturn, JSONReturn}
import spray.json.{JsNumber, JsObject}

/** Basic plugin to generate an overview over the meta data of a given experiment
 *
 * @author Simon Koch
 *
 */
class ExperimentSummary extends AnalysisPlugin {

  /** running the analysis and outputting the count/failure/success for analysis,requests,and distinct apps
   *
   * @param context the analysis context for the plugin
   * @return either a failure exception or a JSONReturn summarizing the numbers as a json
   */
  override def analyze(
      context: AnalysisContext): Either[Exception, AnalysisReturn] = {
    try {
      val analysis = InterfaceAnalysis.get(context.experiment)(context.database)
      val requests = analysis.flatMap(_.getTrafficCollection.flatMap(_.getRequests))
      val json = JsObject(
        "experiment" -> JsNumber(context.experiment.getId),
        "analysis" -> JsObject(
          "overall" -> JsNumber(analysis.length),
          "success" -> JsNumber(analysis.count(_.getSuccess)),
          "failure" -> JsNumber(analysis.count(!_.getSuccess))
        ),
        "apps" -> JsObject(
          "overall" -> JsNumber(analysis.map(_.getApp).toSet.size),
          "success" -> JsNumber(analysis.filter(_.getSuccess).map(_.getApp).toSet.size),
          "failure" -> JsNumber(analysis.filter(!_.getSuccess).map(_.getApp).toSet.size),
        ),
        "requests" -> JsObject(
          "overall" -> JsNumber(requests.length),
          "success" -> JsNumber(requests.count(_.error.isEmpty)),
          "failure" -> JsNumber(requests.count(_.error.nonEmpty))
        )
      )
      Right(JSONReturn(json))
    } catch {
      case e: Exception => Left(e)
    }
  }
}
