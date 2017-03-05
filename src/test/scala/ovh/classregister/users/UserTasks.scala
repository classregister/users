package ovh.classregister.users

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object UserTasks {

  private def userBody =
    s"""{
    "login": "gatling_user",
    "password": "gatling_password"
  }"""

  val createUserTask = exec(
    http("create user")
      .post("/users")
      .header("Content-Type", "application/json")
      .body(StringBody(userBody))
      .asJSON
      .check(jsonPath("$.id").saveAs("userId"))
      .check(status is 200)
  )

  val getUserTask = exec(
    http("get user")
      .get("/users/${userId}")
      .header("Content-Type", "application/json")
      .asJSON
      .check(jsonPath("$.id"))
      .check(status is 200)
  )

  val getUsersTask = exec(
    http("get users")
      .get("/users")
      .header("Content-Type", "application/json")
      .asJSON
      .check(status is 200)
  )

  val updateUserTask = exec(
    http("update user")
      .put("/users/${userId}")
      .header("Content-Type", "application/json")
      .body(StringBody(userBody))
      .asJSON
      .check(jsonPath("$.id"))
      .check(status is 200)
  )

  val deleteUserTask = exec(
    http("delete user").delete("/users/${userId}")
      .header("Content-Type", "application/json")
      .asJSON
      .check(status is 204)
  )
}
