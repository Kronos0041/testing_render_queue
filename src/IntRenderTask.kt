class IntRenderTask(private val intArgument: Int) : RenderTask() {
    override fun execute() {
        println("Executing IntRenderTask with int argument: $intArgument")
    }
}