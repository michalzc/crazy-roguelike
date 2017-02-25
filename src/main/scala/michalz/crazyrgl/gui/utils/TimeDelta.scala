package michalz.crazyrgl.gui.utils

class TimeDelta {
  private var lastUpdate = System.currentTimeMillis()
  private var delta_ = 0L
  private var fps_ = 0L
  private var deltaFps = 0L
  private var frames = 0


  def update(): Unit = {
    val now = System.currentTimeMillis()
    delta_ = now - lastUpdate
    frames += 1
    deltaFps += delta_

    if(deltaFps > 1000) {
      fps_ = frames * 1000 / deltaFps
      deltaFps = 0
      frames = 0
    }

    lastUpdate = now
  }

  def fps: Long = fps_
  def delta: Long = delta_
}
