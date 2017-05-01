import java.util.concurrent.atomic.AtomicInteger

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import ovh.classregister.users.UserTasks

import scala.concurrent.duration._

class UsersSimulation extends Simulation {

  var index = new AtomicInteger(0)
  val scenarioName = "CreateUpdateDeleteListViewSearchTasks"
  val httpProtocol = http.baseURL("http://localhost:6002")

  val userScenario = scenario(scenarioName).exec(session => session.set("userLogin", "gatling_user" + index.incrementAndGet()))
    .exec(
      UserTasks.createUserTask,
      UserTasks.getUserTask,
      UserTasks.getUsersTask,
      UserTasks.searchForUserByLoginTask,
      UserTasks.updateUserTask,
      UserTasks.deleteUserTask
  )

  private def userBody =
    """{
          "login": "gatling_user",
          "password": "gatling_password"
       }"""

  // 30000 requests in 60 seconds
  setUp(userScenario.inject(rampUsers(5000) over (60 seconds))).protocols(httpProtocol)
}
