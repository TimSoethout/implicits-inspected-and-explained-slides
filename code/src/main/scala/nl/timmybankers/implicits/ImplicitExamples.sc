

import com.twitter.finagle.builder.ClientConfig.Yes

import scala.concurrent.Future

// ActorRef#!

def doExpensiveComputation() : Any = ???

// Future
Future {
  doExpensiveComputation()
}(scala.concurrent.ExecutionContext.global)

// Finagle
import com.twitter.finagle.Service
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.Http
import com.twitter.finagle.http.{Request, Response}

val builder: ClientBuilder[Request, Response, Yes, Yes, Nothing] = ClientBuilder()
  .codec(Http())
  .hosts("twitter.com:80")

// Triggers exception
//builder.build()

//Error:(24, 15) Builder is not fully configured: Cluster: com.twitter.finagle.builder.ClientConfig.Yes, Codec: com.twitter.finagle.builder.ClientConfig.Yes, HostConnectionLimit: Nothing
//builder.build()
//^

val client: Service[Request, Response] = ClientBuilder()
  .codec(Http())
  .hosts("twitter.com:80")
  .hostConnectionLimit(1)
  .build()


// Typeclass Numeric
List(1,2,3).sum

