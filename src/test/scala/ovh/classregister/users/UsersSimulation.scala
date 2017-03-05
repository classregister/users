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

  setUp(userScenario.inject(rampUsers(200) over (1 seconds))).protocols(httpProtocol)
}
