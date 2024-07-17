import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock


class RenderQueue {

    private val queue: Queue<RenderTask> = LinkedList()
    private var isShutdown = AtomicBoolean(false)
    private val lock = ReentrantLock()

    @Synchronized
    // Добавление задачи в очередь для рендеринга
    fun addTask(task: RenderTask) {
        if (!isShutdown.get()) {
            queue.offer(task)
            println("Task added to the queue: " + task.id)
        } else {
            println("Task not added queue is finished!")
        }
    }

    @Synchronized
    @Throws(InterruptedException::class)
    // Берем следующую задачу из очереди
    fun takeTask(): RenderTask? {
        lock.withLock {
            return if (queue.isNotEmpty()) queue.poll() else null
        }
    }

    // Выключаем очередь рендера и чистим очередь
    @Synchronized
    fun shutdown() {
        queue.clear()
        isShutdown.set(true)
    }

    fun isShutdown(): Boolean = isShutdown.get()
}