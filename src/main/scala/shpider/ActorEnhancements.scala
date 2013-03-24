package shpider

trait ActorEnhancements {
  def handleUnknownEvent(e: Any) = {} // println("Unknown event '%s'. Should not happen!".format(e))
}