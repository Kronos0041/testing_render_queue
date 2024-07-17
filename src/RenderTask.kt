import java.util.*

abstract class RenderTask {
    val id: UUID = UUID.randomUUID()
    abstract fun execute()
}