class StringRenderTask(private val argument: String) : RenderTask() {
    override fun execute() {
        println("Executing StringRenderTask with string argument: $argument")
    }
}