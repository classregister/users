import io.gatling.core.Predef._
import io.gatling.http.Predef._
import ovh.classregister.users.UserTasks

import scala.concurrent.duration._

class UsersSimulation extends Simulation {

  val httpConfig = http.baseURL("http://localhost:6002").header("Content-Type", "application/json")

  val scenarioName = "CreateUpdateDeleteListViewTasks"
  val httpProtocol = http.baseURL("http://localhost:6002")

  val userScenario = scenario(scenarioName).exec(
      UserTasks.createUserTask,
      UserTasks.getUserTask,
      UserTasks.getUsersTask,
      UserTasks.updateUserTask,
      UserTasks.deleteUserTask
  )

  // 30000 requests in 60 seconds
  setUp(userScenario.inject(rampUsers(6000) over (60 seconds))).protocols(httpProtocol)
}
