object Main {
    @JvmStatic
    fun main(args: Array<String>) {

        val renderQueue = RenderQueue()

        // Создаем и запускаем поток для обработки задач
        val consumerThread = Thread {
            while (!renderQueue.isShutdown()) {
                try {
                    val task = renderQueue.takeTask()
                    task?.execute()
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                }
            }
        }
        consumerThread.start()

        // Создаем и добавляем задачи в очередь
        renderQueue.addTask(StringRenderTask("Argument 1"))
        renderQueue.addTask(StringRenderTask("Argument 2"))
        renderQueue.addTask(StringRenderTask("Argument 3"))

        // Добавляем новые задачи во время работы основного потока
        renderQueue.addTask(IntRenderTask(4))
        renderQueue.addTask(IntRenderTask(5))

        // Ждем немного, чтобы дать возможность потокам обработать задачи
        Thread.sleep(500)

        // Завершаем работу очереди и ожидаем завершения потока-потребителя
        renderQueue.shutdown()

        // Пробуем добавить новую задачу, чтобы убедиться, что она не начнется
        renderQueue.addTask(IntRenderTask(6))

        // завершаем наш поток
        try {
            consumerThread.join()
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }

        println("Main thread finished, render thread is alive: ${consumerThread.isAlive}")
    }
}